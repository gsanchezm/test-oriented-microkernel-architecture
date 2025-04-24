package mobile.checkout.validations;

import enums.PlatformType;
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
        return false;//WaitUntil.urlContains("checkout-step-two");
    }
}
