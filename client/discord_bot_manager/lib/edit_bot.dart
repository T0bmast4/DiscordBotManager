import 'package:discord_bot_manager/bot.dart';
import 'package:discord_bot_manager/botmanager.dart';
import 'package:discord_bot_manager/db.dart';
import 'package:flutter/material.dart';

class EditPage extends StatefulWidget {
  final int botId;
  final String botName;
  final String botDescription;
  final String botToken;

  const EditPage(
      {Key? key,
      required this.botId,
      required this.botName,
      required this.botDescription,
      required this.botToken})
      : super(key: key);

  @override
  State<EditPage> createState() => _EditPageState();
}

class _EditPageState extends State<EditPage> {
  TextEditingController nameController = TextEditingController();
  TextEditingController descriptionController = TextEditingController();
  TextEditingController tokenController = TextEditingController();

  BotManager botManager = BotManager();
  String responseMessage = "";

  List<Bot> botList = [];
  bool botsLoaded = false;

  @override
  void initState() {
    super.initState();
    loadBots();
  }

  void loadBots() async {
    if (!botsLoaded) {
      try {
        List<Bot> bots = await BotDatabase.bots();
        setState(() {
          botList = bots;
        });
        print("Loaded Bots");
      } catch (e) {
        print("Error loading Bots: $e");
      }
    }
  }

  void startBot(botToken) async {
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

    String dropdownValue = "Bot$botId | $botName";

    return Scaffold(
      appBar: AppBar(
        title: DropdownButton(
          value: dropdownValue,
          icon: const Icon(Icons.keyboard_arrow_down),
          items: botList.map((bot) {
            return DropdownMenuItem(
              value: "Bot${bot.id} | ${bot.name}",
              child: Text(bot.name),
            );
          }).toList(),
          onChanged: (String? newValue) {
            if (newValue != null) {
              Bot? selectedBot = botList.firstWhere(
                  (bot) => "Bot${bot.id} | ${bot.name}" == newValue);

              if (selectedBot != null) {
                Navigator.pushReplacement(
                  context,
                  MaterialPageRoute(
                    builder: (context) => EditPage(
                      botId: selectedBot.id!,
                      botName: selectedBot.name,
                      botDescription: selectedBot.description,
                      botToken: selectedBot.token,
                    ),
                  ),
                );
              }
            }
          },
        ),
        elevation: 10,
      ),
      body: Container(
        padding: EdgeInsets.all(15),
        child: Column(
          children: [
            Row(
              children: [
                Text("Commands: ", style: TextStyle(fontSize: 22)),
                FloatingActionButton(onPressed: () {}, child: Icon(Icons.add)),
              ],
            ),
            Expanded(
              child: ListView(
                children: [
                  ListTile(
                    leading: Icon(Icons.keyboard_command_key),
                    trailing: Icon(Icons.keyboard_command_key),
                    title: Text("Test"),
                    subtitle: Text("/test"),
                    isThreeLine: true,
                  )
                ],
              ),
            ),
            Padding(padding: EdgeInsets.only(top: 50)),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                FilledButton(
                  onPressed: () async {
                    bool isOnline = await botManager.isBotOnline(botToken);

                    if (!isOnline) {
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

                    if (isOnline) {
                      stopBot(botToken);
                    } else {
                      setState(() {
                        responseMessage = "Bot is not online";
                      });
                    }
                  },
                  child: Text("Stop"),
                  style: ButtonStyle(
                    backgroundColor:
                        MaterialStateProperty.all<Color>(Colors.red),
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
