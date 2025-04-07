package framework.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import config.TOMException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.HashMap;
import java.util.Optional;

public class CapabilitiesUtils {

    private static final String CAPABILITY_NAME_KEY = "name";
    private static final String CAPS_KEY = "caps";
    private static final String APP_KEY = "";//MobileCapabilityType.APP;
    private static final String APPS_FOLDER = "apps"; // Replace with actual constant or path if available

    private Optional<JsonObject> findCapabilityObject(String capabilityName, String jsonLocation) {
        JsonArray capabilitiesArray = null;//getJsonArray(jsonLocation);

        for (JsonElement element : capabilitiesArray) {
            if (!element.isJsonObject()) continue;

            JsonObject capability = element.getAsJsonObject();
            String name = capability.has(CAPABILITY_NAME_KEY)
                    ? capability.get(CAPABILITY_NAME_KEY).getAsString()
                    : "";

            if (name.equalsIgnoreCase(capabilityName) && capability.has(CAPS_KEY)) {
                return Optional.of(capability.getAsJsonObject(CAPS_KEY));
            }
        }

        return Optional.empty();
    }

    private HashMap<String, Object> convertCapsToMap(String capabilityName, String jsonLocation) throws JsonProcessingException {
        Optional<JsonObject> capsJson = findCapabilityObject(capabilityName, jsonLocation);

        if (capsJson.isEmpty()) {
            throw new TOMException("Capability with name '" + capabilityName + "' not found in " + jsonLocation);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(capsJson.get().toString(), HashMap.class);
    }

    public DesiredCapabilities getDesiredCapabilities(String appName, String capabilityName, String jsonLocation) throws JsonProcessingException {
        HashMap<String, Object> caps = convertCapsToMap(capabilityName, jsonLocation);
        caps.put(APP_KEY, new File(APPS_FOLDER, appName).getAbsolutePath());
        return new DesiredCapabilities(caps);
    }
}
