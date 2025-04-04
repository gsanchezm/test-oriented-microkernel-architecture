package checkout.validations;

import framework.actions.WaitUntil;
import intarfaces.validations.IValidation;
import utils.BaseLogger;

public class IsUserOnCheckout extends BaseLogger implements IValidation {

    @Override
    public boolean validate(Object... args) {
        return WaitUntil.urlContains("checkout");
    }
}
