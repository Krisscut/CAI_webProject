package com.enib.cai.web.verticle

import com.enib.cai.web.services.Equipments
import com.enib.cai.web.services.Users
import com.fasterxml.jackson.databind.ObjectMapper
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

    private String[] cmdAvailable = ["createAccount", "login", "logout", "authorize"]
    private String usage = "worms/getAll - Get all informations about the worms into the database \n\n" +
            "worms/get;ID - Replace ID with the identifier of the worm you want to know about"

    @Override
    public start()
    {
        super.start()
        println "deploy Users worker verticle"

        EventBus eb = vertx.eventBus

        eb.registerHandler("users.service") { message ->
            JsonObject response = new JsonObject()

            /* Split given URL to take parameters*/
            println "Message reçu : " + message.body

            HashMap<String,Object> result = new ObjectMapper().readValue(message.body, HashMap.class);
            JsonObject object = new JsonObject(message.body);

            String cmd = result["cmd"]
            HashMap<String,Object> headers = result["headers"]

            if (cmd in cmdAvailable)
            {
                switch (cmd)
                {
                    case "createAccount":
                        if (headers.containsKey("username") && headers.containsKey("password") )
                        {
                            response.putString("status", "ok")
                            response.putString("result", "now we can work")
                        }
                        else
                        {
                            response.putString("status", "error")
                            response.putString("result", "Not all parameters have been given")
                        }
                        break;
                    case "login":
                        response.putString("status", "ok")
                        response.putString("result", "login")
                        break

                    case "logout":
                        response.putString("status", "ok")
                        response.putString("result", "logout")
                        break

                    case "authorize":
                        response.putString("status", "ok")
                        response.putString("result", "authorize")
                        break

                    default:
                        response.putString("status", "error")
                        response.putString("result", "WTF")
                }
            }
            else
            {
                response.putString("status", "error")
                response.putString("result", "Unknow Command")
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

            // Now reply to it
            message.reply(response.encodePrettily())
        }
    }

}
