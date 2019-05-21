package io.vertx.wiki.database;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;

import java.util.HashMap;
import java.util.List;

@ProxyGen
@VertxGen
public interface WikiDatabaseService {

    /*allows operations to be chained by returning the service interface*/
    @Fluent
    WikiDatabaseService fetchAllPages(Handler<AsyncResult<JsonArray>> resultHandler);

    /**
     * Java primitive types json object is passed as parameters
     * Other customized object should with @DataObject annotation
     * */
    @Fluent
    WikiDatabaseService fetchPage(String name, Handler<AsyncResult<JsonObject>> resultHandler);

    @Fluent
    WikiDatabaseService createPage(String title, String markdown, Handler<AsyncResult<Void>> resultHandler);

    @Fluent
    WikiDatabaseService savePage(int id, String markdown, Handler<AsyncResult<Void>> resultHandler);

    @Fluent
    WikiDatabaseService deletePage(int id, Handler<AsyncResult<Void>> resultHandler);

    /**
     * ignore by code generator
     * */
    @GenIgnore
    static WikiDatabaseService create(JDBCClient client,
                                      HashMap<SqlQuery, String> sqlQueries,
                                      Handler<AsyncResult<WikiDatabaseService>> readyHandler){
        return new WikiDatabaseServiceImpl(client, sqlQueries, readyHandler);
    }
    @GenIgnore
    static WikiDatabaseService createProxy(Vertx vertx, String address) {
        return new WikiDatabaseServiceVertxEBProxy(vertx, address);
    }

    @Fluent
    WikiDatabaseService fetchAllPagesData(Handler<AsyncResult<List<JsonObject>>> resultHandler);
}
