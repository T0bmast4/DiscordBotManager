class Bot {
  final int? id;
  final String name;
  final String description;
  final String token;

  const Bot({
    this.id,
    required this.name,
    required this.description,
    required this.token,
  });

  Map<String, Object?> toMap() {
    final map = {
      'name': name,
      'description': description,
      'token': token,
    };

    if (id != null) {
      map['id'] = id.toString();
    }

    return map;
  }

  @override
  String toString() {
    return 'Bot{id: $id, name: $name, description: $description, token: $token}';
  }
}