package server;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class RestServerVerticleTest {
    private Vertx vertx;
    @Test
    public void getFlightsTest(TestContext testContext){
        Async async = testContext.async();

        vertx.createHttpClient()
                .getNow(8080, "localhost", "/api/flights/111", response -> {
                    response.handler(responseBody -> {
                        System.out.println(responseBody);
                        testContext.assertTrue(responseBody.toString().contains("111"));
                        async.complete();
                    });
                });

    }

    @Before
    public void setup(TestContext testContext) {
        vertx = Vertx.vertx();

        vertx.deployVerticle(RestfulServerVerticle.class.getName(), testContext.asyncAssertSuccess());
    }

    @After
    public void tearDown(TestContext testContext) {
        vertx.close(testContext.asyncAssertSuccess());
    }

}
