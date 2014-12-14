package com.enib.cai.web.verticle

import com.enib.cai.web.guice.GuiceModule
import com.google.inject.Guice
import com.google.inject.Injector
import org.vertx.groovy.platform.Verticle


class AbstractGuiceVerticle extends Verticle {

  @Override
  public start() {
    println("inject dependencies")
    Injector injector = Guice.createInjector(new GuiceModule(container.config))
    injector.injectMembers(this)
  }
}
