package inventory.validations;

import framework.actions.WaitUntil;
import general.pages.ProductsPage;
import intarfaces.validations.IValidation;
import utils.BaseLogger;

public class AreProductsDisplayed extends BaseLogger implements IValidation {
    @Override
    public boolean validate(Object... args) {
        ProductsPage productsPage = new ProductsPage();
        return WaitUntil.allElementsExist(productsPage.getProductList());
    }
}
