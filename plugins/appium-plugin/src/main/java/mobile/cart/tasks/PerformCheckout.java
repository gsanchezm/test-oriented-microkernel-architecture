package mobile.cart.tasks;

import enums.PlatformType;
import general.pages.CartPage;
import interfaces.platform.IPlatformSpecific;
import interfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformCheckout extends BaseLogger implements ITask<PerformCheckout>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.MOBILE;
    }

    @Override
    public PerformCheckout execute(Object... args) {
        CartPage cartPage = new CartPage();

        //Click.butScrollFirst(cartPage.getCheckoutButton());

        return this;
    }
}
