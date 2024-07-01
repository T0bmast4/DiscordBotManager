package dev.tobi;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class ApiRequests {

    public static void setupRoutes(Router router) {
        router.route("/api/bot/start").handler(ctx -> {
            ctx.request().bodyHandler(body -> {
                JsonObject jsonBody = body.toJsonObject();
                String token = jsonBody.getString("token");

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
                    BotManager.startBot(token);

                    JsonObject response = new JsonObject()
                            .put("result", "Success")
                            .put("message", "Bot started successfully");
                    ctx.response()
                            .putHeader("Content-Type", "application/json")
                            .setStatusCode(200)
                            .end(response.encodePrettily());
                } catch (Exception e) {
                    JsonObject response = new JsonObject()
                            .put("result", "Failed")
                            .put("message", "Error starting bot: " + e.getMessage());
                    ctx.response()
                            .putHeader("Content-Type", "application/json")
                            .setStatusCode(500)
                            .end(response.encodePrettily());
                }
            });
        });

        router.route("/api/bot/stop").handler(ctx -> {
            ctx.request().bodyHandler(body -> {
                JsonObject jsonBody = body.toJsonObject();
                String token = jsonBody.getString("token");

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
                    BotManager.stopBot(token);

                    JsonObject response = new JsonObject()
                            .put("result", "Success")
                            .put("message", "Bot stopped successfully");
                    ctx.response()
                            .putHeader("Content-Type", "application/json")
                            .setStatusCode(200)
                            .end(response.encodePrettily());
                } catch (Exception e) {
                    JsonObject response = new JsonObject()
                            .put("result", "Failed")
                            .put("message", "Error stopping bot: " + e.getMessage());
                    ctx.response()
                            .putHeader("Content-Type", "application/json")
                            .setStatusCode(500)
                            .end(response.encodePrettily());
                }
            });
        });



        router.route("/api/bot/isOnline").handler(ctx -> {
            ctx.request().bodyHandler(body -> {
                JsonObject jsonBody = body.toJsonObject();
                String token = jsonBody.getString("token");

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
                    if(BotManager.getBots().containsKey(token)) {
                        JsonObject response = new JsonObject()
                                .put("result", "Success")
                                .put("message", true);
                        ctx.response()
                                .putHeader("Content-Type", "application/json")
                                .setStatusCode(200)
                                .end(response.encodePrettily());
                    } else {
                        JsonObject response = new JsonObject()
                                .put("result", "Success")
                                .put("message", false);
                        ctx.response()
                                .putHeader("Content-Type", "application/json")
                                .setStatusCode(200)
                                .end(response.encodePrettily());
                    }
                } catch (Exception e) {
                    JsonObject response = new JsonObject()
                            .put("result", "Failed")
                            .put("message", "Error checking if bot is online: " + e.getMessage());
                    ctx.response()
                            .putHeader("Content-Type", "application/json")
                            .setStatusCode(500)
                            .end(response.encodePrettily());
                }
            });
        });
    }
}
