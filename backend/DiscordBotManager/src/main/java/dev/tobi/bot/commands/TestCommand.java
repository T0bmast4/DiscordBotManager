package dev.tobi.bot.commands;

import dev.tobi.bot.music.PlayerManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Widget;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.managers.AudioManager;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
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
