package mobile.cart.validations;

import enums.PlatformType;
import general.pages.CartPage;
import interfaces.platform.IPlatformSpecific;
import interfaces.validations.IValidation;
import utils.BaseLogger;

public class IsProductRemovedFromCart extends BaseLogger implements IValidation<IsProductRemovedFromCart>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.MOBILE;
    }

    @Override
    public boolean validate(Object... args) {
        CartPage cartPage = new CartPage();
        /*if (cartPage.getCartProductItems().isEmpty()){
            logger.info("Cart is empty");
            return true;
        }*/
        return false;
    }
}
