package com.enib.cai.web.verticle

import com.enib.cai.web.database.MongoInit

/**
 * Created by Simon on 02/12/2014.
 */
import org.vertx.groovy.platform.Verticle;


public class EntryPointVerticle extends Verticle {

    public start() {
        println "deploying entry point verticle of wormBuyer website"

        def config = container.config
        println "config=" + config.toString()

        //deploy third parties module
        container.deployModule("io.vertx~mod-mailer~2.0.0-final", container.config["mailer"])

        //deploy worker verticles
        container.deployWorkerVerticle("groovy:com.enib.cai.web.verticle.MongoVerticle", container.config,2)
        container.deployWorkerVerticle("groovy:com.enib.cai.web.verticle.WormsWorkerVerticle", container.config,2)
        container.deployWorkerVerticle("groovy:com.enib.cai.web.verticle.BonusesWorkerVerticle", container.config, 2)
        container.deployWorkerVerticle("groovy:com.enib.cai.web.verticle.EquipmentWorkerVerticle", container.config, 2)
        container.deployWorkerVerticle("groovy:com.enib.cai.web.verticle.UsersWorkerVerticle", container.config, 2)
        container.deployWorkerVerticle("groovy:com.enib.cai.web.verticle.OrdersWorkerVerticle", container.config, 2)
        container.deployWorkerVerticle("groovy:com.enib.cai.web.verticle.TokensWorkerVerticle", container.config, 2)
        container.deployWorkerVerticle("groovy:com.enib.cai.web.verticle.BeersWorkerVerticle", container.config, 2)

        // deploy Website Verticle programaticaly
        container.deployVerticle("groovy:com.enib.cai.web.verticle.WebMainVerticle", container.config);

        println "Website have been deployed successfully!"
    }
}
