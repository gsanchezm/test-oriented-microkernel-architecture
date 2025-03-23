package authentication.tasks;

import config.PropertyManager;
import framework.factory.WebDriverFactory;
import services.ITask;

import java.util.Optional;
import java.util.Properties;

public class PerformNavigation implements ITask {
    private static final Properties SELENIUM_CONFIG = PropertyManager.loadProperties( "selenium-config.properties");

    @Override
    public PerformNavigation execute(Object... args) {
        Optional.ofNullable(WebDriverFactory.getInstance().getWebDriver())
                .ifPresent(driver -> driver.navigate().to(SELENIUM_CONFIG.getProperty("Url")));
        return this;

    }
}
