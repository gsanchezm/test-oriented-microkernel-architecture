package general.screens;

import framework.core.BaseScreen;
import framework.core.MobileLocatorHelper;
import general.config.MobileTestDataContext;
import org.openqa.selenium.WebElement;

import java.util.function.Supplier;

public class ProductsScreen extends BaseScreen {
    private final MobileLocatorHelper locatorHelper = new MobileLocatorHelper("product_screen");

    private String getPlatform() {
        return MobileTestDataContext.getPlatform(); // e.g., "android"
    }

    public Supplier<WebElement> getLogoAndNameImage() {
        return () -> getDriver().findElement(locatorHelper.getLocator("logoAndNameImage", getPlatform()));
    }

    /*public WebElement getPasswordInput() {
        return getDriver().findElement(locatorHelper.getLocator("passwordInput", getPlatform()));
    }

    public WebElement getLoginButton() {
        return getDriver().findElement(locatorHelper.getLocator("loginButton", getPlatform()));
    }*/
}
