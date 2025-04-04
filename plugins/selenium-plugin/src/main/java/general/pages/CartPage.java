package general.pages;

import framework.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {
    private final By cartIcon = By.id("shopping_cart_container");
    private final By cartProductItems = By.cssSelector("div[data-test='inventory-item']");
    private final By quantity = By.className("cart_quantity");
    public final By removeButton = By.xpath("//button[contains(text(),'Remove')]");
    private final By checkoutButton = By.id("checkout");

    public WebElement getCarIcon(){
        return getDriver().findElement(cartIcon);
    }

    public List<WebElement> getCartProductItems(){
        return getDriver().findElements(cartProductItems);
    }

    public WebElement getQuantity(){
        return getDriver().findElement(quantity);
    }

    public WebElement getRemoveButton(){
        return getDriver().findElement(removeButton);
    }

    public WebElement getCheckoutButton(){return getDriver().findElement(checkoutButton);}
}