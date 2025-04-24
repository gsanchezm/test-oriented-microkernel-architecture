package web.authentication.tasks;

import config.PropertyManager;
import enums.PlatformType;
import framework.factory.WebDriverFactory;
import interfaces.platform.IPlatformSpecific;
import interfaces.tasks.ITask;
import utils.BaseLogger;

import java.util.Optional;
import java.util.Properties;

public class PerformOpenSauceApp extends BaseLogger implements ITask<PerformOpenSauceApp>, IPlatformSpecific {
    private static final Properties SELENIUM_CONFIG = PropertyManager.loadProperties( "selenium-config.properties");

    @Override
    public PlatformType getPlatform() {
        return PlatformType.WEB;
    }

    @Override
    public PerformOpenSauceApp execute(Object... args) {
        Optional.ofNullable(WebDriverFactory.getInstance().getWebDriver())
                .ifPresent(driver -> driver.navigate().to(SELENIUM_CONFIG.getProperty("Url")));

        logger.info("Navigating to: {}", SELENIUM_CONFIG.getProperty("Url"));
        return this;

    }
}
