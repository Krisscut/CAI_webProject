package com.enib.cai.web.verticle

import com.enib.cai.web.services.Orders
import org.vertx.groovy.core.eventbus.EventBus

import javax.inject.Inject

/**
 * Created by Simon on 14/12/2014.
 */
class OrdersWorkerVerticle extends AbstractGuiceVerticle
{
    @Inject
    private Orders orders

    private String usage = "worms/getAll - Get all informations about the worms into the database \n\n" +
            "worms/get;ID - Replace ID with the identifier of the worm you want to know about"

    @Override
    public start()
    {
        super.start()
        println "deploy Orders worker verticle"

        EventBus eb = vertx.eventBus

        eb.registerHandler("orders.service") { message ->
            String response = ""

            /* Split given URL to take parameters*/
            println "Message reçu : " + message.body

            /*
            try {
                switch (message.body)
                {
                    case "getAll":
                        response = users.getEquipments().encodePrettily()
                        break

                    case "get":
                        if (cmd.length == 2)
                        {
                            response = equipments.getEquipment(cmd[1]).encodePrettily()
                        }
                        else
                        {
                            response = usage
                        }
                        break
                //other worms
                    default:
                        response = "NO CASE";
                }
            } catch (Exception exp) {
                response = "Huston we have a problem"
                exp.printStackTrace();
            }
            */
            // Now reply to it
            message.reply("Default answer")
        }
    }
}
