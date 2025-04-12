package general.screens;

import framework.core.BaseScreen;
import framework.core.MobileLocatorHelper;
import general.config.MobileTestDataContext;
import org.openqa.selenium.WebElement;

import java.util.List;

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
