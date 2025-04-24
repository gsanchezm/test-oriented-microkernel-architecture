package web.cart.tasks;

import enums.PlatformType;
import framework.actions.Click;
import general.pages.CartPage;
import interfaces.platform.IPlatformSpecific;
import interfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformCheckout extends BaseLogger implements ITask<PerformCheckout>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.WEB;
    }

    @Override
    public PerformCheckout execute(Object... args) {
        CartPage cartPage = new CartPage();

        Click.butScrollFirst(cartPage.getCheckoutButton());

        return this;
    }
}
