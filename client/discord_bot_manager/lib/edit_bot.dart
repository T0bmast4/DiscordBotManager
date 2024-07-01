import 'package:discord_bot_manager/bot.dart';
import 'package:discord_bot_manager/botmanager.dart';
import 'package:flutter/material.dart';

class EditPage extends StatefulWidget {
  final int botId;
  final String botName;
  final String botDescription;
  final String botToken;

  const EditPage({Key? key, required this.botId, required this.botName, required this.botDescription, required this.botToken}) : super(key: key);

  @override
  State<EditPage> createState() => _EditPageState();
}

class _EditPageState extends State<EditPage> {
  BotManager botManager = BotManager();
  String responseMessage = "";

  void startBot(botToken) async{
    BotManager botManager = BotManager();
    String message = await botManager.startBot(botToken);
    setState(() {
      responseMessage = message;
    });
  }

  void stopBot(botToken) async {
    String message = await botManager.stopBot(botToken);
    setState(() {
      responseMessage = message;
    });
  }

  @override
  Widget build(BuildContext context) {
    int botId = widget.botId;
    String botName = widget.botName;
    String botDescription = widget.botDescription;
    String botToken = widget.botToken;

    return Scaffold(
      appBar: AppBar(
        title: Text("Bot" + botId.toString() + " | " + botName),
        elevation: 10,
      ),
      body: Container(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Padding(padding: EdgeInsets.only(top: 50)),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                FilledButton(
                  onPressed: () async {
                    bool isOnline = await botManager.isBotOnline(botToken);

                    if(!isOnline) {
                      startBot(botToken);
                    } else {
                      setState(() {
                        responseMessage = "Bot is already online";
                      });
                    }
                  },
                  child: Text("Start"),
                ),
                FilledButton(
                  onPressed: () async {
                    bool isOnline = await botManager.isBotOnline(botToken);

                    if(isOnline) {
                      stopBot(botToken);
                    } else {
                      setState(() {
                        responseMessage = "Bot is not online";
                      });
                    }
                  },
                  child: Text("Stop"),
                  style: ButtonStyle(
                    backgroundColor: MaterialStateProperty.all<Color>(Colors.red),
                  ),
                ),
              ],
            ),
            Padding(padding: EdgeInsets.only(top: 50)),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Text(
                  responseMessage,
                  style: TextStyle(color: Colors.white),
                )
              ],
            )
          ],
        ),
      ),
    );
  }
}