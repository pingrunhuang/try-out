package io.vertx.starter;

import com.github.rjeschke.txtmark.Processor;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.common.template.TemplateEngine;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.templ.freemarker.FreeMarkerTemplateEngine;

import java.util.Date;
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
  private static final String EMPTY_PAGE_MARKDOWN = "# A new page\n" +
          "\n" +
          "Feel-free to write in Markdown!\n";

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
    router.get("/wiki/:page").handler(this::pageRenderingHandler);
    router.post().handler(BodyHandler.create());
    router.post("/save").handler(this::pageUpdateHandler);
    router.post("/create").handler(this::pageCreateHandler);
    router.post("/delete").handler(this::pageDeletionHandler);

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


  private void pageRenderingHandler(RoutingContext context) {
    String page = context.request().getParam("page");
    client.getConnection(car -> {
      if (car.succeeded()) {
        SQLConnection connection = car.result();
        connection.queryWithParams(SQL_GET_PAGE, new JsonArray().add(page), fetch -> {
          connection.close();
          if (fetch.succeeded()) {
            JsonArray row = fetch.result().getResults()
                    .stream()
                    .findFirst()
                    .orElseGet(() -> new JsonArray().add(-1).add(EMPTY_PAGE_MARKDOWN));
            Integer id = row.getInteger(0); String rawContent = row.getString(1);
            context.put("title", page);
            context.put("id", id);
            context.put("newPage", fetch.result().getResults().size() == 0 ? "yes" : "no"); context.put("rawContent", rawContent);
            context.put("content", Processor.process(rawContent)); // The Processor class comes from the txtmark Markdown rendering library that we use.
            context.put("timestamp", new Date().toString());
            templateEngine.render(context.data(), "templates/page.ftl", ar -> {
              if (ar.succeeded()) {
                context.response().putHeader("Content-Type", "text/html");
                context.response().end(ar.result());
              } else {
                context.fail(ar.cause());
              }
            });
          } else {
            context.fail(fetch.cause());
          }
      });
    } else {
        context.fail(car.cause());
    }
    });
  }

  /**
   * Key / value data stored in the RoutingContext object is made available as FreeMarker variable accessed with ${params} in ftl page
   * */
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


  private void pageCreateHandler(RoutingContext context){
    String pageName = context.request().getParam("name");
    String location = "/wiki/" + pageName;
    if (pageName == null || pageName.isEmpty()){
      location = "/";
    }

    context.response().setStatusCode(303);
    context.response().putHeader("header", location);
    context.response().end();
  }

  /**
   * sql update or sql insert related
   * */
  private void pageUpdateHandler(RoutingContext context) {
    String id = context.request().getParam("id");
    String title = context.request().getParam("title");
    String markdown = context.request().getParam("markdown");
    boolean newPage = "yes".equals(context.request().getParam("newPage"));
    this.client.getConnection(car -> {
      if (car.succeeded()) {
        SQLConnection connection = car.result();
        String sql = newPage ? SQL_CREATE_PAGE : SQL_SAVE_PAGE; JsonArray params = new JsonArray();
        if (newPage) {
          params.add(title).add(markdown); } else {
          params.add(markdown).add(id);
        }
        connection.updateWithParams(sql, params, res -> {
          connection.close();
          if (res.succeeded()) {
            context.response().setStatusCode(303);
            context.response().putHeader("Location", "/wiki/" + title); context.response().end();
          } else {
            context.fail(res.cause());
          }
        });
      } else {
        context.fail(car.cause());
      }
    });
  }
  private void pageDeletionHandler(RoutingContext context) {
    String id = context.request().getParam("id");
    client.getConnection(car -> {
      if (car.succeeded()) {
        SQLConnection connection = car.result(); connection.updateWithParams(SQL_DELETE_PAGE, new JsonArray().add(id), res -> {
          connection.close();
          if (res.succeeded()) {
            context.response().setStatusCode(303); context.response().putHeader("Location", "/"); context.response().end();
          } else {
            context.fail(res.cause());
          }
        });
      } else {
        context.fail(car.cause());
      }
    });
  }
}
