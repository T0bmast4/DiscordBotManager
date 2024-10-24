package dev.tobi.bot.commands.testCommands;

import dev.tobi.bot.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.List;

public class TestCommand extends Command {

    public TestCommand() {
        super("test", "Just a test command!", List.of(new OptionData(OptionType.STRING, "arg2", "TestArgs")));
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        AudioManager audioManager = event.getGuild().getAudioManager();
        audioManager.openAudioConnection(event.getMember().getVoiceState().getChannel());
        event.reply("Test Command works!").queue();
    }
}
