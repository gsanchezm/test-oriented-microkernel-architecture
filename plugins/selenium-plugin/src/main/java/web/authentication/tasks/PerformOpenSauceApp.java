package web.authentication.tasks;

import config.PropertyManager;
import framework.factory.WebDriverFactory;
import interfaces.tasks.ITask;
import utils.BaseLogger;

import java.util.Optional;
import java.util.Properties;

public class PerformOpenSauceApp extends BaseLogger implements ITask {
    private static final Properties SELENIUM_CONFIG = PropertyManager.loadProperties( "selenium-config.properties");

    @Override
    public PerformOpenSauceApp execute(Object... args) {
        Optional.ofNullable(WebDriverFactory.getInstance().getWebDriver())
                .ifPresent(driver -> driver.navigate().to(SELENIUM_CONFIG.getProperty("Url")));

        logger.info("Navigating to: {}", SELENIUM_CONFIG.getProperty("Url"));
        return this;

    }
}
