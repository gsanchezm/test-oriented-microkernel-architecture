package general.config;

import framework.factory.AppiumDriverFactory;
import interfaces.init.ICleanUp;

public class MobileCleanUpClass implements ICleanUp {

    @Override
    public void cleanUp() {
        AppiumDriverFactory.getInstance().removeAppiumDriver();
    }
}
