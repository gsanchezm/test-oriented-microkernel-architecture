package web.checkout.validations;

import framework.actions.WaitUntil;
import interfaces.validations.IValidation;
import utils.BaseLogger;

public class IsUserOnCheckoutStepTwo extends BaseLogger implements IValidation {

    @Override
    public boolean validate(Object... args) {
        return WaitUntil.urlContains("checkout-step-two");
    }
}
