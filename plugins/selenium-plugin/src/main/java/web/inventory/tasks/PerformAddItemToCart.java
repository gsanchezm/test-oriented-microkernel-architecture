package web.inventory.tasks;

import config.TOMException;
import enums.PlatformType;
import framework.actions.Click;
import general.pages.ProductsPage;
import interfaces.platform.IPlatformSpecific;
import interfaces.tasks.ITask;
import org.openqa.selenium.WebElement;
import utils.BaseLogger;

public class PerformAddItemToCart extends BaseLogger implements ITask<PerformAddItemToCart>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.WEB;
    }

    @Override
    public PerformAddItemToCart execute(Object... args) {
        if (args.length == 0 || args[0] == null) {
            throw new TOMException("Expected Item message data, but got none");
        }

        String itemInventory = (String) args[0];

        ProductsPage productsPage = new ProductsPage();

        productsPage.getProductList().stream()
                .filter(item -> {
                    String title = item.findElement(productsPage.getByTitle()).getText();

                    return title.equals(itemInventory);
                })
                .forEach(item -> {
                    String productTitle = item.findElement(productsPage.getByTitle()).getText();
                    logger.info("Title: " + productTitle);

                    WebElement addToCartButton = item.findElement(productsPage.getByAddToCartButton());
                    if (addToCartButton.isDisplayed()
                            && addToCartButton.isEnabled()
                    && !addToCartButton.getText().equals("Remove")) {
                        Click.on(addToCartButton);
                    }
                });
        return this;
    }
}
