package server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class HTTPServerVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> future){
        vertx.createHttpServer()
                .requestHandler(request -> request.response().end("Welcome to Vert.x Intro"))
                .listen(9090, response -> {
                    if (response.succeeded()){
                        future.complete();
                    }else {
                        future.fail(response.cause());
                    }
                });
    }
}
