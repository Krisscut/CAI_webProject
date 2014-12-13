package com.enib.cai.web.verticle

import org.vertx.groovy.core.eventbus.EventBus
import org.vertx.groovy.core.http.RouteMatcher
import org.vertx.groovy.platform.Verticle

/**
 * Created by Simon on 02/12/2014.
 */
class WebMainVerticle extends Verticle
{

    public start() {
        println "deploy Worm Buyer verticle"

        EventBus eb = vertx.eventBus
        eb.javaEventBus().setDefaultReplyTimeout(5000)

        println "Launch the verticle routeMatcher"

        def rm = new RouteMatcher()
        
        // Extract the params from the uri

        // API Route Matcher send to the event bus

        rm.get("/api/worms/:cmd") { req ->
            String cmd = req.params.get("cmd")

            // send the message throw the eventbus
            eb.send("worms.service", cmd) { response ->
                // get the response from the eventbus and send it as response
                println("I received a reply before the timeout of 5 seconds");
                req.response.end(response.body)
            }
        }

        rm.get("/api/equipments/:cmd") { req ->
            String cmd = req.params.get("cmd")

            // send the message throw the eventbus
            eb.send("equipments.service", cmd) { response ->
                // get the response from the eventbus and send it as response
                println("I received a reply before the timeout of 5 seconds");
                req.response.end(response.body)
            }
        }

        rm.get("/api/bonuses/:cmd") { req ->
            String cmd = req.params.get("cmd")

            // send the message throw the eventbus
            eb.send("bonuses.service", cmd) { response ->
                // get the response from the eventbus and send it as response
                println("I received a reply before the timeout of 5 seconds");
                req.response.end(response.body)
            }
        }

        rm.get("/api/mongo/:id") { req ->
            String id = req.params.get("id")
            println "Received command through URI " + id

            String message = "default"
            if ( id == "all")
            {
                message = "getAll"

            }
            else
            {
                //make regex here !
            }

            // send the message throw the eventbus
            eb.send("mongo.service", message) { response ->
                // get the response from the eventbus and send it as response
                println("I received a reply before the timeout of 5 seconds");
                req.response.end(response.body)
            }
        }

        // otherwise  send file
        rm.noMatch() { req ->
            def file = req.uri == "/" ? "index.html" : req.uri
            req.response.sendFile "web/$file"
        }

        vertx.createHttpServer().requestHandler(rm.asClosure()).listen(8080)

    }
}
