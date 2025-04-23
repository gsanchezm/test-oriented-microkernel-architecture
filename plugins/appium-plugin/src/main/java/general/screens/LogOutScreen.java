package general.screens;

import framework.core.BaseScreen;
import framework.core.MobileLocatorHelper;
import general.config.MobileTestDataContext;
import org.openqa.selenium.WebElement;

import java.util.function.Supplier;

public class LogOutScreen extends BaseScreen {
    private final MobileLocatorHelper locatorHelper = new MobileLocatorHelper("logout_screen");

    private String getPlatform() {
        return MobileTestDataContext.getPlatform(); // e.g., "android"
    }

    public Supplier<WebElement> getLogOutLabel() {
        return element(locatorHelper.getLocator("logOutLabel", getPlatform()));
    }

    public Supplier<WebElement> getCancelButton() {
        return element(locatorHelper.getLocator("cancelButton", getPlatform()));
    }

    public Supplier<WebElement> getLogOutButton() {
        return element(locatorHelper.getLocator("logOutButton", getPlatform()));
    }
}
