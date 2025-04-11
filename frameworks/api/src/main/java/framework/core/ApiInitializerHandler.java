package framework.core;

import config.TOMException;
import enums.PlatformType;
import framework.core.interfaces.IApiInitialize;
import interfaces.init.IInitializeBase;
import interfaces.platform.IPlatformInitializerHandler;

public class ApiInitializerHandler implements IPlatformInitializerHandler {
    @Override
    public boolean supports(PlatformType platform) {
        return platform == PlatformType.API;
    }

    @Override
    public void initialize(IInitializeBase initializer, String platformVariant) {
        if (initializer instanceof IApiInitialize apiInit) {
            //apiInit.initialize(platformVariant);
        } else {
            throw new TOMException("Expected IApiInitialize but got: " + initializer.getClass());
        }
    }
}
