package general.pages;

import framework.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductsPage extends BasePage {
    protected final By ProductList = By.cssSelector("div[data-test='inventory-item']");
    protected final By title = By.cssSelector(".inventory_item_name");
    protected final By description = By.className("inventory_item_desc");
    protected final By price = By.className("inventory_item_price");
    protected final By addToCartButton = By.cssSelector("button.btn_inventory");
    protected final By image = By.className("inventory_item_img");

    public List<WebElement> getProductList(){
        return getDriver().findElements(ProductList);
    }

    public WebElement getTitle() {
        return getDriver().findElement(title);
    }

    public By getByTitle() {
        return title;
    }

    public WebElement getDescription() {
        return getDriver().findElement(description);
    }

    public By getByDescription() {
        return description;
    }

    public WebElement getPrice() {
        return getDriver().findElement(price);
    }

    public By getByPrice() {
        return price;
    }

    public WebElement getAddToCartButton() {
        return getDriver().findElement(addToCartButton);
    }

    public By getByAddToCartButton() {
        return addToCartButton;
    }

    public WebElement getImage() {
        return getDriver().findElement(image);
    }

    public By getByImage() {
        return image;
    }


}
