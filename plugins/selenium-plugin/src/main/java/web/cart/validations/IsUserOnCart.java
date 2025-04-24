package web.cart.validations;

import enums.PlatformType;
import framework.actions.WaitUntil;
import interfaces.platform.IPlatformSpecific;
import interfaces.validations.IValidation;
import utils.BaseLogger;

public class IsUserOnCart extends BaseLogger implements IValidation<IsUserOnCart>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.WEB;
    }

    @Override
    public boolean validate(Object... args) {
        return WaitUntil.urlContains("web/cart");
    }
}
