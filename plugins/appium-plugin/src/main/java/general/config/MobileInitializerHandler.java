package general.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import config.PropertyManager;
import config.TOMException;
import enums.PlatformType;
import framework.config.JSONDataUtils;
import framework.core.AndroidCapabilitiesLoader;
import framework.core.AppiumServer;
import framework.core.IOSCapabilitiesLoader;
import framework.core.interfaces.ICapabilitiesLoader;
import framework.core.interfaces.IMobileInitialize;
import interfaces.init.IInitializeBase;
import interfaces.platform.IPlatformInitializerHandler;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import framework.strategy.LocatorStrategyRegistry;
import utils.BaseLogger;

import java.io.File;
import java.util.List;
import java.util.Properties;

public class MobileInitializerHandler extends BaseLogger implements IPlatformInitializerHandler {
    private static final Properties APPIUM_CONFIG = PropertyManager.loadProperties( "appium-config.properties");
    private static final String ANDROID_APP_NAME = APPIUM_CONFIG.getProperty("AndroidApp");
    private static final String IOS_APP_NAME = APPIUM_CONFIG.getProperty("IOSApp");
    private static final String APPIUM_IP_ADDRESS = APPIUM_CONFIG.getProperty("ipAddress");
    private static final String APPIUM_PATH = APPIUM_CONFIG.getProperty("AppiumPath");
    private static final String NODE_PATH = APPIUM_CONFIG.getProperty("NodePath");
    private static final String APPIUM_LOGS = "reports/logs/" + APPIUM_CONFIG.getProperty("AppiumLogFile");

    @Override
    public boolean supports(PlatformType platform) {
        return platform == PlatformType.MOBILE;
    }

    @Override
    public void initialize(IInitializeBase initializer, String platformVariant) {
        if (initializer instanceof IMobileInitialize mobileInit) {
            registerMobileLocatorStrategies();

            File logFile = new File(APPIUM_LOGS);
            File logDir = logFile.getParentFile();

            if (!logDir.exists()) {
                boolean created = logDir.mkdirs();
                if (!created) {
                    throw new RuntimeException("Failed to create log directory: " + logDir.getAbsolutePath());
                }
            }

            logger.info("📄 Logging Appium output to: {}", logFile.getAbsolutePath());

            String appName = platformVariant.equalsIgnoreCase("android")
                    ? ANDROID_APP_NAME
                    : IOS_APP_NAME;
            MobileTestDataContext.setAppName(appName);
            MobileTestDataContext.setPlatform(platformVariant.toLowerCase());

            // Dynamically resolve capabilities
            ICapabilitiesLoader loader = platformVariant.equalsIgnoreCase("android")
                    ? new AndroidCapabilitiesLoader()
                    : new IOSCapabilitiesLoader();

            List<String> capabilities = loader.loadAllCapabilityFiles(platformVariant.toLowerCase());

            // Loop through each capability file
            for (String capsPath : capabilities) {
                AppiumServer server = new AppiumServer(APPIUM_IP_ADDRESS, APPIUM_PATH, NODE_PATH, APPIUM_LOGS);
                server.waitUntilReady();
                String capabilityName = extractCapabilityName(capsPath);
                System.out.printf("🔍 Initializing with capability '%s' from file: %s%n", capabilityName, capsPath);
                mobileInit.initialize(appName, capabilityName, server, capsPath);
            }

        } else {
            throw new TOMException("Expected IMobileInitialize but got: " + initializer.getClass());
        }
    }

    private String extractCapabilityName(String capsPath) {
        JsonArray capabilitiesArray = JSONDataUtils.getJsonArray(capsPath);
        JsonObject first = capabilitiesArray.get(0).getAsJsonObject();
        return first.get("name").getAsString();
    }

    private void registerMobileLocatorStrategies() {
        logger.info("🧩 Registering mobile locator strategies...");

        LocatorStrategyRegistry.register("accessibilityId", AppiumBy::accessibilityId);
        LocatorStrategyRegistry.register("id", By::id);
        LocatorStrategyRegistry.register("xpath", By::xpath);
        LocatorStrategyRegistry.register("className", By::className);
        LocatorStrategyRegistry.register("name", By::name);
        LocatorStrategyRegistry.register("css", By::cssSelector);

        // Optional: Mobile-specific
        LocatorStrategyRegistry.register("androidUIAutomator", AppiumBy::androidUIAutomator);
        LocatorStrategyRegistry.register("iosPredicate", AppiumBy::iOSNsPredicateString);
    }

}

