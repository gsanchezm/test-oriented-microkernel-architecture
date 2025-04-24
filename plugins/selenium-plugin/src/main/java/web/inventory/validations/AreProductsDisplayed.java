package web.inventory.validations;

import enums.PlatformType;
import framework.actions.WaitUntil;
import general.pages.ProductsPage;
import interfaces.platform.IPlatformSpecific;
import interfaces.validations.IValidation;
import utils.BaseLogger;

public class AreProductsDisplayed extends BaseLogger implements IValidation<AreProductsDisplayed>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.WEB;
    }

    @Override
    public boolean validate(Object... args) {
        ProductsPage productsPage = new ProductsPage();
        return WaitUntil.allElementsExist(productsPage.getProductList());
    }
}
