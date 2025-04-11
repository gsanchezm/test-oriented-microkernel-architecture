package framework.factory;

import config.TOMException;
import framework.config.ADBDeviceChecker;
import framework.core.AppiumServer;
import framework.config.CapabilitiesJsonParser;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import strategy.CapabilityStrategyResolver;
import utils.BaseLogger;

import java.util.Map;

public class AppiumDriverFactory extends BaseLogger {

    private static final AppiumDriverFactory instance = new AppiumDriverFactory();
    private final ThreadLocal<AppiumDriver> driverThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<AppiumServer> serverThreadLocal = new ThreadLocal<>();

    private AppiumDriverFactory() {
        // private constructor to enforce singleton
    }

    public static AppiumDriverFactory getInstance() {
        return instance;
    }

    public AppiumDriver getAppiumDriver() {
        return driverThreadLocal.get();
    }

    public void setAppiumDriver(String appName, String device, AppiumServer server, String capabilitiesContent) {
        // 1. Parse caps and server
        this.serverThreadLocal.set(server); // ✅ store server reference
        Map<String, Object> capsMap = new CapabilitiesJsonParser()
                .extractCapabilityMap(appName, device, capabilitiesContent);

        String platformName = String.valueOf(capsMap.getOrDefault("platformName", "")).toLowerCase();

        if (platformName.isBlank()) {
            throw new TOMException("❌ platformName is missing in the capability JSON.");
        }

        // 2. Check that UDID exists via ADB
        // ✅ ADB device check (only for Android)
        if (platformName.equals("android")) {
            String udid = String.valueOf(capsMap.getOrDefault("udid", ""));
            if (udid.isBlank()) {
                throw new TOMException("❌ 'udid' is required in capabilities for Android.");
            }

            ADBDeviceChecker.assertDeviceConnected(udid); // 💥 Will throw if device not connected
        }

        logger.info("🔎 Capabilities class: " + capsMap.getClass());

        logger.info("🧠 Capabilities being sent to Appium:");
        capsMap.forEach((k, v) -> System.out.printf("  %s: %s%n", k, v));

        // 3. Create driver
        try {
            AppiumDriver driver = CapabilityStrategyResolver
                    .resolve(platformName)
                    .createDriver(server.getUrl(), capsMap);
            logger.info("🚀 Starting Appium session at: " + server.getUrl());
            driverThreadLocal.set(driver);
        } catch (Exception e) {
            throw new TOMException("❌ Failed to create Appium session", e);
        }
    }

    public void removeAppiumDriver() {
        AppiumDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }

        AppiumServer server = serverThreadLocal.get();
        if (server != null) {
            server.stop(); // ✅ stop Appium server here
            serverThreadLocal.remove();
        }
    }
}