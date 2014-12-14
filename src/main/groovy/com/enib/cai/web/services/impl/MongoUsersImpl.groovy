package com.enib.cai.web.services.impl

import com.enib.cai.web.services.Users
import com.mongodb.DB
import org.vertx.java.core.json.JsonObject

import javax.inject.Inject

/**
 * Created by Simon on 13/12/2014.
 */
class MongoUsersImpl implements Users
{
    // MongoDB driver
    @Inject
    private DB db;

    @Override
    void createAccount() {

    }

    @Override
    JsonObject authentificateUser() {
        return null
    }
}
