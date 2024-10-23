package dev.tobi.bot;

import dev.tobi.bot.commands.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.EnumSet;

public class DcBot {

    private final String token;
    private JDA jda;
    private CommandManager commandManager;

    public DcBot(String token) {
        this.token = token;
    }

    public void start() {
        if (jda == null) {
            jda = JDABuilder.createDefault(token)
                    .enableIntents(EnumSet.of(GatewayIntent.GUILD_MESSAGES))
                    .disableCache(EnumSet.of(CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS))
                    .build();

            commandManager = new CommandManager(jda);
            commandManager.registerCommand(new PlayCommand());
            commandManager.registerCommand(new TestCommand());
            commandManager.registerCommand(new JoinCommand());
            commandManager.registerCommand(new TrollCommand());
            commandManager.registerCommand(new BrCommand());
            commandManager.registerCommand(new GlückCommand());
            jda.addEventListener(commandManager);
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

    public CommandManager getCommandManager() {
        return commandManager;
    }
}
