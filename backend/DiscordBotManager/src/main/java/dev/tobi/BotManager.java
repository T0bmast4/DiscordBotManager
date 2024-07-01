package dev.tobi;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class BotManager {

    private static final Map<String, DcBot> bots = new HashMap<>();

    public static synchronized DcBot getBot(String token) {
        return bots.get(token);
    }

    public static synchronized void startBot(String token) throws Exception {
        if (!bots.containsKey(token)) {
            DcBot bot = new DcBot(token);
            bot.start();
            bots.put(token, bot);
        }
        System.out.println(bots);
    }

    public static synchronized void stopBot(String token) {
        DcBot bot = bots.get(token);
        if (bot != null) {
            bot.stop();
            bots.remove(token);
        }
        System.out.println(bots);
    }

    public static Map<String, DcBot> getBots() {
        return bots;
    }
}
