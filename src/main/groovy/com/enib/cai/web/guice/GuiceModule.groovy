package com.enib.cai.web.guice;

import com.enib.cai.web.guice.provider.MongoProvider
import com.enib.cai.web.services.Articles
import com.enib.cai.web.services.Beers
import com.enib.cai.web.services.Bonuses
import com.enib.cai.web.services.Equipments
import com.enib.cai.web.services.Files
import com.enib.cai.web.services.Orders
import com.enib.cai.web.services.Users
import com.enib.cai.web.services.Worms;
import com.enib.cai.web.services.impl.FIlesImpl
import com.enib.cai.web.services.impl.MongoArticlesImpl
import com.enib.cai.web.services.impl.MongoBeersImpl
import com.enib.cai.web.services.impl.MongoBonusesImpl
import com.enib.cai.web.services.impl.MongoEquipmentsImpl
import com.enib.cai.web.services.impl.MongoOrdersImpl
import com.enib.cai.web.services.impl.MongoUsersImpl
import com.enib.cai.web.services.impl.MongoWormsImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.mongodb.DB

import static com.enib.cai.web.services.impl.MongoArticlesImpl.*;


public class GuiceModule extends AbstractModule {

  private def config

  public GuiceModule(def config){
    this.config = config
  }

  @Override
  protected void configure() {
    //get the vertx configuration
    Map<String,Object> mongoConfig = config["mongo"]

    bind(new TypeLiteral<Map<String, Object>>() {})
        .annotatedWith(Names.named("mongo"))
        .toInstance(mongoConfig);

    bind(DB.class).toProvider(MongoProvider.class).in(Singleton.class);

    //Bindings
    bind(Beers.class).to(MongoBeersImpl.class).in(Singleton.class);
    bind(Files.class).to(FIlesImpl.class).in(Singleton.class);
    bind(Worms.class).to(MongoWormsImpl.class).in(Singleton.class);
    bind(Bonuses.class).to(MongoBonusesImpl.class).in(Singleton.class);
    bind(Equipments.class).to(MongoEquipmentsImpl.class).in(Singleton.class);
    bind(Articles.class).to(MongoArticlesImpl.class).in(Singleton.class);
    bind(Users.class).to(MongoUsersImpl.class).in(Singleton.class);
    bind(Orders.class).to(MongoOrdersImpl.class).in(Singleton.class)
  }
}
