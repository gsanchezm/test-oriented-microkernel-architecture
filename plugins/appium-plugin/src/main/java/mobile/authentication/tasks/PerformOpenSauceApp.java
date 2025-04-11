package mobile.authentication.tasks;

import general.config.MobileTestDataContext;
import interfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformOpenSauceApp extends BaseLogger implements ITask {
    @Override
    public PerformOpenSauceApp execute(Object... args) {
        String appName = MobileTestDataContext.getAppName();
        logger.info("🚀 Launching test with app: {}", appName);
        return this;

    }
}
