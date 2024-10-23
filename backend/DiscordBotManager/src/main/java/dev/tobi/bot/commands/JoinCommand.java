package dev.tobi.bot.commands;

import dev.tobi.bot.actions.BotCommandAction;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand extends Command {

    public JoinCommand() {
        super("join", "Bot joins your Channel!", null);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if (!event.getMember().getVoiceState().inAudioChannel()) {
            event.reply("You need to be in an audio channel for this command!").queue();
            return;
        }
        AudioManager audioManager = event.getGuild().getAudioManager();
        audioManager.openAudioConnection(event.getMember().getVoiceState().getChannel());
        event.reply("Joined!").setEphemeral(true).queue();
    }
}
