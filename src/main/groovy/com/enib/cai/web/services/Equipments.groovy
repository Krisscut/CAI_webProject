package com.enib.cai.web.services

import com.enib.cai.web.model.Equipment
import org.vertx.java.core.json.JsonArray
import org.vertx.java.core.json.JsonObject

/**
 * Created by Simon on 10/12/2014.
 */
public interface Equipments
{
    public JsonArray getEquipments();

    public JsonObject getEquipment(String id) throws Exception;

    public JsonObject addEquipment(Equipment equipment) throws Exception;
}