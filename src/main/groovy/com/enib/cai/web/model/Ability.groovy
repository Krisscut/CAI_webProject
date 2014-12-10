package com.enib.cai.web.model

import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import org.vertx.java.core.json.JsonObject

/**
 * Main class that represent bonus,
 */
class Ability
{
    String id;
    int force
    int vitality
    int speed
    int resistance
    int mind

   public Ability()
   {

   }

   public Ability(String id, int force, int vitality, int speed, int resistance, int mind)
   {
        this.id = id
        this.force = force
        this.vitality = vitality
        this.speed = speed
        this.resistance = resistance
        this.mind = mind
   }

    //construct from database
    public Ability (DBObject object)
    {
        this.id = object.get("_id")
        this.force = object.get("force")
        this.vitality = object.get("vitality")
        this.speed = object.get("speed")
        this.resistance = object.get("resistance")
        this.mind = object.get("mind")

    }

    public Map<String,Object> getHashmap ()
    {
        Map<String,Object> map = new HashMap<>()

        map["_id"] = this.id
        map["force"] = this.force
        map["vitality"] = this.vitality
        map["speed"] = this.speed
        map["resistance"] = this.resistance
        map["mind"] = this.mind

        return map
    }

    public JsonObject getJsonObject()
    {
        JsonObject jsonObject = new  JsonObject()

        jsonObject.putString("id", (String) this.id)
        jsonObject.putNumber("force", (Number) this.force)
        jsonObject.putNumber("vitality" , (Number) this.vitality)
        jsonObject.putNumber("speed" , (Number) this.speed)
        jsonObject.putNumber("resistance" , (Number) this.speed)
        jsonObject.putNumber("mind", (Number) this.mind)

        return jsonObject
    }

    public DBObject getDBObject ()
    {
        DBObject object = new BasicDBObject()

        object.put("_id", this.id)
        object.put("force", this.force)
        object.put("vitality" , this.vitality)
        object.put("speed" ,this.speed)
        object.put("resistance" , this.speed)
        object.put("mind", this.mind)
    }
}
