package com.enib.cai.web.services

import org.vertx.java.core.json.JsonObject

/**
 * Created by Simon on 13/12/2014.
 */
public interface Users
{
   public void createAccount ();
   public JsonObject authentificateUser ();

}
