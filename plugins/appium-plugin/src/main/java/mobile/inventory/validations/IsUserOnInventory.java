package mobile.inventory.validations;

import framework.actions.MobileWaitUntil;
import general.screens.ProductsScreen;
import interfaces.validations.IValidation;
import utils.BaseLogger;

public class IsUserOnInventory extends BaseLogger implements IValidation {

    @Override
    public boolean validate(Object... args) {
        ProductsScreen productsScreen = new ProductsScreen();
        return MobileWaitUntil.elementExists(productsScreen.getLogoAndNameImage());
    }
}
