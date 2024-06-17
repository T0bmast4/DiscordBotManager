package dev.tobi;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.exceptions.ErrorHandler;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.EnumSet;

public class Main {
    private Vertx vertx;


    public static void main(String[] args) throws Exception {
        new Main().startServer();
    }

    private void startServer() throws Exception{
        vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);



        //API START BOT
        router.route("/api/bot/start").handler(ctx -> {

            String token = ctx.pathParam("token");

            //MTI1MjMyOTI1NjkyNzYyOTQyMw.Gl9g2K.aDX_wfSTtGECrZoql5noEQlu_5yib1B-RvsB10

            JDA jda = JDABuilder.createDefault(token)
                    .addEventListeners(new MessageReceiveListener())
                    .build();

            try {
                jda.awaitReady();

                jda.awaitStatus(JDA.Status.CONNECTED);

                if (jda.getStatus() == JDA.Status.CONNECTED) {
                    JsonObject response = new JsonObject()
                            .put("result", "Success")
                            .put("message", "Bot started successfully");
                    ctx.response()
                            .putHeader("Content-Type", "application/json")
                            .setStatusCode(200)
                            .end(response.encodePrettily());
                }else{
                    JsonObject response = new JsonObject()
                            .put("result", "Failed")
                            .put("message", "Bot failed to start");
                    ctx.response()
                            .putHeader("Content-Type", "application/json")
                            .setStatusCode(200)
                            .end(response.encodePrettily());
                }


            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });



        server.listen(8765, result -> {
            if (result.succeeded()) {
                System.out.println("Server started on port 8765");
            } else {
                result.cause().printStackTrace();
            }
        });
    }
}