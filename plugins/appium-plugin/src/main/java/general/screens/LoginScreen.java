package general.screens;

import framework.core.BaseScreen;
import framework.core.MobileLocatorHelper;
import general.config.MobileTestDataContext;
import org.openqa.selenium.WebElement;

import java.util.function.Supplier;

public class LoginScreen extends BaseScreen {
    private final MobileLocatorHelper locatorHelper = new MobileLocatorHelper("login_screen");

    private String getPlatform() {
        return MobileTestDataContext.getPlatform(); // e.g., "android"
    }

    public Supplier<WebElement> getUsernameInput() {
        return element(locatorHelper.getLocator("usernameInput", getPlatform()));
    }

    public Supplier<WebElement> getPasswordInput() {
        return element(locatorHelper.getLocator("passwordInput", getPlatform()));
    }

    public Supplier<WebElement> getLoginButton() {
        return element(locatorHelper.getLocator("loginButton", getPlatform()));
    }

    public Supplier<WebElement> getErrorLabel() {
        return element(locatorHelper.getLocator("errorLabel", getPlatform()));
    }

    public Supplier<WebElement> getLockedUserErrorLabel() {
        return element(locatorHelper.getLocator("lockedUserErrorLabel", getPlatform()));
    }

    public Supplier<WebElement> getOkButton() {
        return element(locatorHelper.getLocator("okButton", getPlatform()));
    }
}
