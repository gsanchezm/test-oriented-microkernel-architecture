package general.config;

import config.TOMException;
import enums.PlatformType;
import framework.core.interfaces.IWebInitialize;
import interfaces.init.IInitializeBase;
import interfaces.platform.IPlatformInitializerHandler;

public class WebInitializerHandler implements IPlatformInitializerHandler {
    @Override
    public boolean supports(PlatformType platform) {
        return platform == PlatformType.WEB;
    }

    @Override
    public void initialize(IInitializeBase initializer, String platformVariant) {
        if (initializer instanceof IWebInitialize webInit) {
            webInit.initialize(platformVariant);
        } else {
            throw new TOMException("Expected IWebInitialize but got: " + initializer.getClass());
        }
    }
}
