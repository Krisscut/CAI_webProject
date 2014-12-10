package com.enib.cai.web.services

import com.enib.cai.web.model.Worm
import org.vertx.java.core.json.JsonArray
import org.vertx.java.core.json.JsonObject

/**
 * Created by Simon on 10/12/2014.
 */
public interface Worms
{
    public JsonArray getWorms();

    public JsonObject getWorm(String id) throws Exception;

    public JsonObject addWorm(Worm worm) throws Exception;
}
