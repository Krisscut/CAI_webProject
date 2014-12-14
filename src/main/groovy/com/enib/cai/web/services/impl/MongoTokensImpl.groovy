package com.enib.cai.web.services.impl

import com.enib.cai.web.model.Token
import com.enib.cai.web.services.Tokens
import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCollection
import com.mongodb.DBCursor
import com.mongodb.DBObject
import org.vertx.java.core.json.JsonObject

import javax.inject.Inject

/**
 * Created by Simon on 14/12/2014.
 */
class MongoTokensImpl implements Tokens
{
    // MongoDB driver
    @Inject
    private DB db;

    @Override
    void cleanOldToken()
    {
        DBCursor cursor = null
        //Step 1, search for an existing entry with the same token
        try
        {
            DBCollection tokenCollection = db.getCollection("token")

            cursor = tokenCollection.find()

            if (cursor.size() == 0)
            {
                println "Token database is empty"
            }
            else
            {
                //retrieve existing token
                DBObject dbObject = cursor.next()
                Token existingToken = new Token (dbObject)

                //Check if the date of expiration is not overflow
                Date currentDate = new Date()

                //if overflow, fire an error and delete the token
                if ( currentDate > existingToken.dateExpiration )
                {
                    //Delete the token
                    DBObject queryDelete = new BasicDBObject("token", existingToken.token)
                    DBObject resultDelete = tokenCollection.findAndRemove(queryDelete)

                    println "Session have expired, token " + existingToken.token + " have been deleted"
                }
                else            //else, ok , update the token's date of expiration
                {
                    println "Token is valid"
                }
            }

        }
        catch (Exception e)
        {
            throw e;
            println "Error in maintenance tasks"
        }
        finally
        {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
