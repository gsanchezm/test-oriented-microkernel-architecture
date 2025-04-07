package inventory.validations;

import general.pages.ProductsPage;
import interfaces.validations.IValidation;
import utils.BaseLogger;

public class AreProductsDisplayed extends BaseLogger implements IValidation {
    @Override
    public boolean validate(Object... args) {
        ProductsPage productsPage = new ProductsPage();
        return false;//WaitUntil.allElementsExist(productsPage.getProductList());
    }
}
