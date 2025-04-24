package web.checkout.validations;

import enums.PlatformType;
import framework.actions.WaitUntil;
import interfaces.platform.IPlatformSpecific;
import interfaces.validations.IValidation;
import utils.BaseLogger;

public class IsUserOnCheckoutStepTwo extends BaseLogger implements IValidation<IsUserOnCheckoutStepTwo>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.WEB;
    }

    @Override
    public boolean validate(Object... args) {
        return WaitUntil.urlContains("checkout-step-two");
    }
}
