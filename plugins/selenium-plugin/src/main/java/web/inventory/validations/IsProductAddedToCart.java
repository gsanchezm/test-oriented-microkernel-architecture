package web.inventory.validations;

import config.TOMException;
import enums.PlatformType;
import framework.actions.Obtain;
import framework.actions.WaitUntil;
import general.pages.CartPage;
import general.pages.ProductsPage;
import interfaces.platform.IPlatformSpecific;
import interfaces.validations.IValidation;
import utils.BaseLogger;

public class IsProductAddedToCart extends BaseLogger implements IValidation<IsProductAddedToCart>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.WEB;
    }

    @Override
    public boolean validate(Object... args) {

        if (args.length == 0 || args[0] == null) {
            throw new TOMException("Expected Item message data, but got none");
        }

        String itemInventory = (String) args[0];

        CartPage cartPage = new CartPage();
        ProductsPage productsPage = new ProductsPage();

        WaitUntil.pageLoaded();
        WaitUntil.allElementsExist(cartPage.getCartProductItems());

        return cartPage.getCartProductItems().stream()
                .filter(cartItem -> cartItem.findElement(productsPage.getByTitle())
                        .getText()
                        .equals(itemInventory))
                .findFirst()
                .map(cartItem -> {
                    String productTitle = cartItem.findElement(productsPage.getByTitle()).getText();
                    logger.info("Title: {}", productTitle);
                    Obtain.text(cartItem.findElement(productsPage.getByTitle()));
                    return true;
                })
                .orElse(false);
    }
}
