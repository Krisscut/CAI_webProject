package com.enib.cai.web.services.impl

import com.enib.cai.web.model.Equipment
import com.enib.cai.web.services.Equipments
import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCollection
import com.mongodb.DBCursor
import com.mongodb.DBObject
import org.vertx.java.core.json.JsonArray
import org.vertx.java.core.json.JsonObject

import javax.inject.Inject

/**
 * Created by Simon on 13/12/2014.
 */
class MongoEquipmentsImpl implements Equipments
{
    // MongoDB driver
    @Inject
    private DB db;

    public MongoEquipmentsImpl() {

    }

    public JsonArray getEquipments() {
        JsonArray jsonEquipments = new JsonArray();
        DBCursor cursor = null;
        try {
            // GET ALL Worms
            DBCollection equipmentsCollection = db.getCollection("equipments");

            cursor = equipmentsCollection.find();

            while (cursor.hasNext())
            {
                DBObject object = cursor.next();

                Equipment equipment = new Equipment(object)
                JsonObject equipmentJson = equipment.getJsonObject();

                jsonEquipments.addObject(equipmentJson);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return jsonEquipments;
    }


    public JsonObject getEquipment(String id) throws Exception
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

                    Equipment worm = new Equipment(dbObject)
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

    @Override
    JsonArray getEquipment() {
        return null
    }

    @Override
    JsonObject getEquipments(String id) throws Exception {
        return null
    }

    public JsonObject addEquipment(Equipment worm) throws Exception
    {
        try {
            DBCollection wormsCollection = db.getCollection("worms");

            DBObject dbWorm = worm.getDBObject()

            wormsCollection.insert(dbWorm).getLastError().throwOnError();

            return this.getEquipment(worm.id);
        } catch(com.mongodb.MongoException.DuplicateKey exp) {
            throw new Exception("already exists");
        } catch (Exception e) {
            throw e;
        }
    }



}
