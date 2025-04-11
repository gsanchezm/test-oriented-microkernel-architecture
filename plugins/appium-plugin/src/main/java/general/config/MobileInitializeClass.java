package general.config;

import framework.core.AppiumServer;
import framework.core.interfaces.IMobileInitialize;
import framework.factory.AppiumDriverFactory;

public class MobileInitializeClass implements IMobileInitialize {

    @Override
    public void initialize(String appName, String deviceKey, AppiumServer server, String capabilitiesContent) {
        // Initialize AppiumDriver & Open App
        AppiumDriverFactory.getInstance().setAppiumDriver(appName, deviceKey, server, capabilitiesContent);
    }
}
