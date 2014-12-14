package com.enib.cai.web.verticle

import com.enib.cai.web.services.Worms
import org.vertx.groovy.core.eventbus.EventBus

import com.enib.cai.web.services.Beers
import org.vertx.groovy.core.eventbus.EventBus
import org.vertx.java.core.json.JsonObject;

import javax.inject.Inject;


/**
 * Created by Simon on 09/12/2014.
 */
class WormsWorkerVerticle extends AbstractGuiceVerticle
{
    @Inject
    private Worms worms

    private String usage = "worms/getAll - Get all informations about the worms into the database \n\n" +
                            "worms/get;ID - Replace ID with the identifier of the worm you want to know about"

    @Override
    public start() {
        super.start()
        println "deploy Worms worker verticle"

        EventBus eb = vertx.eventBus

        eb.registerHandler("worms.service") { message ->
            JsonObject response = new JsonObject()

            /* Split given URL to take parameters*/
            String[] cmd = message.body.split(";");

            println "Message reçu : " + message.body

            try {
                switch (cmd[0])
                {
                    case "getAll":
                        response.putString("status", "ok")
                        response.putArray("result", worms.getWorms())
                        break

                    case "get":
                        if (cmd.length == 2)
                        {
                            response.putString("status", "ok")
                            response.putObject("result",  worms.getWorm(cmd[1]))
                        }
                        else
                        {
                            response.putString("status", "error")
                            response.putString("result", usage)
                        }
                        break
                    //other worms
                    default:
                        response.putString("status", "Unknow Command")
                        response.putString("result", usage)
                }
            }
            catch (Exception exp)
            {
                response.putString("status", "error")
                response.putString("result", "Exception catched !")
                exp.printStackTrace();
            }

            // Now reply to it
            message.reply(response.encodePrettily())
        }
    }
}
