package com.enib.cai.web.database

import com.enib.cai.web.model.Ability
import com.enib.cai.web.model.Worm
import com.gmongo.GMongo
import org.vertx.groovy.platform.Verticle

class MongoInit
{
    MongoInit()
    {
        println ("MongoInit :: Creating basic database")
        def mongo = new GMongo()

        def db = mongo.getDB("CAIwebProject")

        /* Remove all datas */
        db.worms.remove([:])
        db.abilities.remove([:])

        /* Create some abilities to use it with all the stuff */
        Ability forceAbilityBonus0 = new Ability(0, 10, 0, 0, 0, 0)


        /* Create some instance of worms ! */
        Worm wormFort = new Worm(0,"Worm Fort", "L'un des worm les plus imposants que vous pourrez trouver sur le jeu", "img/wormFort.jpg", 100, 1000, forceAbilityBonus0)
        db.worms << wormFort.getHashmap()
    }
}
