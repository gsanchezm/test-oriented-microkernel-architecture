package cart.validations;

import general.pages.CartPage;
import intarfaces.validations.IValidation;
import utils.BaseLogger;

public class IsProductRemovedFromCart extends BaseLogger implements IValidation {
    @Override
    public boolean validate(Object... args) {
        CartPage cartPage = new CartPage();
        if (cartPage.getCartProductItems().isEmpty()){
            logger.info("Cart is empty");
            return true;
        }
        return false;
    }
}
