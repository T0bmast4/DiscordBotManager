package dev.tobi.bot.actions;

import java.util.HashMap;
import java.util.Map;

public enum BotCommandAction {
    SEND_MESSAGE("Send a message to the current channel"),
    SEND_MESSAGE_EPHEMERAL("Send ephemeral message to a user"),
    SEND_PRIVATE_MESSAGE("Send a private message to a user"),
    MENTION_USER("Mention a user in the current channel"),
    REPLY_WITH_MESSAGE("Reply to the command with a predefined message"),
    SEND_RANDOM_MESSAGE("Send a random message from a list of options"),
    MENTION_EVERYONE("Mention everyone in the current channel"),
    TIMEOUT_USER("Put a user in timeout for a specified duration"),
    EXECUTE_API_REQUEST("Make an API request and output the result"),
    ADD_ROLE_TO_USER("Add a specific role to a user"),
    REMOVE_ROLE_FROM_USER("Remove a specific role from a user"),
    PLAY_MUSIC("Play a music track in a voice channel"),
    STOP_MUSIC("Stop the music playing in a voice channel"),
    MOVE_USER_TO_VOICE_CHANNEL("Move a user to a different voice channel"),
    START_VOTE("Start a vote or poll in the current channel"),
    BAN_USER("Ban a user from the server"),
    KICK_USER("Kick a user from the server"),
    ASK_QUESTION("Ask a question and await a user response"),
    SEND_EMBED_MESSAGE("Send an embedded message with rich content"),
    EXECUTE_WITH_DELAY("Execute the command with a specified delay"),
    REACT_WITH_EMOJI("React to a message with a specific emoji"),
    PLAY_SOUND("Play a sound effect in a voice channel");

    public final String description;

    BotCommandAction(String description) {
        this.description = description;
    }
}
