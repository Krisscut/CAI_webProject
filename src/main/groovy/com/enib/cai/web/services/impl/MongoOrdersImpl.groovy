package com.enib.cai.web.services.impl

import com.enib.cai.web.services.Orders
import com.mongodb.DB

import javax.inject.Inject

/**
 * Created by Simon on 14/12/2014.
 */
class MongoOrdersImpl implements Orders
{
    // MongoDB driver
    @Inject
    private DB db;



}
