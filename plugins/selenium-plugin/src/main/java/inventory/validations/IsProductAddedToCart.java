package inventory.validations;

import intarfaces.validations.IValidation;
import utils.BaseLogger;

public class IsProductAddedToCart extends BaseLogger implements IValidation {
    @Override
    public boolean validate(Object... args) {
        return false;
    }
}
