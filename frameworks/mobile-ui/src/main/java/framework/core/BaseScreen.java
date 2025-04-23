package framework.core;

import config.TOMException;
import framework.factory.AppiumDriverFactory;
import interfaces.init.IBase;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.function.Supplier;

public class BaseScreen implements IBase {

    protected AppiumDriver driver;

    protected AppiumDriver getDriver() {
        if (this.driver == null) {
            this.driver = AppiumDriverFactory.getInstance().getAppiumDriver();
            if (this.driver == null) {
                throw new TOMException("AppiumDriver instance is null! Ensure AppiumDriver is initialized.");
            }
        }
        return this.driver;
    }

    protected Supplier<WebElement> element(By locator) {
        return () -> getDriver().findElement(locator);
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
