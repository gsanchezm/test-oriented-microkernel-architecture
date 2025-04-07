package checkout.validations;

import interfaces.validations.IValidation;
import utils.BaseLogger;

public class IsUserOnCheckoutStepOne extends BaseLogger implements IValidation {

    @Override
    public boolean validate(Object... args) {
        return false;//WaitUntil.urlContains("checkout-step-one");
    }
}
