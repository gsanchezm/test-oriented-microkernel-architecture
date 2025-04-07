package general.config;

import authentication.tasks.PerformAuthentication;
import factories.ObjectFactory;
import framework.core.AppiumServer;
import framework.core.interfaces.IMobileInitialize;
import framework.factory.AppiumDriverFactory;

public class MobileInitializeClass implements IMobileInitialize {

    @Override
    public void initialize(String appNameKey, String deviceKey, AppiumServer server) {
        // Initialize AppiumDriver & Open App
        AppiumDriverFactory.getInstance().setAppiumDriver(appNameKey, deviceKey, server);

        // Register AppiumDriver-dependent tasks after AppiumDriver is initialized
        ObjectFactory.register(PerformAuthentication.class, PerformAuthentication::new);
    }
}
