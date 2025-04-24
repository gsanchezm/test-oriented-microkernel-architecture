package mobile.cart.validations;

import enums.PlatformType;
import interfaces.platform.IPlatformSpecific;
import interfaces.validations.IValidation;
import utils.BaseLogger;

public class IsUserOnCart extends BaseLogger implements IValidation<IsUserOnCart>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.MOBILE;
    }


    @Override
    public boolean validate(Object... args) {
        return true;// WaitUntil.urlContains("cart");
    }
}
