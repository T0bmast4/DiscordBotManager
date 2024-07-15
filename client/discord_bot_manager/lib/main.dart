import 'bot_overview.dart';
import 'package:flutter/material.dart';

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
          elevation: 5.0,
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
    BotOverviewPage(),
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
          backgroundColor: Colors.blueAccent,
          title: Text("Discord Bot Manager"),
          centerTitle: true,
        ),
        bottomNavigationBar: Container(
          decoration: BoxDecoration(
              border: Border(
                  top: BorderSide(
                    color: Color(0xffAEADB2),
                    width: 0.3,
                  )
              )
          ),

          child: BottomNavigationBar(
            elevation: 25,
            items: const [
              BottomNavigationBarItem(
                icon: Icon(Icons.home),
                label: "Bots",
              ),
              BottomNavigationBarItem(
                  icon: Icon(Icons.settings), label: "Settings")
            ],
            currentIndex: _selectedIndex,
            selectedItemColor: Colors.blueAccent,
            onTap: _onItemTapped,
          ),
        ),
        body: Center(
          child: _pages.elementAt(_selectedIndex),
        ));
  }
}