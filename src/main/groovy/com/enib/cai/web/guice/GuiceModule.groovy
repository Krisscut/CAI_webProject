package com.enib.cai.web.guice;

import com.enib.cai.web.guice.provider.MongoProvider
import com.enib.cai.web.services.Beers;
import com.enib.cai.web.services.Files
import com.enib.cai.web.services.Worms;
import com.enib.cai.web.services.impl.FIlesImpl
import com.enib.cai.web.services.impl.MongoBeersImpl
import com.enib.cai.web.services.impl.MongoWormsImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.mongodb.DB;


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
  }
}
