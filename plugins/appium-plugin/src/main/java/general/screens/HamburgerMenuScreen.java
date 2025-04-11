package general.screens;

import config.TOMException;
import framework.actions.Tap;
import framework.core.BaseScreen;
import framework.core.MobileLocatorHelper;
import framework.factory.AppiumDriverFactory;
import general.config.MobileTestDataContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import strategy.LocatorStrategyRegistry;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import static config.Constants.WAIT_TIMEOUT;

public class HamburgerMenuScreen extends BaseScreen {
    private final MobileLocatorHelper locatorHelper = new MobileLocatorHelper("hamburger_menu_screen");

    private String getPlatform() {
        return MobileTestDataContext.getPlatform(); // e.g., "android"
    }

    public WebElement getHamburgerMenu() {
        return getDriver().findElement(locatorHelper.getLocator("hamburgerMenu", getPlatform()));
    }

    public List<WebElement> getAllMenuItems() {
        // ✅ Only one locator for all items
        return getDriver().findElements(locatorHelper.getLocator("menuItems", getPlatform()));
    }

    /*
    Multiple items in json element
    public List<WebElement> getAllMenuItems() {
        List<WebElement> allItems = new ArrayList<>();
        for (By by : locatorHelper.getLocatorList("menuItem", getPlatform())) {
            allItems.addAll(getDriver().findElements(by));
        }
        return allItems;
    }*/
}
