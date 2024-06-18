import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(
        useMaterial3: true,
        colorScheme: ColorScheme.fromSeed(
          seedColor: Colors.blueAccent,
          brightness: Brightness.dark,
        ),
        appBarTheme: AppBarTheme(
          elevation: 4.0,
          shadowColor: Theme.of(context).colorScheme.shadow,
        ),
      ),
      home: const MyStatefulWidget(),
    );
  }
}

class MyStatefulWidget extends StatefulWidget {
  const MyStatefulWidget({Key? key}) : super(key: key);

  @override
  State<MyStatefulWidget> createState() => _MyStatefulWidgetState();
}

class _MyStatefulWidgetState extends State<MyStatefulWidget> {
  int _selectedIndex = 0;
  List<Widget> _pages = <Widget>[
    HomePage(),
    Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text("Abc"),
          Icon(Icons.home),
        ],
      ),
    ),
    Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text("Settings"),
          Icon(Icons.settings),
        ],
      ),
    ),
  ];

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.blue,
          title: Text("Discord Bot Manager"),
          centerTitle: true,
        ),
        bottomNavigationBar: BottomNavigationBar(
          items: const [
            BottomNavigationBarItem(
              icon: Icon(Icons.home),
              label: "Home",
            ),
            BottomNavigationBarItem(icon: Icon(Icons.abc), label: "Abc"),
            BottomNavigationBarItem(
                icon: Icon(Icons.settings), label: "Settings")
          ],
          currentIndex: _selectedIndex,
          selectedItemColor: Colors.blueAccent,
          onTap: _onItemTapped,
        ),
        body: Center(
          child: _pages.elementAt(_selectedIndex),
        ));
  }
}

class HomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {

    final _formKey = GlobalKey<FormState>();
    TextEditingController tokenController = TextEditingController();

    return Form(
      key: _formKey,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Expanded(
                child: Container(
                  padding: EdgeInsets.symmetric(horizontal: 24.0),
                  child: TextFormField(
                    controller: tokenController,
                    decoration: InputDecoration(
                      border: OutlineInputBorder(),
                      labelText: 'Discord Bot Token',
                    ),
                    validator: (value) {
                      if(value == null || value.isEmpty) {
                        return "Bitte gib einen gültigen Discord Bot Token ein.";
                      }
                    },
                  ),
                ),
              )
            ],
          ),
          Padding(padding: EdgeInsets.only(top: 50)),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              FilledButton(
                onPressed: () {
                  if(_formKey.currentState!.validate()) {
                    ScaffoldMessenger.of(context).showSnackBar(
                        const SnackBar(content: Text('Bot wird gestartet...'))
                    );
                  }
                  startBot(tokenController.text);
                },
                child: Text("Start"),
              ),
              FilledButton(
                onPressed: () {},
                child: Text("Stop"),
                style: ButtonStyle(
                  backgroundColor: MaterialStateProperty.all<Color>(Colors.red),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }
}
void startBot(token) async {
  print(token);
  var url = Uri.parse("http://10.0.0.21:8765/api/bot/start");
  try {
    var response = await http.post(url, headers: {"Content-Type": "application/json"}, body: jsonEncode({"token": token}));

    if(response.statusCode == 200) {
      var jsonResponse = jsonDecode(response.body);
      print(jsonResponse["result"]);
      print(jsonResponse["message"]);
    }else {
      var jsonResponse = jsonDecode(response.body);
      print(jsonResponse["result"]);
      print(jsonResponse["message"]);
    }
  } catch(e) {
    print(e);
  }
}