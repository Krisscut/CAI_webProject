package com.enib.cai.web.services

import com.enib.cai.web.model.Article
import com.mongodb.DBObject
import org.vertx.java.core.json.JsonArray
import org.vertx.java.core.json.JsonObject

/**
 * Created by Simon on 10/12/2014.
 */

public interface Articles
{
    public JsonObject getArticle(String typeArticle, int id) throws Exception;
    public JsonArray  getArticles(String[] typeArticles);
    public JsonArray  getArticles(String typeArticles);
    public JsonObject addArticle(String typeArticle  ,Article article) throws Exception;
    public Article    createArticle(String typeArticle, DBObject dbObject)
}