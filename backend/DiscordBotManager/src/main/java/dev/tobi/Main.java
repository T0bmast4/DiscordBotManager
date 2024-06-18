package dev.tobi;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.EnumSet;

public class Main {
    private Vertx vertx;

    public static void main(String[] args) {
        new Main().startServer();
    }

    private void startServer() {
        vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        //MTI1MjMyOTI1NjkyNzYyOTQyMw.GRQPtW.aeJj7t23m8mGknRpZ3nbBP0PW4Ukk5vbpDTQCk
        router.route("/api/bot/start").handler(ctx -> {

            ctx.request().bodyHandler(body -> {
                JsonObject jsonBody = body.toJsonObject();
                String token = jsonBody.getString("token");

                System.out.println(token);

                if (token == null || token.isEmpty()) {
                    JsonObject response = new JsonObject()
                            .put("result", "Failed")
                            .put("message", "Token is missing or empty");
                    ctx.response()
                            .putHeader("Content-Type", "application/json")
                            .setStatusCode(400)
                            .end(response.encodePrettily());
                    return;
                }

                try {
                    JDA jda = JDABuilder.createDefault(token)
                            .enableIntents(EnumSet.of(GatewayIntent.GUILD_MESSAGES))
                            .disableCache(EnumSet.of(CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS))
                            .build();

                    jda.addEventListener(new Main() {
                        @Override
                        public void onReady(ReadyEvent event) {
                            JsonObject response = new JsonObject()
                                    .put("result", "Success")
                                    .put("message", "Bot started successfully");
                            ctx.response()
                                    .putHeader("Content-Type", "application/json")
                                    .setStatusCode(200)
                                    .end(response.encodePrettily());
                        }
                    });


                } catch (InvalidTokenException e) {
                    e.printStackTrace();
                    JsonObject response = new JsonObject()
                            .put("result", "Failed")
                            .put("message", "Invalid Token");
                    ctx.response()
                            .putHeader("Content-Type", "application/json")
                            .setStatusCode(401)
                            .end(response.encodePrettily());
                } catch (Exception e) {
                    e.printStackTrace();
                    JsonObject response = new JsonObject()
                            .put("result", "Failed")
                            .put("message", "An unexpected error occurred: " + e.getMessage());
                    ctx.response()
                            .putHeader("Content-Type", "application/json")
                            .setStatusCode(500)
                            .end(response.encodePrettily());
                }
            });
        });

        server.requestHandler(router).listen(8765, result -> {
            if (result.succeeded()) {
                System.out.println("Server started on port 8765");
            } else {
                result.cause().printStackTrace();
            }
        });
    }
}