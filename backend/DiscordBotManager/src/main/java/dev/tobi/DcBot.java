package dev.tobi;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.EnumSet;

public class DcBot {

    private final String token;
    private JDA jda;

    public DcBot(String token) {
        this.token = token;
    }

    public void start() throws Exception {
        if (jda == null) {
            jda = JDABuilder.createDefault(token)
                    .enableIntents(EnumSet.of(GatewayIntent.GUILD_MESSAGES))
                    .disableCache(EnumSet.of(CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS))
                    .build();
        }
    }

    public void stop() {
        if (jda != null) {
            jda.shutdown();
            jda = null;
        }
    }

    public boolean isRunning() {
        return jda != null;
    }

    public String getToken() {
        return token;
    }

}
