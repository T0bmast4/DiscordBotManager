import 'package:discord_bot_manager/edit_bot.dart';

import 'db.dart';
import 'bot.dart';
import 'botmanager.dart';
import 'package:flutter/material.dart';

class BotOverviewPage extends StatefulWidget {
  const BotOverviewPage({Key? key}) : super(key: key);

  @override
  _BotOverviewWidget createState() => _BotOverviewWidget();
}

class _BotOverviewWidget extends State<BotOverviewPage> {
  BotManager botManager = BotManager();
  List<Bot> botList = [];

  @override
  void initState() {
    super.initState();
    loadBots();
  }

  void loadBots() async {
    try {
      List<Bot> bots = await BotDatabase.bots();
      setState(() {
        botList = bots;
      });
    } catch (e) {
      print("Error loading Bots: $e");
    }
  }

  //ACTUAL BOT OVERVIEW
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Bot Overview"),
      ),
      body: Container(
        padding: EdgeInsets.all(20),
        child: ListWheelScrollView(
          itemExtent: 100,
          diameterRatio: 10,
          physics: BouncingScrollPhysics(),
          children: botList
              .map((bot) => CustomBotCard(
                    title: bot.name,
                    subtitle: bot.description,
                    onTap: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) => EditPage(
                            botId: bot.id as int,
                            botName: bot.name,
                            botDescription: bot.description,
                            botToken: bot.token,
                          ),
                        ),
                      );
                    },
                    onLongPress: () {
                      print("Token: ${bot.token}");
                    },
                  ))
              .toList(),
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          _showAddForm(context);
        },
        child: Icon(Icons.add),
      ),
    );
  }

  void _showAddForm(BuildContext context) async {
    final _formKey = GlobalKey<FormState>();

    TextEditingController nameController = TextEditingController();
    TextEditingController descriptionController = TextEditingController();
    TextEditingController tokenController = TextEditingController();

    showModalBottomSheet(
      isScrollControlled: true,
      context: context,
      elevation: 10,
      builder: ((context) {
        return Padding(
          padding:
          EdgeInsets.only(bottom: MediaQuery.of(context).viewInsets.bottom),
          child: Form(
              key: _formKey,
              child: Container(
                padding: EdgeInsets.symmetric(horizontal: 24.0),
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  crossAxisAlignment: CrossAxisAlignment.end,
                  children: [
                    Padding(padding: EdgeInsets.only(top: 20)),

                    //Name
                    TextFormField(
                      controller: nameController,
                      decoration: InputDecoration(
                        border: OutlineInputBorder(),
                        labelText: 'Name',
                      ),
                      validator: (value) {
                        if (value == null || value.isEmpty) {
                          return "Bitte gib einen Namen ein.";
                        }
                      },
                    ),

                    Padding(padding: EdgeInsets.only(top: 20)),

                    //Description
                    TextFormField(
                      controller: descriptionController,
                      decoration: InputDecoration(
                        border: OutlineInputBorder(),
                        labelText: 'Beschreibung',
                      ),
                      validator: (value) {
                        if (value == null || value.isEmpty) {
                          return "Bitte gib eine Beschreibung ein.";
                        }
                      },
                    ),

                    Padding(padding: EdgeInsets.only(top: 20)),

                    //Bot-Token
                    TextFormField(
                      controller: tokenController,
                      decoration: InputDecoration(
                        border: OutlineInputBorder(),
                        labelText: 'Discord Bot Token',
                      ),
                      validator: (value) {
                        if (value == null || value.isEmpty) {
                          return "Bitte gib einen gültigen Discord Bot Token ein.";
                        }
                      },
                    ),

                    Padding(padding: EdgeInsets.only(top: 50)),

                    OutlinedButton(
                      onPressed: () async {
                        if (_formKey.currentState!.validate()) {
                          Bot newBot = Bot(
                            name: nameController.text,
                            description: descriptionController.text,
                            token: tokenController.text,
                          );

                          BotDatabase.createBot(newBot);
                          loadBots();
                          Navigator.pop(context);
                        }
                      },
                      child: Text('Speichern'),
                    ),
                    Padding(padding: EdgeInsets.only(bottom: 20)),
                  ],
                ),
              )
          ),
        );
      }),
    );
  }
}

class CustomBotCard extends StatelessWidget {
  final String title;
  final String subtitle;
  final VoidCallback onTap;
  final VoidCallback? onLongPress;

  const CustomBotCard({
    Key? key,
    required this.title,
    required this.subtitle,
    required this.onTap,
    this.onLongPress,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 10,
      child: ListTile(
        title: Text(title),
        subtitle: Text(subtitle),
        onTap: onTap,
        onLongPress: onLongPress,
        leading: const Icon(Icons.person, size: 30),
        trailing: const Icon(Icons.arrow_forward_ios),
        contentPadding: EdgeInsets.fromLTRB(20, 12.0, 20, 12.0),
      ),
    );
  }
}
