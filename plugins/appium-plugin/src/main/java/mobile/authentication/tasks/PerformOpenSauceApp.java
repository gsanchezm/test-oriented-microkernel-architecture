package mobile.authentication.tasks;

import enums.PlatformType;
import general.config.MobileTestDataContext;
import interfaces.platform.IPlatformSpecific;
import interfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformOpenSauceApp extends BaseLogger implements ITask<PerformOpenSauceApp>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.MOBILE;
    }

    @Override
    public PerformOpenSauceApp execute(Object... args) {
        String appName = MobileTestDataContext.getAppName();
        logger.info("🚀 Launching test with app: {}", appName);
        return this;

    }
}
