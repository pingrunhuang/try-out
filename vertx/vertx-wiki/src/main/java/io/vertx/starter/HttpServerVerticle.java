package io.vertx.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.ext.web.templ.freemarker.FreeMarkerTemplateEngine;

public class HttpServerVerticle extends AbstractVerticle {

    public static final String CONFIG_HTTP_SERVER_PORT = "http.server.port";
    public static final String CONFIG_WIKIDB_QUEUE = "wikidb.queue";
    private String wikiDbQueue = "wikidb.queue";
    private FreeMarkerTemplateEngine templateEngine;



    @Override
    public void start(Future<Void> future){
        this.vertx.eventBus();
    }



}
