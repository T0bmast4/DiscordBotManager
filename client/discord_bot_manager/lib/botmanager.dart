import 'dart:convert';
import 'dart:ffi';
import 'package:http/http.dart' as http;
class BotManager {
  String serverIp = "http://10.0.0.21:8765/";

  Future<String> startBot(String token) async {
    String responseMessage = "";
    var url = Uri.parse(serverIp + "api/bot/start");
    try {
      var response = await http.post(
        url,
        headers: {"Content-Type": "application/json"},
        body: jsonEncode({"token": token}),
      );
      var jsonResponse = jsonDecode(response.body);
      responseMessage = jsonResponse["message"];
    } catch (e) {
      responseMessage = e.toString();
    }
    return responseMessage;
  }

  Future<String> stopBot(String token) async {
    String responseMessage = "";
    var url = Uri.parse(serverIp + "api/bot/stop");
    try {
      var response = await http.post(
        url,
        headers: {"Content-Type": "application/json"},
        body: jsonEncode({"token": token}),
      );
      var jsonResponse = jsonDecode(response.body);
      responseMessage = jsonResponse["message"];
    } catch (e) {
      responseMessage = e.toString();
    }
    return responseMessage;
  }

  Future<bool> isBotOnline(String token) async {
    var url = Uri.parse(serverIp + "api/bot/isOnline");
    try {
      var response = await http.post(
        url,
        headers: {"Content-Type": "application/json"},
        body: jsonEncode({"token": token}),
      );
      var jsonResponse = jsonDecode(response.body);
      print(jsonResponse);
      return jsonResponse["message"];
    } catch (e) {
      return false;
    }
  }
}
