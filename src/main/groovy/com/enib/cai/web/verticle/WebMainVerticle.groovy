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

        /*
   RouteMatcher routeMatcher = new RouteMatcher()


   // API Route Matcher send to the event bus
   routeMatcher.get("/api/beers/:id") { req ->
     String id = req.params.get("id")
     // send the message throw the eventbus
     eb.send("beers.service", id) { response ->
       // get the response from the eventbus and send it as response
       println("I received a reply before the timeout of 5 seconds");
       req.response.end(response.body)
     }
   }


   // API Route Matcher send to the event bus
   routeMatcher.get("/img/:name") { req  ->
     String filename = req.get("name")

     eb.send("images.service", filename) { response ->
       req.response.putHeader("Content-Length", Integer.toString(response.body.length))
       req.response << response.body()
       req.response.end();
     }
   }

   // otherwise  send file
   routeMatcher.noMatch() { req ->
     String file = "";
     if (req.path.equals("/")) {
       file = "index.html";
     } else if (!req.path.contains("..")) {
       file = req.path();
     }
     req.response.sendFile("web/" + file);
   }
   */
        //def server = vertx.createHttpServer();
        //server.requestHandler{ request -> println "A request has arrived on the server!" }.listen(8080, "localhost")
        //vertx.createHttpServer().requestHandler(routeMatcher.asClosure()).listen(44081)


        /*
          def server = vertx.createHttpServer()
          def routeMatcher = new RouteMatcher()

          routeMatcher.noMatch
                  {
              req -> req.response.end "Nothing matched"
          }
          */

        /*
          server.requestHandler { req ->
              req.response.end "<html><body><h1>Hello from vert.x!</h1></body></html>"
          }.listen(44081, "localhost");
          */

        //server.requestHandler {routeMatcher.asClosure()}.listen(44081, "localhost");

        def rm = new RouteMatcher()

        // Extract the params from the uri

        // API Route Matcher send to the event bus

        rm.get("/api/worms/:id") { req ->
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
            eb.send("worms.service", message) { response ->
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

        /*
        // API Route Matcher send to the event bus
        rm.get("/img/:name") { req  ->
            String filename = req.get("name")

            eb.send("images.service", filename) { response ->
                req.response.putHeader("Content-Length", Integer.toString(response.body.length))
                req.response << response.body()
                req.response.end();
            }
        }
        */

        // otherwise  send file
        rm.noMatch() { req ->
            def file = req.uri == "/" ? "index.html" : req.uri
            req.response.sendFile "web/$file"
        }

// Catch all - serve the index page

        /*
        rm.getWithRegEx('.*') { req ->

            req.response.sendFile "route_match/index.html"
        }
          */

        vertx.createHttpServer().requestHandler(rm.asClosure()).listen(8080)

    }
}
