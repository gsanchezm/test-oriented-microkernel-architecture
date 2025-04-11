package mobile.inventory.validations;

import interfaces.validations.IValidation;
import utils.BaseLogger;

public class IsUserOnInventory extends BaseLogger implements IValidation {

    @Override
    public boolean validate(Object... args) {
        return false;//WaitUntil.urlContains("inventory.html");
    }
}
