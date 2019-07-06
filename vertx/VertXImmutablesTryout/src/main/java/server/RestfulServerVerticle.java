package server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import models.Flight;
import models.ImmutableFlight;

import java.util.ArrayList;
import java.util.List;

public class RestfulServerVerticle extends AbstractVerticle {

    private List<Flight> flights = new ArrayList<>();

    @Override
    public void init(Vertx vertx, Context context) {
        this.vertx = vertx;
        this.context = context;
        this.flights.add(ImmutableFlight.builder().no(111).departure("beijing").destination("bali").build());
        this.flights.add(ImmutableFlight.builder().no(222).departure("beijing").destination("hk").build());
        this.flights.add(ImmutableFlight.builder().no(333).departure("shenzhen").destination("france").build());
    }

    /**
     * this method is called when server started
     * */
    @Override
    public void start(Future<Void> startFuture){

        Router router = Router.router(vertx);
        router.get("/api/flights/:no").handler(this::getFlights);

        // add router to server
        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(8080, result -> {
                    if (result.succeeded()){
                        startFuture.complete();
                    }else{
                        startFuture.fail(result.cause());
                    }
                });
    }

    /**
     * Handler method
     * */
    private void getFlights(RoutingContext routingContext) {
        int no = Integer.parseInt(routingContext.request().getParam("no"));

        Flight flight = null;
        for (Flight f : this.flights){
            if (f.getNo() == no){
                flight = f;
            }
        }

        assert flight != null;

        routingContext.response()
                .putHeader("content-type", "application/json")
                .setStatusCode(200)
                .end(flight.toString());
    }

}
