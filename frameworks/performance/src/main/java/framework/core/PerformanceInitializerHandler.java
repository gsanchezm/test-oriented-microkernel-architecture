package framework.core;

import config.TOMException;
import enums.PlatformType;
import framework.core.interfaces.IPerformanceInitialize;
import interfaces.init.IInitializeBase;
import interfaces.platform.IPlatformInitializerHandler;

public class PerformanceInitializerHandler implements IPlatformInitializerHandler {
    @Override
    public boolean supports(PlatformType platform) {
        return platform == PlatformType.PERFORMANCE;
    }

    @Override
    public void initialize(IInitializeBase initializer, String platformVariant) {
        if (initializer instanceof IPerformanceInitialize performanceInit) {
            //performanceInit.initialize(platformVariant);
        } else {
            throw new TOMException("Expected IPerformanceInitialize but got: " + initializer.getClass());
        }
    }
}
