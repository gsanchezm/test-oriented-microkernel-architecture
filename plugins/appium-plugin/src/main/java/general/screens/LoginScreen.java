package general.screens;

import framework.core.BaseScreen;
import framework.core.MobileLocatorHelper;
import general.config.MobileTestDataContext;
import org.openqa.selenium.WebElement;

public class LoginScreen extends BaseScreen {
    private final MobileLocatorHelper locatorHelper = new MobileLocatorHelper("login_screen");

    private String getPlatform() {
        return MobileTestDataContext.getPlatform(); // e.g., "android"
    }

    public WebElement getUsernameInput() {
        return getDriver().findElement(locatorHelper.getLocator("usernameInput", getPlatform()));
    }

    public WebElement getPasswordInput() {
        return getDriver().findElement(locatorHelper.getLocator("passwordInput", getPlatform()));
    }

    public WebElement getLoginButton() {
        return getDriver().findElement(locatorHelper.getLocator("loginButton", getPlatform()));
    }
}
