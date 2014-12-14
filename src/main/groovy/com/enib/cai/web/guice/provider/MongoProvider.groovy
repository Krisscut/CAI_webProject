package com.enib.cai.web.guice.provider

import com.enib.cai.web.model.Ability
import com.enib.cai.web.model.Bonus
import com.enib.cai.web.model.Equipment
import com.enib.cai.web.model.Worm

import com.google.inject.Inject;
import com.google.inject.Provider
import com.mongodb.DB;
import com.mongodb.DBObject
import com.mongodb.MongoClient
import com.mongodb.util.JSON

import javax.inject.Named
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

      DBObject bson = ( DBObject ) JSON.parse("{\n" +
              "  \"alcohol\": 6.8,\n" +
              "  \"availability\": \"Year round\",\n" +
              "  \"brewery\": \"Brasserie Affligem (Heineken)\",\n" +
              "  \"description\": \"Affligem Blonde, the classic cééééééaaaaàààààlear blonde abbey ale, with a gentle roundness and 6.8% alcohol. Low on bitterness, it is eminently drinkable.\",\n" +
              "  \"_id\": \"AffligemBlond\",\n" +
              "  \"img\": \"img/AffligemBlond.jpg\",\n" +
              "  \"label\": \"img/AffligemBlond-label.png\",\n" +
              "  \"name\": \"Affligem Blond\",\n" +
              "  \"serving\": \"Serve in a Snifter\",\n" +
              "  \"style\": \"Belgian-Style Blonde Ale\"\n" +
              "}");
      db.getCollection("beers").save(bson);

    println "Preparing abilities"
      //Force vitality speed resistance mind
    Ability forceAbility =              new Ability ('0', 10, 0, 0, 0, 0)
    Ability weakAbility =               new Ability ('1', -10, 0, 0, 0, 0)
    Ability heavyAbility =              new Ability ('2', 0, 10, 0, 0, 0)
    Ability tinyAbility =               new Ability ('3', 0, -10, 0, 0, 0)
    Ability quickAbility =              new Ability ('4', 0, 0, 10, 0, 0)
    Ability slowAbility =               new Ability ('5', 0, 0, -10, 0, 0)
    Ability resistanceAbility =         new Ability ('6', 0, 0, 0, 10, 0)
    Ability unResistanceAbility =       new Ability ('7', 0, 0, 0, -10, 0)
    Ability smartAbility =              new Ability ('8', 0, 0, 0, 0, 10)
    Ability unSmartAbility =            new Ability ('9', 0, 0, 0, 0, -10)

    println "Inserting worms"
    Worm wormStrong = new Worm('0',"Worm Fort", "L'un des worm les plus imposants que vous pourrez trouver sur le jeu", "img/wormFort.jpg", 100, 1000, forceAbility)
    db.getCollection("worms").save(wormStrong.getDBObject());
    Worm wormWeak = new Worm('1',"Worm Faible", "L'un des worm les plus faibles que vous pourrez trouver sur le jeu", "img/wormWeak.jpg", 100, 1000, weakAbility)
    db.getCollection("worms").save(wormWeak.getDBObject());

    Bonus   speedBonus = new Bonus('0', "Bonus rapidité", "Founis un bonus de rapidité pour votre worm !", "img/bonusQuick.jpg", 100, 100, quickAbility)
    db.getCollection("bonus").save(speedBonus.getDBObject());
    DBObject object = speedBonus.getDBObject()
    Bonus   slowBonus = new Bonus('1', "Slow Malus", "Founis un malus de rapidité pour votre worm !", "img/bonusSlow.jpg", 100, 100, slowAbility)
    db.getCollection("bonus").save(slowBonus.getDBObject());

    Equipment   sword = new Equipment('0', "Epée", "Une épée tout ce qu'il y a de plus classique", "img/sword.png", 10, 1000, heavyAbility)
    db.getCollection("equipments").save(sword.getDBObject());
    Equipment   grenade = new Equipment('1', "Grenade", "Une grenade augmentant considérablement votre puissance de feu", "img/grenade.png", 10, 1000, smartAbility)
    db.getCollection("equipments").save(grenade.getDBObject());
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