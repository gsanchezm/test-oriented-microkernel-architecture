package general.config;

import framework.factory.WebDriverFactory;
import framework.core.interfaces.IWebInitialize;

public class WebInitializeClass implements IWebInitialize {

    @Override
    public void initialize(String browser) {
        // Initialize WebDriver & Navigate
        WebDriverFactory.getInstance().setWebDriver(browser);
    }
}
