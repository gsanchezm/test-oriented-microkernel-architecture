package mobile.inventory.validations;

import enums.PlatformType;
import framework.actions.MobileWaitUntil;
import general.screens.ProductsScreen;
import interfaces.platform.IPlatformSpecific;
import interfaces.validations.IValidation;
import utils.BaseLogger;

public class IsUserOnInventory extends BaseLogger implements IValidation<IsUserOnInventory>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.MOBILE;
    }


    @Override
    public boolean validate(Object... args) {
        ProductsScreen productsScreen = new ProductsScreen();
        return MobileWaitUntil.elementExists(productsScreen.getLogoAndNameImage());
    }
}
