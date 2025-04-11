package framework.core;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import framework.config.JSONDataUtils;
import framework.factory.AppiumDriverFactory;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import strategy.LocatorStrategyRegistry;

import java.util.ArrayList;
import java.util.List;

public class MobileLocatorHelper {

    private final JsonObject locators;

    public MobileLocatorHelper(String screenName) {
        String path = "locators/" + screenName + ".json";
        JsonElement json = JSONDataUtils.parseJSON(path);
        this.locators = json.getAsJsonObject();
    }

    public By getLocator(String key, String platform) {
        JsonObject entry = locators.get(key).getAsJsonObject();
        String rawValue = entry.get(platform.toLowerCase()).getAsString();

        String[] parts = rawValue.split(":", 2);
        if (parts.length != 2) {
            throw new IllegalArgumentException("❌ Invalid locator format for key: " + key);
        }

        String type = parts[0].trim();
        String value = parts[1].trim();

        return LocatorStrategyRegistry.getStrategy(type).apply(value);
    }

    public List<By> getLocatorList(String key, String platform) {
        JsonObject entry = locators.get(key).getAsJsonObject();
        String rawValue = entry.get(platform.toLowerCase()).getAsString();

        // Support comma-separated values like "xpath://div[1], xpath://div[2]"
        String[] multipleLocators = rawValue.split(",");

        List<By> locatorsList = new ArrayList<>();
        for (String locatorStr : multipleLocators) {
            String[] parts = locatorStr.trim().split(":", 2);
            if (parts.length != 2) {
                throw new IllegalArgumentException("❌ Invalid locator format in list for key: " + key);
            }

            String type = parts[0].trim();
            String value = parts[1].trim();
            locatorsList.add(LocatorStrategyRegistry.getStrategy(type).apply(value));
        }

        return locatorsList;
    }
}