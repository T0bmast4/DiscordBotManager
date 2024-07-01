package dev.tobi;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.EnumSet;

public class Main extends ListenerAdapter {
    private Vertx vertx;

    public static void main(String[] args) {
        new Main().startServer();
    }

    private void startServer() {
        vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        ApiRequests.setupRoutes(router);

        server.requestHandler(router).listen(8765, result -> {
            if (result.succeeded()) {
                System.out.println("Server started on port 8765");
            } else {
                result.cause().printStackTrace();
            }
        });
    }
}