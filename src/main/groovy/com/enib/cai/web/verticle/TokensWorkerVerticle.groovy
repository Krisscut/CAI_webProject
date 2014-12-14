package com.enib.cai.web.verticle

import com.enib.cai.web.services.Tokens
import org.vertx.groovy.core.eventbus.EventBus
import org.vertx.java.core.json.JsonObject

import javax.inject.Inject

/**
 * Created by Simon on 14/12/2014.
 */
class TokensWorkerVerticle extends AbstractGuiceVerticle
{
    @Inject
    private Tokens tokens

    private String usage = "worms/getAll - Get all informations about the worms into the database \n\n" +
                            "worms/get;ID - Replace ID with the identifier of the worm you want to know about"

    @Override
    public start()
    {
        super.start()
        println "deploy Tokens worker verticle"

        EventBus eb = vertx.eventBus

        //Maintenance timer ( one shot + periodically )
        tokens.cleanOldToken()
        def timerTokenAutoRemove = vertx.setPeriodic(1000 * 60 * (20 + (int)(Math.random()*60)) ) { timerTokenAutoRemove ->
            tokens.cleanOldToken()
        }

        eb.registerHandler("tokens.service") { message ->
            JsonObject response = new JsonObject()

            /* Split given URL to take parameters*/
            println "Message reçu : " + message.body

            response.putString("status", "error")
            response.putString("result", "default Answer")

            // Now reply to it
            message.reply(response.encodePrettily())
        }
    }



}
