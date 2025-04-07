package inventory.validations;

import framework.actions.WaitUntil;
import interfaces.validations.IValidation;
import utils.BaseLogger;

public class IsUserOnInventory extends BaseLogger implements IValidation {

    @Override
    public boolean validate(Object... args) {
        return WaitUntil.urlContains("inventory.html");
    }
}
