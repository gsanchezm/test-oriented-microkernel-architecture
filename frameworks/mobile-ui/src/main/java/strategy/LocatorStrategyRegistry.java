package strategy;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class LocatorStrategyRegistry {
    private static final Map<String, Function<String, By>> strategies = new HashMap<>();

    static {
        // 🧠 Cross-platform and Android/iOS specific
        register("accessibilityId", AppiumBy::accessibilityId);
        register("androidUiAutomator", AppiumBy::androidUIAutomator);
        register("androidViewTag", AppiumBy::androidViewTag);
        register("androidDataMatcher", AppiumBy::androidDataMatcher);
        register("iosClassChain", AppiumBy::iOSClassChain);
        register("iosNsPredicate", AppiumBy::iOSNsPredicateString);

        // 🌐 Generic strategies
        register("id", By::id);
        register("name", By::name); // Legacy
        register("xpath", By::xpath);
        register("css", By::cssSelector);
        register("className", By::className);       // iOS only

    }

    public static void register(String key, Function<String, By> resolver) {
        strategies.put(key.toLowerCase(), resolver);
    }

    public static Function<String, By> getStrategy(String type) {
        Function<String, By> strategy = strategies.get(type.toLowerCase());
        if (strategy == null) {
            throw new IllegalArgumentException("❌ Unsupported locator type: " + type);
        }
        return strategy;
    }
}

