package dev.tobi.bot.commands.testCommands;

import dev.tobi.bot.commands.Command;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GlückCommand extends Command {

    public GlückCommand() {
        super("glück", "Generiert eine zufällige Zahl", List.of(new OptionData(OptionType.INTEGER, "zahl", "Errate die Zufallszahl")));
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Random rnd = new Random();
        int i = rnd.nextInt(200);
        Integer guessedNumber = event.getOption("zahl") != null ? event.getOption("zahl").getAsInt() : null;
        if (guessedNumber == null) {
            event.reply("Bitte gib eine Zahl an.").setEphemeral(true).queue();
            return;
        }
        if(i == guessedNumber) {
            Member user = event.getMember();
            event.deferReply().queue(hook -> {
                hook.editOriginal("OMG DIE RICHTIGE ZAHL!!!!").queue();  // Sofortige Antwort

                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                final int[] secondsLeft = {6};

                Runnable countdownTask = new Runnable() {
                    @Override
                    public void run() {
                        if (secondsLeft[0] > 0) {
                            hook.editOriginal("Kicking in **" + secondsLeft[0] + "** seconds...").queue();  // Aktualisiere die Antwort
                            secondsLeft[0]--;
                        } else {
                            scheduler.shutdown();
                            event.getGuild().kickVoiceMember(user).queue(
                                    success -> hook.editOriginal("Kicked **" + user.getUser().getName() + "**!").queue(),
                                    error -> hook.editOriginal("Failed to kick **" + user.getUser().getName() + "**: " + error.getMessage()).queue()
                            );
                        }
                    }
                };

                scheduler.scheduleAtFixedRate(countdownTask, 0, 1, TimeUnit.SECONDS);
            });
        }else{
            event.reply("Oh wie Schade, die Zahl wäre gewesen: **" + i + "**").setEphemeral(true).queue();
        }

    }
}
