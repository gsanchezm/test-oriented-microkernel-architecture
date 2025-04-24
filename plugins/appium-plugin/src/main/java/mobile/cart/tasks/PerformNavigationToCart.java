package mobile.cart.tasks;

import enums.PlatformType;
import general.pages.CartPage;
import interfaces.platform.IPlatformSpecific;
import interfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformNavigationToCart extends BaseLogger implements ITask<PerformNavigationToCart>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.MOBILE;
    }

    @Override
    public PerformNavigationToCart execute(Object... args) {
        CartPage cartPage = new CartPage();
        //Click.on(cartPage.getCarIcon());
        return this;
    }
}
