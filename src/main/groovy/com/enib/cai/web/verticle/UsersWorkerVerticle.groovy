package com.enib.cai.web.verticle

import com.enib.cai.web.services.Equipments
import com.enib.cai.web.services.Users
import org.vertx.groovy.core.eventbus.EventBus
import org.vertx.java.core.json.JsonObject

import javax.inject.Inject

/**
 * Created by Simon on 13/12/2014.
 */
class UsersWorkerVerticle extends AbstractGuiceVerticle
{
    @Inject
    private Users users

    private String[] cmdAvailable = ["createAccount", "authentificateConnexion"]
    private String usage = "worms/getAll - Get all informations about the worms into the database \n\n" +
            "worms/get;ID - Replace ID with the identifier of the worm you want to know about"

    @Override
    public start()
    {
        super.start()
        println "deploy Users worker verticle"

        EventBus eb = vertx.eventBus

        eb.registerHandler("users.service") { message ->
            String response = ""

            /* Split given URL to take parameters*/
            println "Message reçu : " + message.body

            JsonObject object = new JsonObject(message.body);

            String cmd = object.getString("cmd")
            JsonObject headers = object.getObject("headers")


            println headers.hasProperty("username")
            println headers.hasProperty("password")
            if (cmd in cmdAvailable)
            {
                switch (cmd)
                {
                    case "createAccount":
                        if (headers.hasProperty("username") && headers.hasProperty("password") )
                        {
                            response = "yeah, now we can work !"
                        }
                        else
                        {
                            response = "not all parameters have been given"
                        }
                        break;
                    default:
                        response = "WTF !"
                }
            }
            else
            {
                response = "Unknow cmd"
            }


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
            println response
            // Now reply to it
            message.reply(response)
        }
    }

}
