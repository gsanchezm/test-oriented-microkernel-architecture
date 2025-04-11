package strategy;

import framework.core.interfaces.PlatformCapabilityStrategy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.net.URL;
import java.util.Map;

public class IOSCapabilityStrategy implements PlatformCapabilityStrategy {
    @Override
    public AppiumDriver createDriver(URL serverUrl, Map<String, Object> capsMap) {
        XCUITestOptions options = new XCUITestOptions();
        capsMap.forEach(options::setCapability);
        return new AppiumDriver(serverUrl, options);
    }
}
