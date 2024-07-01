import 'dart:io';

import 'package:path/path.dart';
import 'package:path_provider/path_provider.dart';
import 'package:sqflite/sqflite.dart';
import 'package:sqflite_common_ffi/sqflite_ffi.dart';
import 'bot.dart';

class BotDatabase {
  Database? database;

  static Future<Database> db() async {
    if(Platform.isWindows || Platform.isLinux) {
      sqfliteFfiInit();
      final databaseFactory = databaseFactoryFfi;
      final appDocumentsDir = await getApplicationDocumentsDirectory();
      final dbDirectory = Directory(join(appDocumentsDir.path, "databases"));

      if (!(await dbDirectory.exists())) {
        await dbDirectory.create(recursive: true);
      }

      final dbPath = join(dbDirectory.path, "bot_database.db");

      final winLinuxDB = await databaseFactory.openDatabase(
        dbPath,
        options: OpenDatabaseOptions(
          version: 1,
          onCreate: (Database database, int version) async {
            await createTables(database);
          },
        ),
      );
      return winLinuxDB;


    } else if (Platform.isAndroid || Platform.isIOS) {
      final documentsDirectory = await getApplicationDocumentsDirectory();
      final path = join(documentsDirectory.path, "bot_database.db");
      final iOSAndroidDB = await openDatabase(
        path,
        version: 1,
        onCreate: (Database database, int version) async {
          await createTables(database);
        },
      );
      return iOSAndroidDB;
    }
    throw Exception("Unsupported platform");
  }

  static Future<void> createTables(Database db) async {
    await db.execute("CREATE TABLE Bots (PK_BotID INTEGER PRIMARY KEY AUTOINCREMENT,"
        "Name TEXT,"
        "Description TEXT,"
        "Token TEXT);");
  }

  static Future<void> createBot(Bot bot) async {
    final db = await BotDatabase.db();
    if (db == null) {
      throw Exception("Database Error");
    }
    await db.insert(
      'Bots',
      bot.toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
  }

  static Future<List<Bot>> bots() async {
    final db = await BotDatabase.db();
    if (db == null) {
      throw Exception("Database Error");
    }
    final List<Map<String, dynamic>> botMaps = await db.query("Bots");
    return List.generate(botMaps.length, (i) {
      return Bot(
        id: botMaps[i]["PK_BotID"] as int,
        name: botMaps[i]["Name"] as String,
        description: botMaps[i]["Description"] as String,
        token: botMaps[i]["Token"] as String,
      );
    });
  }

  static Future<void> updateBot(int id, String name, String description, String token) async {
    final db = await BotDatabase.db();

    if (db == null) {
      throw Exception("Database Error");
    }

    final data = {
      "name": name,
      "description": description,
      "token": token,
    };

    await db.update("Bots", data, where: "id = ?", whereArgs: [id]);
  }

  static Future<void> deleteBot(int id) async {
    final db = await BotDatabase.db();
    try {
      await db.delete("bots", where: "id = ?", whereArgs: [id]);
    } catch (err) {
      print("Error deleting Bot: $err");
    }
  }

  static Future<void> resetBotDatabase() async {
    databaseFactory.deleteDatabase("bot_database.db");
  }
}