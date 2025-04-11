package framework.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import config.TOMException;

import java.io.InputStream;
import java.io.InputStreamReader;

public class JSONDataUtils {

    public static JsonElement parseJSON(String jsonLocation) {
        try (InputStream is = JSONDataUtils.class.getClassLoader().getResourceAsStream(jsonLocation)) {
            if (is == null) {
                throw new TOMException("Resource not found: " + jsonLocation);
            }
            return JsonParser.parseReader(new InputStreamReader(is));
        } catch (Exception e) {
            throw new TOMException("Error reading resource file: " + jsonLocation, e);
        }
    }

    public static JsonArray getJsonArray(String jsonLocation) {
        JsonElement root = parseJSON(jsonLocation);

        if (!root.isJsonObject()) {
            throw new TOMException("Invalid JSON structure: root should be a JSON object.");
        }

        JsonObject jsonObject = root.getAsJsonObject();
        if (!jsonObject.has("capabilities") || !jsonObject.get("capabilities").isJsonArray()) {
            throw new TOMException("Missing or invalid 'capabilities' array in JSON file: " + jsonLocation);
        }

        return jsonObject.getAsJsonArray("capabilities");
    }
}