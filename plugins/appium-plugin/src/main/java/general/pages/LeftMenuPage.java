package general.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class LeftMenuPage {
    protected final By burgerMenu = By.id("react-burger-menu-btn");
    protected final By menuItemList = By.cssSelector(".bm-item.menu-item");

    /*public WebElement getBurguerMenu(){
        return getDriver().findElement(burgerMenu);
    }

    public List<WebElement> getMenuItemList(){
        return getDriver().findElements(menuItemList);
    }*/
}
