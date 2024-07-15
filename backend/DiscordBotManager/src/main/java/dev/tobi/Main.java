package dev.tobi;

import dev.tobi.api.ApiRequests;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Main extends ListenerAdapter {
    private Vertx vertx;

    public static void main(String[] args) {
        new Main().startServer();
    }

    private void startServer() {
        vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        new ApiRequests().setupRoutes(router);

        server.requestHandler(router).listen(8765, result -> {
            if (result.succeeded()) {
                System.out.println("Server started on port 8765");
            } else {
                result.cause().printStackTrace();
            }
        });
    }
}