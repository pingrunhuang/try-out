package server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import sun.rmi.runtime.Log;

import java.util.logging.Logger;

public class SimpleVerticle extends AbstractVerticle {

    private Logger LOGGER = Logger.getLogger(this.getClass().getName());
    @Override
    public void start(Future<Void> startFuture) throws Exception {
        LOGGER.info("Welcome to vertx");
    }

    @Override
    public void stop(){
        LOGGER.info("Shutting down app");
    }

    public static void main(String[] args){
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new SimpleVerticle());
    }

}
