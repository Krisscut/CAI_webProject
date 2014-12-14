package com.enib.cai.web.model

import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import java.security.MessageDigest;
/**
 * Created by Simon on 13/12/2014.
 */
class User
{
    def salt = "4v4o57f%_5cFbbejb_9SM8v()#T0OTOYYJLwKhjCMmof6!AcN#XpGB&oM2wvQG9"
    String id;
    String username;
    String password;
    String email;
    Date inscriptionDate;
    Date lastConnexionDate;


    public User(String username, String password, String email= "default")
    {
        this.username = username
        def source = password + username + salt
        def messageDigest = MessageDigest.getInstance("SHA-256")
        source = messageDigest.digest(source.getBytes("UTF-8")).encodeBase64().toString()
        this.password = source
        this.email = email
    }

    public User(DBObject object )
    {
        this.username = object.get("username")
        this.password = object.get("password")
        this.email = object.get("email")
    }

    public DBObject getDBObject ()
    {
        DBObject dbObject = new BasicDBObject();

        dbObject.put("username", (String) this.username)
        dbObject.put("password",(String) this.password)
        dbObject.put("email",(String) this.email)

        return dbObject
    }
}
