package com.enib.cai.web.guice.provider

import com.enib.cai.web.model.Ability
import com.enib.cai.web.model.Worm;
import com.google.inject.Inject;
import com.google.inject.Provider
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import com.mongodb.util.JSON;
import org.vertx.java.core.json.JsonObject;

import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicBoolean;

public class MongoProvider implements Provider<DB> {

  @Inject
  @Named("mongo")
  private Map<String,Object> config


  private MongoClient mongo;
  private final AtomicBoolean mongoInitialized = new AtomicBoolean(false);

  @Override
  public DB get() {
    if (!mongoInitialized.getAndSet(true)) {
      try {
        mongo = new MongoClient((String)config["host"], (Integer)config["port"]);

        String path = config["staticImgs"];
        staticInit(path);

      } catch (UnknownHostException e) {
        e.printStackTrace();
      }

    }
    return mongo.getDB(config["dbname"]);
  }


  private void staticInit(String path) {
    // Do the static data injection
    println "Static initialisation of the database"
    // init the worms
    DB db = mongo.getDB(config["dbname"]);

    println "Preparing abilities"
    Ability forceAbility = new Ability('0', 10, 0, 0, 0, 0)
    Ability weakAbility =  new Ability('1', -10, 0, 0, 0, 0)

    println "Inserting worms"
    Worm wormStrong = new Worm('0',"Worm Strong", "L'un des worm les plus imposants que vous pourrez trouver sur le jeu", "img/wormFort.jpg", 100, 1000, forceAbility)
    db.getCollection("worms").save(wormStrong.getDBObject());
    Worm wormWeak = new Worm('1',"Worm Weak", "L'un des worm les plus faibles que vous pourrez trouver sur le jeu", "img/wormWeak.jpg", 100, 1000, weakAbility)
    db.getCollection("worms").save(wormWeak.getDBObject());

    /*
    if (path != null) {
      System.out.println("load images path=" + path);
      try {
        //grid fs img
        GridFS gfsImages = new GridFS(db, "img");
        File actual = new File(path);
        for (File f : actual.listFiles()) {
          String fileName = f.getName();
          GridFSInputFile gfsFile = gfsImages.createFile(f);
          gfsFile.setFilename(fileName);
          gfsFile.save();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    */
  }


}