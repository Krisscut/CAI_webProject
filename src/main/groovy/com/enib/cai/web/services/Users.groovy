package com.enib.cai.web.services

import org.vertx.java.core.json.JsonObject

/**
 * Created by Simon on 13/12/2014.
 */
public interface Users
{
   public JsonObject createAccount (String username, String password, String email);
   public JsonObject login (String username, String password);
   public JsonObject logout(String token);
   public JsonObject authorize (String token);
   public JsonObject authentificateUser ();

}
