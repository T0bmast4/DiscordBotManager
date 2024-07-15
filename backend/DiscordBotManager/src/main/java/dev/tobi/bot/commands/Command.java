package dev.tobi.bot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public abstract class Command {
    String name;
    String description;
    List<OptionData> options;

    public abstract void execute(SlashCommandInteractionEvent event);
}
