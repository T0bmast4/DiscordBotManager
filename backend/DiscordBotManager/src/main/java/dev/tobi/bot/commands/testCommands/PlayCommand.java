package dev.tobi.bot.commands.testCommands;

import dev.tobi.bot.commands.Command;
import dev.tobi.bot.music.PlayerManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.managers.AudioManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PlayCommand extends Command {

    public PlayCommand() {
        super("play", "This command is to play music from YouTube.", List.of(new OptionData(OptionType.STRING, "link", "URL")));
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if (!event.getMember().getVoiceState().inAudioChannel()) {
            event.getChannel().sendMessage("You need to be in an audio channel for this command!").queue();
            return;
        }

        if (!event.getGuild().getSelfMember().hasPermission(Permission.VOICE_SPEAK)) {
            event.getChannel().sendMessage("Bot doesn't have permission to speak!").queue();
            return;
        }

        String link = event.getOption("link").getAsString();
        if (!isUrl(link)) {
            link = "ytsearch:" + link;
        }

        connectToVoiceChannel(event.getGuild(), event.getMember().getVoiceState().getChannel());
        PlayerManager.getInstance().loadAndPlay(event.getChannel().asTextChannel(), link);

        event.reply("Loading track...").queue();
    }

    private void connectToVoiceChannel(Guild guild, AudioChannel channel) {
        AudioManager audioManager = guild.getAudioManager();
        audioManager.openAudioConnection(channel);
    }

    public boolean isUrl(String link) {
        try {
            new URL(link);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
