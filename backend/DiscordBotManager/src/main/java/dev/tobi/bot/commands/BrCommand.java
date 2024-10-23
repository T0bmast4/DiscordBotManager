package dev.tobi.bot.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BrCommand extends Command{

    public BrCommand() {
        super("br", "Randomly kicks a channel member!", null);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if (!event.getMember().getVoiceState().inAudioChannel()) {
            event.reply("You need to be in an audio channel for this command!").queue();
            return;
        }

        AudioChannel audioChannel = event.getMember().getVoiceState().getChannel();
        List<Member> clientsInChannel = new ArrayList<>(audioChannel.getMembers());

        Collections.shuffle(clientsInChannel);
        Member selectedClient = clientsInChannel.get(0);

        event.deferReply().queue(hook -> {
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            final int[] secondsLeft = {6};

            Runnable countdownTask = new Runnable() {
                @Override
                public void run() {
                    if (secondsLeft[0] > 0) {
                        hook.editOriginal("Kicking in **" + secondsLeft[0] + "** seconds...").queue();
                        secondsLeft[0]--;
                    } else {
                        scheduler.shutdown();
                        event.getGuild().kickVoiceMember(selectedClient).queue(
                                success -> hook.editOriginal("Kicked **" + selectedClient.getUser().getName() + "**!").queue(),
                                error -> hook.editOriginal("Failed to kick **" + selectedClient.getUser().getName() + "**: " + error.getMessage()).queue()
                        );
                    }
                }
            };

            scheduler.scheduleAtFixedRate(countdownTask, 0, 1, TimeUnit.SECONDS);
        });

    }
}
