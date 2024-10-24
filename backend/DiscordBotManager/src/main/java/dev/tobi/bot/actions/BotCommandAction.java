package dev.tobi.bot.actions;

import java.util.HashMap;
import java.util.Map;

public enum BotCommandAction {
    SEND_MESSAGE("Send a message to the current channel"),
    SEND_MESSAGE_EPHEMERAL("Send ephemeral message to a user"),
    SEND_PRIVATE_MESSAGE("Send a private message to a user"),
    TIMEOUT_USER("Put a user in timeout for a specified duration"),
    ADD_ROLE_TO_USER("Add a specific role to a user"),
    REMOVE_ROLE_FROM_USER("Remove a specific role from a user"),
    MOVE_USER_TO_VOICE_CHANNEL("Move a user to a different voice channel"),
    START_VOTE("Start a vote or poll in the current channel"),
    BAN_USER("Ban a user from the server"),
    KICK_USER("Kick a user from the server"),
    REACT_WITH_EMOJI("React to a message with a specific emoji"),
    PLAY_SOUND("Play a sound effect in a voice channel");

    public final String description;

    BotCommandAction(String description) {
        this.description = description;
    }
}
