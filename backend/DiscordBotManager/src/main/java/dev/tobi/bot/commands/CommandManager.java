package dev.tobi.bot.commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {

    private List<Command> commands = new ArrayList<>();
    private JDA jda;

    public CommandManager(JDA jda) {
        this.jda = jda;
    }

    @Override
    public void onReady(ReadyEvent event) {
        for(Guild guild : jda.getGuilds()) {
            for(Command command : commands) {
                if(command.options != null) {
                    guild.upsertCommand(command.name, command.description).addOptions(command.options).queue();
                }else{
                    guild.upsertCommand(command.name, command.description).queue();
                }
            }
        }
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        for(Command command : commands) {
            if (command.name.equals(commandName)) {
                command.execute(event);
                return;
            }
        }
        event.reply("Unknown command!").queue();
    }

    public void registerCommand(Command command) {
        commands.add(command);
        for(Guild guild : jda.getGuilds()) {
            guild.upsertCommand(command.name, command.description).addOptions(command.options).queue();
        }
    }

    public List<Command> getCommands() {
        return commands;
    }
}
