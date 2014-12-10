package com.enib.cai.web.model

import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import com.mongodb.util.JSON
import org.vertx.java.core.json.JsonObject

/**
 * Created by Simon on 07/12/2014.
 */
class Worm extends Article
{
    Ability ability;

    //For static init
    public Worm(String id, String name, String description, String img, int availability, int cost , Ability ability)
    {
        super(id, name, description, img, availability, cost)
        this.ability = ability;
    }

    //Construct from database
    public Worm(DBObject object)
    {
        super(object)
        this.ability = new Ability (object.get("ability"));
    }

    public Map<String,Object> getHashmap ()
    {
        Map<String,Object> map = super.getHashmap()

        map["ability"] = this.ability.getHashmap()
        return map
    }

    public JsonObject getJsonObject()
    {
        JsonObject jsonObject = super.getJsonObject()

        jsonObject.putObject("ability", this.ability.getJsonObject())

        return jsonObject
    }

    public DBObject getDBObject ()
    {
        DBObject dbWorm = super.getDBObject()

        dbWorm.put("ability", this.ability.getHashmap())

        return dbWorm
    }
}
