package dev.tobi.api;

import io.vertx.core.json.JsonObject;

public class ApiResponse {
    private String result;
    private String message;

    public ApiResponse(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public JsonObject toJson() {
        return new JsonObject()
                .put("result", result)
                .put("message", message);
    }
}
