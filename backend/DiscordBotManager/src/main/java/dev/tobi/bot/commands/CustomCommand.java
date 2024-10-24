package dev.tobi.bot.commands;

import dev.tobi.bot.actions.BotCommandAction;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public class CustomCommand extends Command {

    private JsonArray actions;

    public CustomCommand(String name, String description, List<OptionData> options, JsonArray actions) {
        super(name, description, options);
        this.actions = actions;
    }

    /* Command
    {
      "token": "YOUR_BOT_TOKEN",
      "commandName": "dynamicCommand",
      "description": "This command executes multiple dynamic actions",
      "actions": [
        {
          "actionName": "SEND_MESSAGE",
          "parameters": {
            "message": "Hello, World!"
          }
        },
        {
          "actionName": "REACT_WITH_EMOJI",
          "parameters": {
            "emoji": "🎉"
          }
        },
        {
          "actionName": "MENTION_USER",
          "parameters": {
            "userId": "123456789"
          }
        }
      ]
    }
     */

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        for(int i = 0; i < actions.size(); i++) {
            JsonObject action = actions.getJsonObject(i);

            BotCommandAction botAction = BotCommandAction.valueOf(action.getString("actionName"));
            JsonObject parameters = action.getJsonObject("parameters");

            switch (botAction) {
                case SEND_MESSAGE:
                    parameters.getString("message");
                    break;
            }
        }
    }
}
