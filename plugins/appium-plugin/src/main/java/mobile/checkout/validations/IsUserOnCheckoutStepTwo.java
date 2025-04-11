package mobile.checkout.validations;

import interfaces.validations.IValidation;
import utils.BaseLogger;

public class IsUserOnCheckoutStepTwo extends BaseLogger implements IValidation {

    @Override
    public boolean validate(Object... args) {
        return false;//WaitUntil.urlContains("checkout-step-two");
    }
}
