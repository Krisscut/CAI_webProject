package com.enib.cai.web.model

import com.mongodb.BasicDBObject
import com.mongodb.DBObject

import java.security.MessageDigest

/**
 * Created by Simon on 13/12/2014.
 */


class Token
{
    def salt = "Kl4CGTCYawx(jHg@wL)UKmXXxmkKTIk5WUN^U3Y@2w(VrgpnvLr7T^pKYyL%XP-Z"
    String token
    Date   dateCreated
    Date   dateExpiration


    public Token( String username)
    {
        def source = new Date(System.currentTimeMillis())
        source = source.toString() + username + this.salt
        def messageDigest = MessageDigest.getInstance("SHA-256")
        this.token = messageDigest.digest(source.getBytes("UTF-8")).encodeBase64().toString()
        this.dateCreated = new Date(System.currentTimeMillis())
        this.dateExpiration = new Date(System.currentTimeMillis() + 60 * 60 *1000 )   //Expiration in one hour
    }

    public Token(DBObject object )
    {
        this.token = object.get("token")
        this.dateCreated = object.get("dateCreated")
        this.dateExpiration = object.get("dateExpiration")
    }

    public DBObject getDBObject ()
    {
        DBObject dbObject = new BasicDBObject();

        dbObject.put("token", (String) this.token)
        dbObject.put("dateCreated",(Date) this.dateCreated)
        dbObject.put("dateExpiration",(Date) this.dateExpiration)

        return dbObject
    }

    public void updateDateExpiration ()
    {
        this.dateExpiration = new Date(System.currentTimeMillis() + 60 * 60 *1000 ) //1 hour increase
    }
}
