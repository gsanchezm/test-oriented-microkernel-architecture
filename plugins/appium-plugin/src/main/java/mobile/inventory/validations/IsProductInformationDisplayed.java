package mobile.inventory.validations;

import config.TOMException;
import enums.PlatformType;
import general.pages.ProductsPage;
import interfaces.platform.IPlatformSpecific;
import interfaces.validations.IValidation;
import utils.BaseLogger;

public class IsProductInformationDisplayed extends BaseLogger implements IValidation<IsProductInformationDisplayed>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.MOBILE;
    }

    @Override
    public boolean validate(Object... args) {
        if (args.length == 0 || args[0] == null) {
            throw new TOMException("Expected product info data, but got none");
        }

        // Parse arguments dynamically
        String title = (String) args[0]; // First parameter is title
        String description = (String) args[1]; // Second parameter is description
        String price = (String) args[2]; // Second parameter is price

        ProductsPage productsPage = new ProductsPage();

        /*return productsPage.getProductList().stream()
                .filter(product -> Obtain.text(product.findElement(productsPage.getByTitle()))
                        .equals(title))
                .anyMatch(product -> {
                    boolean matches = true;

                    if (!description.isEmpty()) {
                        String actualDescription = Obtain.text(product.findElement(productsPage.getByDescription()));
                        matches &= actualDescription.equals(description);
                        logger.info("{} description is equals to: {}",actualDescription, description);
                    }

                    if (!price.isEmpty()) {
                        String actualPrice = Obtain.text(product.findElement(productsPage.getByPrice()));
                        matches &= actualPrice.equals(price);
                        logger.info("{} price is equals to: {}",actualPrice, price);
                    }
                    return matches;
                });*/
        return false;
    }
}
