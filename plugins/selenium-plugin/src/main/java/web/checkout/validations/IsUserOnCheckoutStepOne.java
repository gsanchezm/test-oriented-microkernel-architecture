package web.checkout.validations;

import enums.PlatformType;
import framework.actions.WaitUntil;
import interfaces.platform.IPlatformSpecific;
import interfaces.validations.IValidation;
import utils.BaseLogger;

public class IsUserOnCheckoutStepOne extends BaseLogger implements IValidation<IsUserOnCheckoutStepOne>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.WEB;
    }

    @Override
    public boolean validate(Object... args) {
        return WaitUntil.urlContains("checkout-step-one");
    }
}
