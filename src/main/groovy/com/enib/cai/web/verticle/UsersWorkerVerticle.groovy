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
                        if (headers.containsKey("username") && headers.containsKey("password") && headers.containsKey("email"))
                        {
                            JsonObject resultCreation = users.createAccount(headers["username"],headers["password"],headers["email"])
                            response = resultCreation
                            if (resultCreation.getString("status") == "ok")
                            {
                                //Send the welcome message
                                //https://github.com/vert-x/mod-mailer
                                JsonObject dataMail = new JsonObject()
                                dataMail.putString("from", "wormBuyer@gmail.com")
                                dataMail.putString("to", "simon.teneau@gmail.com")
                                dataMail.putString("subject", "Test envoie de mail")
                                dataMail.putString("body", "Hey !")

                                eb.send("mailer.my_mailer", dataMail) { answer ->
                                    println("I received a reply before the timeout of 5 seconds")
                                }
                            }
                        }
                        else
                        {
                            response.putString("status", "error")
                            response.putString("result", "Not all parameters have been given")
                        }
                        break;
                    case "login":
                        if (headers.containsKey("username") && headers.containsKey("password"))
                        {
                            response = users.login(headers["username"],headers["password"])
                        }
                        else
                        {
                            response.putString("status", "error")
                            response.putString("result", "Not all parameters have been given")
                        }
                        break;

                    case "logout":
                        if (headers.containsKey("token"))
                        {
                            response = users.logout(headers["token"])
                        }
                        else
                        {
                            response.putString("status", "error")
                            response.putString("result", "Not all parameters have been given")
                        }
                        break;

                    case "authorize":
                        if (headers.containsKey("token"))
                        {
                            response = users.authorize(headers["token"])
                        }
                        else
                        {
                            response.putString("status", "error")
                            response.putString("result", "Not all parameters have been given")
                        }
                        break;
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
