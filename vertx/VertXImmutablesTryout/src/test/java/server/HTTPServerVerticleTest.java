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
public class HTTPServerVerticleTest {

    private Vertx vertx;
    @Before
    public void setup(TestContext testContext) {
        vertx = Vertx.vertx();
        /**
         * This is used to know if the server is deployed correctly or any failures occurred.
         * It waits for the future.complete() or future.fail() in the server verticle to be called
         * */
        vertx.deployVerticle(HTTPServerVerticle.class.getName(),
                testContext.asyncAssertSuccess());
    }

    @Test
    public void receiveRequestTest(TestContext testContext){
        Async async = testContext.async();

        /*simulate client*/
        vertx.createHttpClient()
                .getNow(9090, "localhost", "/", response ->
                    response.handler(responseBody -> {
                        System.out.println(responseBody.toString());
                        testContext.assertTrue(responseBody.toString().contains("Welcome"));
                        async.complete();
                    }));
    }

    @After
    public void tearDown(TestContext testContext){
        vertx.close(testContext.asyncAssertSuccess());
    }
}
