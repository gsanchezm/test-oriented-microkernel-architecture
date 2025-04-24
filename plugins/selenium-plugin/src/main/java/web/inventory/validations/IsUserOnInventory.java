package web.inventory.validations;

import enums.PlatformType;
import framework.actions.WaitUntil;
import interfaces.platform.IPlatformSpecific;
import interfaces.validations.IValidation;
import utils.BaseLogger;

public class IsUserOnInventory extends BaseLogger implements IValidation<IsUserOnInventory>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.WEB;
    }

    @Override
    public boolean validate(Object... args) {
        return WaitUntil.urlContains("inventory.html");
    }
}
