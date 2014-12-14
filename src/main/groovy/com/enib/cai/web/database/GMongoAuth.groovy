package com.enib.cai.web.database

import com.gmongo.GMongoClient
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress

/**
 * Created by Simon on 07/12/2014.
 */
class GMongoAuth
{
    private def config;
    private def client;

    public GMongoAuth ( def config )
    {
        this.config = config
        configConnexion()
    }

    void configConnexion ()
    {
        Map<String,Object> mongoConfig = config["mongo"]

        if (mongoConfig["useAuth"] == true){
            println "New authentificated client"
            def credentials = MongoCredential.createMongoCRCredential(mongoConfig["username"], mongoConfig["database"], mongoConfig["password"] as char[])
            client = new GMongoClient(new ServerAddress(mongoConfig["host"],mongoConfig["port"] ), [credentials])
        }
        else
        {
            println "New client"
            client = new GMongoClient(new ServerAddress())
        }
    }





}
