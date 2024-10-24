package dev.tobi.bot;

import java.util.HashMap;
import java.util.Map;

public class BotManager {

    private static final Map<String, DcBot> bots = new HashMap<>();

    public static synchronized DcBot getBotByToken(String token) {
        return bots.get(token);
    }

    public static synchronized void startBot(String token) {
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

    public static synchronized boolean isOnline(String token) {
        return bots.containsKey(token);
    }

    public static Map<String, DcBot> getBots() {
        return bots;
    }
}
