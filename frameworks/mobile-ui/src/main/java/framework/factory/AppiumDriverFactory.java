package framework.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import config.TOMException;
import framework.core.AppiumServer;
import framework.core.CapabilitiesUtils;
import io.appium.java_client.AppiumDriver;

public class AppiumDriverFactory {

    private static final AppiumDriverFactory instance = new AppiumDriverFactory();
    private final ThreadLocal<AppiumDriver> driverThreadLocal = new ThreadLocal<>();

    private AppiumDriverFactory() {
        // private constructor to enforce singleton
    }

    public static AppiumDriverFactory getInstance() {
        return instance;
    }

    public AppiumDriver getAppiumDriver() {
        return driverThreadLocal.get();
    }

    public void setAppiumDriver(String appNameKey, String deviceKey, AppiumServer server) {
        try {
            String appName = "";//PropertyManager.getInstance().getProperty(appNameKey);
            String device = "";//PropertyManager.getInstance().getProperty(deviceKey);
            var capabilities = new CapabilitiesUtils().getDesiredCapabilities(appName, device, "");// CAPS_CONTENT);

            AppiumDriver driver = null;//new AppiumDriver(server.get(), capabilities);
            driverThreadLocal.set(driver);
        } catch (JsonProcessingException e) {
            throw new TOMException("Failed to parse desired capabilities JSON: " + e.getMessage(), e);
        }
    }

    public void removeAppiumDriver() {
        AppiumDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }
}