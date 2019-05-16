package io.vertx.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.common.template.TemplateEngine;
import io.vertx.ext.web.templ.freemarker.FreeMarkerTemplateEngine;

import java.util.List;
import java.util.stream.Collectors;

public class MainVerticle extends AbstractVerticle {

  private JDBCClient client;
  private final static Logger logger = LoggerFactory.getLogger(MainVerticle.class);
  private TemplateEngine templateEngine;

  private static final String SQL_CREATE_PAGES_TABLE = "create table if not exists Pages (Id integer identity primary key, Name varchar(255) unique, Content clob)";
  private static final String SQL_GET_PAGE = "select Id, Content from Pages where Name = ?";
  private static final String SQL_CREATE_PAGE = "insert into Pages values (NULL, ?, ?)";
  private static final String SQL_SAVE_PAGE = "update Pages set Content = ? where Id = ?";
  private static final String SQL_ALL_PAGES = "select Name from Pages";
  private static final String SQL_DELETE_PAGE = "delete from Pages where Id = ?";

  @Override
  public void start() {
    vertx.createHttpServer()
        .requestHandler(req -> req.response().end("Hello Vert.x!"))
        .listen(8080);
  }

  @Override
  public void start(Future<Void> future){
    Future<Void> steps = prepareDatabaseHandler().compose(v -> startHTTPServerHandler());
    /*set handler to be called once the steps future get completed*/
    steps.setHandler(asyncResult -> {
      if(asyncResult.succeeded()){
        future.complete();
      }else{
        future.fail(asyncResult.cause());
      }
    });
  }

  private Future<Void> prepareDatabaseHandler(){
    Future<Void> future = Future.future();

    /*create a jdbc client attach to this vertx where all verticles known by this vertx could use*/
    client = JDBCClient.createShared(this.vertx, new JsonObject()
            .put("url", "jdbc:hsqldb:file:db/wiki")
            .put("driver_class", "org.hsqldb.jdbcDriver")
            .put("max_pool_size", 30));

    client.getConnection(asyncResult -> {
      if (asyncResult.failed()){
        logger.error("Error oppening db connection...");
        future.fail(asyncResult.cause());
      }else{
        SQLConnection conn = asyncResult.result();
        conn.execute(SQL_CREATE_PAGES_TABLE, create ->{
          conn.close();
          if (create.succeeded()){
            future.complete();
          }else{
            future.fail(create.cause());
          }
        });
      }
    });

    return future;
  }

  private Future<Void> startHTTPServerHandler(){
    Future<Void> future = Future.future();

    Router router = Router.router(this.vertx);
    HttpServer server = this.vertx.createHttpServer();
    this.templateEngine = FreeMarkerTemplateEngine.create(this.vertx);

    router.get("/").handler(this::indexHandler);

    server.requestHandler(router)
            .listen(8080, asyncResult -> {
              if (asyncResult.succeeded()){
                logger.info("HTTP server running on port 8080");
                future.complete();
              }else{
                logger.error("HTTP server not started...");
                future.fail(asyncResult.cause());
              }
            });

    return future;
  }

  private void indexHandler(RoutingContext context) {
    this.client.getConnection(asyncResult -> {
      if (asyncResult.succeeded()){
        SQLConnection conn = asyncResult.result();
        conn.query(SQL_ALL_PAGES, result -> {
          conn.close();
          if (result.succeeded()){
            List<String> pages = result.result()
            .getResults()
            .stream()
            .map(json -> json.getString(0))
            .sorted()
            .collect(Collectors.toList());

            context.put("title", "Wiki home");
            context.put("pages", pages);
            this.templateEngine.render(context.data(), "templates/index.ftl", asyncResult1 -> {
              if (asyncResult1.succeeded()){
                context.response().putHeader("Content-Type", "text/html");
                context.response().end(asyncResult1.result());
              }else{
                context.fail(asyncResult1.cause());
              }
            });
          }else{
            context.fail(result.cause());
          }
        });
      }else{
        context.fail(asyncResult.cause());
      }
    });
  }



}
