package authentication.tasks;

import config.PropertyManager;
import interfaces.tasks.ITask;
import utils.BaseLogger;

import java.util.Optional;
import java.util.Properties;

public class PerformUrlNavigation extends BaseLogger implements ITask {
    private static final Properties SELENIUM_CONFIG = PropertyManager.loadProperties( "selenium-config.properties");

    @Override
    public PerformUrlNavigation execute(Object... args) {
       /* Optional.ofNullable(WebDriverFactory.getInstance().getWebDriver())
                .ifPresent(driver -> driver.navigate().to(SELENIUM_CONFIG.getProperty("Url")));*/

        logger.info("Navigating to: {}", SELENIUM_CONFIG.getProperty("Url"));
        return this;

    }
}
