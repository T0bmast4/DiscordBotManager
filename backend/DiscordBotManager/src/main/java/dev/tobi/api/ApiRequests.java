package dev.tobi.api;

import dev.tobi.bot.BotManager;
import dev.tobi.bot.DcBot;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class ApiRequests {

    public void setupRoutes(Router router) {
        router.route("/api/bot/start").handler(this::handleStartBot);
        router.route("/api/bot/stop").handler(this::handleStopBot);
        router.route("/api/bot/isOnline").handler(this::handleIsBotOnline);
    }

    public void handleError(RoutingContext ctx, int statusCode, String message) {
        ApiResponse response = new ApiResponse("Failed", message);
        ctx.response()
                .putHeader("Content-Type", "application/json")
                .setStatusCode(statusCode)
                .end(response.toJson().encodePrettily());
    }

    public boolean isTokenValid(RoutingContext ctx, String token) {
        if (token == null || token.isEmpty()) {
            handleError(ctx, 400, "Token is missing or empty");
            return false;
        }
        return true;
    }

    private void handleStartBot(RoutingContext ctx) {
        ctx.request().bodyHandler(body -> {
            JsonObject jsonBody = body.toJsonObject();
            String token = jsonBody.getString("token");
            if (!isTokenValid(ctx, token)) return;

            try {
                BotManager.startBot(token);
                ApiResponse response = new ApiResponse("Success", "Bot started successfully");
                ctx.response()
                        .putHeader("Content-Type", "application/json")
                        .setStatusCode(200)
                        .end(response.toJson().encodePrettily());

            } catch (Exception e) {
                handleError(ctx, 500, "Error starting bot: " + e.getMessage());
            }
        });
    }

    private void handleStopBot(RoutingContext ctx) {
        ctx.request().bodyHandler(body -> {
            JsonObject jsonBody = body.toJsonObject();
            String token = jsonBody.getString("token");
            if (!isTokenValid(ctx, token)) return;

            try {
                BotManager.stopBot(token);

                ApiResponse response = new ApiResponse("Success", "Bot stopped successfully");
                ctx.response()
                        .putHeader("Content-Type", "application/json")
                        .setStatusCode(200)
                        .end(response.toJson().encodePrettily());

            } catch (Exception e) {
                handleError(ctx, 500, "Error stopping bot: " + e.getMessage());
            }
        });
    }

    private void handleIsBotOnline(RoutingContext ctx) {
        ctx.request().bodyHandler(body -> {
            JsonObject jsonBody = body.toJsonObject();
            String token = jsonBody.getString("token");
            if (!isTokenValid(ctx, token)) return;

            try {
                boolean isOnline = BotManager.getBots().containsKey(token);
                ApiResponse response = new ApiResponse("Success", String.valueOf(isOnline));
                ctx.response()
                        .putHeader("Content-Type", "application/json")
                        .setStatusCode(200)
                        .end(response.toJson().encodePrettily());

            } catch (Exception e) {
                handleError(ctx, 500, "Error checking if bot is online: " + e.getMessage());
            }
        });
    }

    /* Command
    {
      "token": "YOUR_BOT_TOKEN",
      "commandName": "dynamicCommand",
      "description": "This command executes multiple dynamic actions",
      "actions": [
        {
          "actionName": "SEND_MESSAGE",
          "parameters": {
            "message": "Hello, World!"
          }
        },
        {
          "actionName": "REACT_WITH_EMOJI",
          "parameters": {
            "emoji": "🎉"
          }
        },
        {
          "actionName": "MENTION_USER",
          "parameters": {
            "userId": "123456789"
          }
        }
      ]
    }
     */

    private void handleCustomCommand(RoutingContext ctx) {
        ctx.request().bodyHandler(body -> {
            JsonObject jsonBody = body.toJsonObject();
            String token = jsonBody.getString("token");
            if (!isTokenValid(ctx, token)) return;

            String commandName = jsonBody.getString("commandName");
            String description = jsonBody.getString("description");
            JsonArray actions = jsonBody.getJsonArray("actions");

            DcBot bot = BotManager.getBotByToken(token);

            bot.getCommandManager().registerCommand(new CustomCommand());
        });
    }
}
