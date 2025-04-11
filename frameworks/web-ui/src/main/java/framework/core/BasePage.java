package framework.core;

import config.TOMException;
import framework.factory.WebDriverFactory;
import interfaces.init.IBase;
import org.openqa.selenium.WebDriver;

public class BasePage implements IBase {

    // No longer initialized in constructor
    protected WebDriver driver;

    protected WebDriver getDriver() {
        if (this.driver == null) {
            this.driver = WebDriverFactory.getInstance().getWebDriver();
            if (this.driver == null) {
                throw new TOMException("WebDriver instance is null! Ensure WebDriver is initialized.");
            }
        }
        return this.driver;
    }

    @Override
    public <T extends IBase> T getInstance(Class<T> pageClass) {
        try {
            return pageClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new TOMException("Unable to create instance of " + pageClass.getSimpleName(), e);
        }
    }
}
