package config;

import framework.factory.WebDriverFactory;

import services.ICleanUp;

public class WebCleanUpClass implements ICleanUp {

    @Override
    public void cleanUp() {
        WebDriverFactory.getInstance().removeWebDriver();
    }
}
