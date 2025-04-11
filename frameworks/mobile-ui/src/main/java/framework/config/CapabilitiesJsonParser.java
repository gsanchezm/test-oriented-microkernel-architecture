package framework.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import config.TOMException;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;


import java.util.Map;

public class CapabilitiesJsonParser {

    private static final String CAPS_KEY = "caps";
    private static final String NAME_KEY = "name";
    private static final String CAPS_ARRAY_KEY = "capabilities";
    private static final String PLATFORM_KEY = "platformName";
    private static final String APP_KEY = "app";

    /**
     * Extracts capability map from a JSON file, replaces the app path based on platform + app name.
     */
    public Map<String, Object> extractCapabilityMap(String appName, String capabilityName, String jsonPath) {
        JsonArray capabilitiesArray = getCapabilitiesArray(jsonPath);
        JsonObject matchedCaps = findMatchingCaps(capabilityName, capabilitiesArray)
                .orElseThrow(() -> new TOMException("❌ No matching capability with name '" + capabilityName + "' found in file: " + jsonPath));

        return convertToMapAndInjectApp(matchedCaps, appName);
    }

    private JsonArray getCapabilitiesArray(String jsonPath) {
        JsonElement root = JSONDataUtils.parseJSON(jsonPath);

        if (!root.isJsonObject()) {
            throw new TOMException("❌ Invalid JSON structure: root should be an object.");
        }

        JsonObject rootObj = root.getAsJsonObject();

        if (!rootObj.has(CAPS_ARRAY_KEY) || !rootObj.get(CAPS_ARRAY_KEY).isJsonArray()) {
            throw new TOMException("❌ Missing or invalid 'capabilities' array in: " + jsonPath);
        }

        return rootObj.getAsJsonArray(CAPS_ARRAY_KEY);
    }

    private Optional<JsonObject> findMatchingCaps(String deviceKey, JsonArray capabilitiesArray) {
        for (JsonElement element : capabilitiesArray) {
            if (!element.isJsonObject()) continue;

            JsonObject entry = element.getAsJsonObject();
            String name = entry.has(NAME_KEY) ? entry.get(NAME_KEY).getAsString() : "";

            if (name.equalsIgnoreCase(deviceKey) && entry.has(CAPS_KEY)) {
                return Optional.of(entry.getAsJsonObject(CAPS_KEY));
            }
        }
        return Optional.empty();
    }

    private Map<String, Object> convertToMapAndInjectApp(JsonObject capsJson, String appName) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> capsMap = mapper.readValue(capsJson.toString(), HashMap.class);

            String platform = String.valueOf(capsMap.getOrDefault(PLATFORM_KEY, "")).toLowerCase();
            if (platform.isBlank()) {
                throw new TOMException("❌ 'platformName' missing inside caps JSON block.");
            }

            String appPath = resolveAppPath(appName, platform);
            capsMap.put(APP_KEY, appPath);

            return capsMap;

        } catch (Exception e) {
            throw new TOMException("❌ Failed to convert caps to Map or inject app", e);
        }
    }

    private String resolveAppPath(String appName, String platform) {
        try {
            String resourcePath = String.format("app/%s/%s", platform, appName);
            URL appUrl = getClass().getClassLoader().getResource(resourcePath);

            if (appUrl == null) {
                throw new TOMException("❌ App not found in classpath at: " + resourcePath);
            }

            File appFile = new File(appUrl.toURI());
            if (!appFile.exists()) {
                throw new TOMException("❌ Resolved app file does not exist: " + appFile.getAbsolutePath());
            }

            return appFile.getAbsolutePath();

        } catch (Exception e) {
            throw new TOMException("❌ Failed to resolve app path for: " + appName, e);
        }
    }
}