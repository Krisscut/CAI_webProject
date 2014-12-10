package com.enib.cai.web.model

import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import org.vertx.java.core.json.JsonObject

/**
 * Created by Simon on 09/12/2014.
 */
abstract class Article
{
    String id
    String name
    String description
    String img
    int availability
    int cost

    public Article ()
    {

    }

    public Article(DBObject object )
    {
        println "Creating new article"

        this.id = object.get("_id")
        this.name = object.get("name")
        this.description = object.get("description")
        this.img = object.get("img")
        this.availability = object.get("availability")
        this.cost = object.get("cost")
    }

    public Article(String id, String name, String description, String img, int availability , int cost)
    {
        this.id = id
        this.name = name
        this.description = description
        this.img = img
        this.availability = availability
        this.cost = cost
    }

    public Map<String,Object> getHashmap ()
    {
        Map<String,Object> map = new HashMap<>()
        map["id"] = this.id
        map["name"] = this.name
        map["description"] = this.name
        map["img"] = this.name
        map["availability"] = this.name
        map["cost"] = this.cost

        return map
    }

    public JsonObject getJsonObject()
    {
        JsonObject jsonObject = new JsonObject()

        jsonObject.putString("id", (String) this.id)
        jsonObject.putString("name", (String) this.name)
        jsonObject.putString("description",(String) this.description)
        jsonObject.putString("img",(String) this.img)
        jsonObject.putNumber("availability",(Number) this.availability)
        jsonObject.putNumber("cost", (Number) this.cost)

        return jsonObject
    }

    public DBObject getDBObject ()
    {
        DBObject dbObject = new BasicDBObject();

        dbObject.put("_id", (String) this.id)
        dbObject.put("name", (String) this.name)
        dbObject.put("description",(String) this.description)
        dbObject.put("img",(String) this.img)
        dbObject.put("availability",(Number) this.availability)
        dbObject.put("cost", (Number) this.cost)


        return dbObject
    }
}
