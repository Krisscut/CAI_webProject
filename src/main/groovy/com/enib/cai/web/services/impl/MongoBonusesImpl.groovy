package com.enib.cai.web.services.impl


import com.enib.cai.web.model.Bonus
import com.enib.cai.web.services.Articles
import com.enib.cai.web.services.Bonuses
import org.vertx.java.core.json.JsonArray
import org.vertx.java.core.json.JsonObject

import javax.inject.Inject

/**
 * Created by Simon on 13/12/2014.
 */
class MongoBonusesImpl implements Bonuses
{
    @Inject
    private Articles articles

    @Override
    JsonArray getBonuses() {
        JsonArray jsonArray = articles.getArticles("bonus")

        return jsonArray
    }

    @Override
    JsonObject getBonus(String id) throws Exception {
        JsonObject jsonObject = articles.getArticle("bonus", id)

        return jsonObject
    }

    @Override
    JsonObject addBonus(Bonus bonus) throws Exception {
        JsonObject jsonObject = articles.addArticle("bonus", bonus)
    }
}
