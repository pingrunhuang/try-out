package io.vertx.wiki.database;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(VertxUnitRunner.class)
public class WikiDatabaseServiceImplTest {

    private Vertx vertx;
    private WikiDatabaseService service;
    @Before
    public void prepare(TestContext context) throws InterruptedException {
        vertx = Vertx.vertx();

        JsonObject conf = new JsonObject()
                .put(WikiDatabaseVerticle.CONFIG_WIKIDB_JDBC_URL, "jdbc:hsqldb:mem:testdb;shutdown=true")
                .put(WikiDatabaseVerticle.CONFIG_WIKIDB_JDBC_MAX_POOL_SIZE, 4);

        vertx.deployVerticle(
                new WikiDatabaseVerticle(),
                new DeploymentOptions().setConfig(conf),
                context.asyncAssertSuccess(id ->
                    service = WikiDatabaseService.createProxy(vertx, WikiDatabaseVerticle.CONFIG_WIKIDB_QUEUE)
                ));
    }

    @After
    public void after(TestContext context){
        vertx.close(context.asyncAssertSuccess());
    }

    /*default timeout*/
    @Test(timeout=5000)
    public void asyncBehaviour(TestContext context){
        Vertx vertx = Vertx.vertx();
        Async async1 = context.async();
        Async async2 = context.async(3); // tick 3 times
        vertx.setTimer(100 , ar -> async1.complete());
        vertx.setPeriodic(100, ar->async2.countDown());// 3 times ticker with 100 periodic
    }

    @Test
    public void crud_operation(TestContext context){
        Async async = context.async();

        service.createPage("test", "some content", ar -> {
            service.fetchPage("test", context.asyncAssertSuccess(json->{
                context.assertTrue(json.getBoolean("found"));
                context.assertTrue(json.containsKey("id"));

                service.savePage(json.getInteger("id"), "Yo", context.asyncAssertSuccess(ar2->{
                    service.fetchAllPages(context.asyncAssertSuccess(array->{
                        context.assertEquals(1, array.size());

                        service.fetchPage("test", context.asyncAssertSuccess(json2->{
                            context.assertEquals("Yo", json2.getString("rawContent"));

                            service.deletePage(json2.getInteger("id"), ar3->{
                                service.fetchAllPages(context.asyncAssertSuccess(json3->{
                                    context.assertTrue(json3.isEmpty());
                                    async.complete();
                                }));
                            });
                        }));
                        }));
                    }));
                }) );
            });
        async.awaitSuccess(5000);
    }

    @Test
    public void fetchAllPages() {
    }

    @Test
    public void fetchPage() {
    }

    @Test
    public void createPage() {
    }

    @Test
    public void savePage() {
    }

    @Test
    public void deletePage() {
    }


}