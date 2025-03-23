package config;

import authentication.tasks.PerformAuthentication;
import factories.ObjectFactory;
import framework.factory.WebDriverFactory;
import intarfaces.init.IInitialize;

public class WebInitializeClass implements IInitialize {

    @Override
    public void initialize(String browser) {
        // Initialize WebDriver & Navigate
        WebDriverFactory.getInstance().setWebDriver(browser);

        // Register WebDriver-dependent tasks after WebDriver is initialized
        ObjectFactory.register(PerformAuthentication.class, PerformAuthentication::new);
    }
}
