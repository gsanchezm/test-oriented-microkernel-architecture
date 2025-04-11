package mobile.cart.validations;

import interfaces.validations.IValidation;
import utils.BaseLogger;

public class IsUserOnCart extends BaseLogger implements IValidation {

    @Override
    public boolean validate(Object... args) {
        return true;// WaitUntil.urlContains("cart");
    }
}
