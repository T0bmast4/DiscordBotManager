package dev.tobi.bot.commands.testCommands;

import dev.tobi.bot.commands.Command;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TrollCommand extends Command {

    public TrollCommand() {
        super("troll", "This command moves you to a random channel!", null);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        Member member = event.getMember();

        AudioChannel currentChannel = member.getVoiceState().getChannel();
        if (currentChannel == null) {
            event.reply("You need to be in a voice channel to use this command.").queue();
            return;
        }

        List<VoiceChannel> voiceChannels = guild.getVoiceChannels()
                .stream()
                .filter(vc -> !vc.equals(currentChannel))  // Exclude current channel
                .collect(Collectors.toList());

        if (voiceChannels.isEmpty()) {
            event.reply("There are no other voice channels to move you to.").queue();
            return;
        }

        Random random = new Random();
        VoiceChannel randomChannel = voiceChannels.get(random.nextInt(voiceChannels.size()));

        guild.moveVoiceMember(member, randomChannel).queue(
                success -> event.reply("Moved you to " + randomChannel.getName()).queue(),
                failure -> event.reply("Failed to move you: " + failure.getMessage()).queue()
        );
    }
}
