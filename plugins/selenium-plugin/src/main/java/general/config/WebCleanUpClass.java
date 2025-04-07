package general.config;

import framework.factory.WebDriverFactory;

import interfaces.init.ICleanUp;

public class WebCleanUpClass implements ICleanUp {

    @Override
    public void cleanUp() {
        WebDriverFactory.getInstance().removeWebDriver();
    }
}
