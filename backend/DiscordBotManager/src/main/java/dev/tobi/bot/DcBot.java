package dev.tobi.bot;

import dev.tobi.bot.commands.CommandManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.command.CommandExecutor;

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
