package com.enib.cai.web.services

import com.enib.cai.web.model.Bonus
import org.vertx.java.core.json.JsonArray
import org.vertx.java.core.json.JsonObject

/**
 * Created by Simon on 13/12/2014.
 */
public interface Bonuses
{
    public JsonArray getBonuses();
    public JsonObject getBonus(String id) throws Exception;
    public JsonObject addBonus(Bonus bonus) throws Exception;
}
