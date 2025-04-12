package framework.strategy;

import framework.core.interfaces.PlatformCapabilityStrategy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.URL;
import java.util.Map;

public class AndroidCapabilityStrategy implements PlatformCapabilityStrategy {
    @Override
    public AppiumDriver createDriver(URL serverUrl, Map<String, Object> capsMap) {
        UiAutomator2Options options = new UiAutomator2Options();
        capsMap.forEach(options::setCapability);
        return new AppiumDriver(serverUrl, options);
    }
}

