package general.pages;

import framework.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class LeftMenuPage extends BasePage {
    protected By burgerMenu = By.id("react-burger-menu-btn");
    protected By menuItemList = By.cssSelector(".bm-item.menu-item");

    public WebElement getBurguerMenu(){
        return getDriver().findElement(burgerMenu);
    }

    public List<WebElement> getMenuItemList(){
        return getDriver().findElements(menuItemList);
    }
}
