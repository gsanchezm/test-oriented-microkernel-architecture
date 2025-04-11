package framework.core.interfaces;

import io.appium.java_client.AppiumDriver;

import java.net.URL;
import java.util.Map;

public interface PlatformCapabilityStrategy {
    AppiumDriver createDriver(URL serverUrl, Map<String, Object> capsMap);
}
