package framework.core;

import enums.PlatformType;
import framework.core.interfaces.IMobileInitialize;
import interfaces.init.IInitializeBase;
import interfaces.platform.IPlatformInitializerHandler;

public class MobileInitializerHandler implements IPlatformInitializerHandler {
    @Override
    public boolean supports(PlatformType platform) {
        return platform == PlatformType.MOBILE;
    }

    @Override
    public void initialize(IInitializeBase initializer, String platformVariant) {
        if (initializer instanceof IMobileInitialize mobileInit) {
            // Ideally, extract appNameKey/deviceKey from TestNG or config
            String appNameKey = "MyApp"; // Example
            String deviceKey = platformVariant;
            AppiumServer server = null; // TODO: inject or retrieve
            mobileInit.initialize(appNameKey, deviceKey, server);
        } else {
            throw new IllegalStateException("Expected IMobileInitialize but got: " + initializer.getClass());
        }
    }
}

