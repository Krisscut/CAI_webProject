package com.enib.cai.web.verticle

import com.enib.cai.web.services.Worms
import org.vertx.groovy.core.eventbus.EventBus

import com.enib.cai.web.services.Beers
import org.vertx.groovy.core.eventbus.EventBus;

import javax.inject.Inject;


/**
 * Created by Simon on 09/12/2014.
 */
class WormsWorkerVerticle extends AbstractGuiceVerticle
{
    @Inject
    private Worms worms

    @Override
    public start() {
        super.start()
        println "deploy Worms worker verticle"

        EventBus eb = vertx.eventBus

        eb.registerHandler("worms.service") { message ->
            String response = ""
            println "Message reçu : " + message.body

            try {
                switch (message.body) {
                    case "getAll":
                        response = worms.getWorms().encodePrettily()
                        break

                //other worms
                    default:
                        response = "NO CASE";
                }
            } catch (Exception exp) {
                response = "Huston we have a problem"
                exp.printStackTrace();
            }

            // Now reply to it
            message.reply(response)
        }
    }
}
