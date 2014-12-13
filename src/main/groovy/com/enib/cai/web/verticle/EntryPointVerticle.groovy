package com.enib.cai.web.verticle

import com.enib.cai.web.database.MongoInit

/**
 * Created by Simon on 02/12/2014.
 */
import org.vertx.groovy.platform.Verticle;


public class EntryPointVerticle extends Verticle {

    public start() {
        println "deploy entry point verticle of wormBuyer website"

        def config = container.config
        println "config=" + config.toString()

        //deploy worker verticles
        container.deployWorkerVerticle("groovy:com.enib.cai.web.verticle.MongoVerticle", container.config,4)
        container.deployWorkerVerticle("groovy:com.enib.cai.web.verticle.WormsWorkerVerticle", container.config,4)
        container.deployWorkerVerticle("groovy:com.enib.cai.web.verticle.BonusesWorkerVerticle", container.config, 4)
        container.deployWorkerVerticle("groovy:com.enib.cai.web.verticle.EquipmentWorkerVerticle", container.config, 4)

        // deploy Website Verticle programaticaly
        container.deployVerticle("groovy:com.enib.cai.web.verticle.WebMainVerticle", container.config);
    }
}
