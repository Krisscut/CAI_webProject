package com.enib.cai.web.services.impl

import com.enib.cai.web.model.Token
import com.enib.cai.web.model.User
import com.enib.cai.web.model.Worm
import com.enib.cai.web.services.Users
import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCollection
import com.mongodb.DBCursor
import com.mongodb.DBObject
import org.vertx.java.core.json.JsonObject

import javax.inject.Inject

/**
 * Created by Simon on 13/12/2014.
 */
class MongoUsersImpl implements Users
{
    // MongoDB driver
    @Inject
    private DB db;

    @Override
    public JsonObject createAccount(String username, String password, String email)
    {
        JsonObject returnValue = new JsonObject()
        DBCursor cursor = null
        //Step 1, search for an existing entry with the same name....
        boolean alreadyUse = false
        try
        {
            DBCollection usersCollection = db.getCollection("users")

            DBObject query = new BasicDBObject("username", username)
            cursor = usersCollection.find(query)

            switch (cursor.size())
            {
                case 0:
                    //Now, prepare the insertion of the new user
                    User user = new User (username, password, email)
                    DBObject insertQuery = user.getDBObject()

                    usersCollection.insert(insertQuery)

                    returnValue.putString("status", "ok")
                    returnValue.putString("result", "account created successfully")
                    break;
                case 1:
                    returnValue.putString("status", "error")
                    returnValue.putString("result", "username already exist")
                    break;
                default:
                    returnValue.putString("status", "error")
                    returnValue.putString("result", "Multiple username already exist")
                    throw new Exception("Ouch! More than one result was found")
            }
        }
        catch (Exception e)
        {
            throw e;
            returnValue.putString("status", "error")
            returnValue.putString("result", "Exception catched !")
        }
        finally
        {
            if (cursor != null) {
                cursor.close();
            }
        }
        return returnValue
    }

    @Override
    JsonObject login(String username, String password)
    {
        JsonObject returnValue = new JsonObject()
        DBCursor cursor = null
        //Step 1, search for an existing entry with the same name....
        try
        {
            DBCollection usersCollection = db.getCollection("users")

            DBObject query = new BasicDBObject("username", username)
            cursor = usersCollection.find(query)

            switch (cursor.size())
            {
                case 0:
                    //Error, user do not exist
                    returnValue.putString("status", "error")
                    returnValue.putString("result", "username don't exist")

                    break;
                case 1:
                    //retrieve existing user
                    DBObject dbObject = cursor.next()
                    User existingUser = new User (dbObject)

                    //Create user using given informations
                    User currentUser = new User(username, password)

                    //same password, login is ok
                    if ( existingUser.password == currentUser.password)
                    {
                        //Create new token
                        Token token = new Token(username)
                        DBObject insertQuery = token.getDBObject()

                        //Insert the new token in the database
                        DBCollection tokenCollection = db.getCollection("token")

                        tokenCollection.insert(insertQuery)

                        returnValue.putString("status", "ok")
                        returnValue.putString("result", token.token)
                    }
                    else
                    {
                        returnValue.putString("status", "error")
                        returnValue.putString("result", "Password missmatch")
                    }


                    break;
                default:
                    returnValue.putString("status", "error")
                    returnValue.putString("result", "Multiple username already exist")
                    throw new Exception("Ouch! More than one result was found")
            }
        }
        catch (Exception e)
        {
            throw e;
            returnValue.putString("status", "error")
            returnValue.putString("result", "Exception catched !")
        }
        finally
        {
            if (cursor != null) {
                cursor.close();
            }
        }
        return returnValue
    }

    @Override
    JsonObject logout(String token)
    {
        //Delete all existing token
        JsonObject returnValue = new JsonObject()
        try
        {
            DBCollection tokenCollection = db.getCollection("token")

            DBObject query = new BasicDBObject("token", token)
            DBObject result = tokenCollection.findAndRemove(query)

            if (result != null)
            {
                returnValue.putString("status", "ok")
                returnValue.putString("result", "A token have been removed from the database")
            }
            else
            {
                returnValue.putString("status", "ok")
                returnValue.putString("result", "Unknow given token")
            }
        }
        catch (Exception e)
        {
            throw e;
            returnValue.putString("status", "error")
            returnValue.putString("result", "Exception catched !")
        }
        return returnValue
    }

    @Override
    JsonObject authorize(String token)
    {
        JsonObject returnValue = new JsonObject()
        DBCursor cursor = null
        //Step 1, search for an existing entry with the same token
        try
        {
            DBCollection tokenCollection = db.getCollection("token")

            DBObject query = new BasicDBObject("token", token)
            cursor = tokenCollection.find(query)

            switch (cursor.size())
            {
                case 0:
                    //Error, user do not exist
                    returnValue.putString("status", "error")
                    returnValue.putString("result", "token doen't exist")
                    break;
                case 1:
                    //retrieve existing token
                    DBObject dbObject = cursor.next()
                    Token existingToken = new Token (dbObject)

                    //Check if the date of expiration is not overflow
                    Date currentDate = new Date()

                    //if overflow, fire an error and delete the token
                    if ( currentDate > existingToken.dateExpiration )
                    {
                        //Delete the token
                        DBObject queryDelete = new BasicDBObject("token", token)
                        DBObject resultDelete = tokenCollection.findAndRemove(queryDelete)

                        returnValue.putString("status", "error")
                        returnValue.putString("result", "Session have expired")
                    }
                    else            //else, ok , update the token's date of expiration
                    {
                        existingToken.updateDateExpiration()

                        tokenCollection.findAndModify(dbObject,existingToken.getDBObject())

                        returnValue.putString("status", "ok")
                        returnValue.putString("result", "Session is online")
                    }
                    break;
                default:
                    returnValue.putString("status", "error")
                    returnValue.putString("result", "Multiple username already exist")
                    throw new Exception("Ouch! More than one result was found")
            }
        }
        catch (Exception e)
        {
            throw e;
            returnValue.putString("status", "error")
            returnValue.putString("result", "Exception catched !")
        }
        finally
        {
            if (cursor != null) {
                cursor.close();
            }
        }
        return returnValue
    }

    @Override
    JsonObject authentificateUser() {
        return null
    }
}
