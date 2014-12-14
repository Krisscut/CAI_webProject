package com.enib.cai.web.services.impl

import com.enib.cai.web.model.Article
import com.enib.cai.web.model.Bonus
import com.enib.cai.web.model.Equipment
import com.enib.cai.web.model.Worm
import com.enib.cai.web.services.Articles

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
class MongoArticlesImpl implements Articles
{
    // MongoDB driver
    @Inject
    private DB db;

    /**
     * @param typeArticle
     * @param id
     * @return
     * @throws Exception
     */
    public JsonObject getArticle(String typeArticle, int id) throws Exception
    {
        JsonObject articleJson = new JsonObject();
        DBCursor cursor = null;
        try {
            // GET ALL Article from the given type
            DBCollection articleCollection = db.getCollection(typeArticle)

            DBObject query = new BasicDBObject("_id", id)
            cursor = articleCollection.find(query)

            switch (cursor.size())
            {
                case 0:
                    throw new Exception("Unknown id")
                case 1:
                    //wrap the response
                    DBObject dbObject = cursor.next()

                    Article article = createArticle(typeArticle, dbObject)
                    articleJson = article.getJsonObject()
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
        return articleJson;

    }

    @Override
    JsonArray getArticles(String[] typeArticles) {
        JsonObject jsonArticles = new JsonObject();
        DBCursor cursor = null;
        try {
            for ( int i = 0 ; i < typeArticles.length ; i++)
            {
                // GET ALL Articles from the given types
                JsonArray listArticlesJson = new JsonArray();

                DBCollection currentCollection = db.getCollection(typeArticles[i]);
                cursor = currentCollection.find();

                while (cursor.hasNext())
                {
                    DBObject object = cursor.next();

                    Article article = createArticle(typeArticles[i], object)
                    JsonObject articleJson =  article.getJsonObject()

                    listArticlesJson.addObject(articleJson);
                }
                jsonArticles.putArray(typeArticles[i], listArticlesJson)
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return jsonArticles;
    }

    @Override
    JsonArray getArticles(String typeArticles) {
        JsonArray jsonArticles = new JsonArray();
        DBCursor cursor = null;
        try {
            // GET ALL Articles from the given type
            DBCollection currentCollection = db.getCollection(typeArticles);
            cursor = currentCollection.find();

            while (cursor.hasNext())
            {
                DBObject object = cursor.next();
                Article article = createArticle(typeArticles, object)
                JsonObject articleJson =  article.getJsonObject()
                jsonArticles.addObject(articleJson);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return jsonArticles;
    }


    @Override
    JsonObject addArticle(String typeArticle, Article article) throws Exception {
        try {
            DBCollection articleCollection = db.getCollection("typeArticle");

            DBObject dbArticle = article.getDBObject()

            articleCollection.insert(dbArticle).getLastError().throwOnError();

            return this.getArticle(typeArticle, article.id);
        } catch(com.mongodb.MongoException.DuplicateKey exp) {
            throw new Exception("already exists");
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    Article createArticle(String typeArticle, DBObject dbObject)
    {
        Article returnObject

        switch (typeArticle)
        {
            case "worm":
               returnObject = new Worm(dbObject)
               break
            case "equipment":
                returnObject = new Equipment(dbObject)
                break
            case "bonus":
                returnObject = new Bonus(dbObject)
                break
            default :
                println "FATAL ERROR : CAN't CREATE ARTICLE"
                break;
        }
        return returnObject
    }
}
