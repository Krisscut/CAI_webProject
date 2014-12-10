package com.enib.cai.web.verticle

import com.enib.cai.web.database.GMongoAuth
import groovy.json.JsonOutput
import org.vertx.groovy.core.eventbus.EventBus
import org.vertx.groovy.platform.Verticle

/**
 * Created by Simon on 07/12/2014.
 */
class MongoVerticle extends Verticle {
    public start() {
        super.start()
        println "deploying Mongo verticle"

        EventBus eb = vertx.eventBus

        def handler = { message ->
            String response = ""


            Map<String,Object> map = new HashMap<>();
            map["test"] = "myCuteTest"
            map["test3"] = "myMarvellousTest"

            def json = JsonOutput.toJson(map)
            String jsonString = JsonOutput.prettyPrint(json)
            println "Parsed JSON : " + jsonString

            try
            {
                switch (message.body)
                {
                    case "worm":
                        println ("in handler worm")
                        response = "in handler worm"
                        new GMongoAuth(container.config)
                        break

                    default:
                        println ("in default worm")
                        response = "in default worm";
                }
            }
            catch (Exception exp)
            {
                response = "Huston we have a problem"
                exp.printStackTrace();
            }
            // Now reply to it
            message.reply(response)
        }

        eb.registerHandler("mongo.service", handler)
        {
            println (" mongo.service handler !")
        }
    }








}
