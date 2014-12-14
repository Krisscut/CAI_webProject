package com.enib.cai.web.services;

import com.enib.cai.web.model.Beer;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

public interface Beers {

  public JsonArray getBeers();

  public JsonObject getBeer(String id) throws Exception;

  public JsonObject addBeer(Beer beer) throws Exception;

}
