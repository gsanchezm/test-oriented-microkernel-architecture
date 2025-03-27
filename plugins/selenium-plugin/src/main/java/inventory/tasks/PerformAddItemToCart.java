package inventory.tasks;

import config.FrameworkException;
import framework.actions.Click;
import general.pages.ProductsPage;
import intarfaces.tasks.ITask;
import org.openqa.selenium.WebElement;
import utils.BaseLogger;

public class PerformAddItemToCart extends BaseLogger implements ITask {

    @Override
    public ITask execute(Object... args) {
        if (args.length == 0 || args[0] == null) {
            throw new FrameworkException("Expected Item message data, but got none");
        }

        String itemInventory = (String) args[0];

        ProductsPage productsPage = new ProductsPage();

        productsPage.getInventoryItemList().stream()
                .filter(item -> {
                    String title = item.findElement(productsPage.getByTitle()).getText();
                    return title.equals(itemInventory);
                })
                .forEach(item -> {
                    String productTitle = item.findElement(productsPage.getByTitle()).getText();
                    logger.info("Title: " + productTitle);

                    WebElement addToCartButton = item.findElement(productsPage.getByAddToCartButton());
                    if (addToCartButton.isDisplayed() && addToCartButton.isEnabled()) {
                        Click.on(addToCartButton);
                    }
                });
        return this;
    }
}
