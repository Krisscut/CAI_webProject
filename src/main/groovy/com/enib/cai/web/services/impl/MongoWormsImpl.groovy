package com.enib.cai.web.services.impl

import com.enib.cai.web.model.Worm
import com.enib.cai.web.services.Articles
import com.enib.cai.web.services.Worms
import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCollection
import com.mongodb.DBCursor
import com.mongodb.DBObject
import org.vertx.java.core.json.JsonArray
import org.vertx.java.core.json.JsonObject

import javax.inject.Inject

/**
 * Created by Simon on 10/12/2014.
 */
class MongoWormsImpl implements Worms {
    // MongoDB driver
    @Inject
    private DB db;

    public MongoWormsImpl() {

    }

    public JsonArray getWorms() {
        JsonArray jsonWorms = new JsonArray();
        DBCursor cursor = null;
        try {
            // GET ALL Worms
            DBCollection wormsCollection = db.getCollection("worms");

            cursor = wormsCollection.find();

            while (cursor.hasNext())
            {
                DBObject object = cursor.next();

                Worm worm = new Worm(object)
                JsonObject wormJson = worm.getJsonObject();

                jsonWorms.addObject(wormJson);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return jsonWorms;
    }


    public JsonObject getWorm(String id) throws Exception
    {
        JsonObject wormJson = new JsonObject();
        DBCursor cursor = null;
        try {
            // GET ALL Worms
            DBCollection wormsCollection = db.getCollection("worms")

            DBObject query = new BasicDBObject("_id", id)
            cursor = wormsCollection.find(query)

            switch (cursor.size())
            {
                case 0:
                    throw new Exception("Unknown id")
                case 1:
                    //wrap the response
                    DBObject dbObject = cursor.next()

                    Worm worm = new Worm(dbObject)
                    wormJson = worm.getJsonObject()
                    break;
                default:
                    throw new Exception("Ouch! More than one result was found")
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return wormJson;
    }

    public JsonObject addWorm(Worm worm) throws Exception
    {
        try {
            DBCollection wormsCollection = db.getCollection("worms");

            DBObject dbWorm = worm.getDBObject()

            wormsCollection.insert(dbWorm).getLastError().throwOnError();

            return this.getWorm(worm.id);
        } catch(com.mongodb.MongoException.DuplicateKey exp) {
            throw new Exception("already exists");
        } catch (Exception e) {
            throw e;
        }
    }
}