package web.cart.tasks;

import enums.PlatformType;
import framework.actions.Click;
import general.pages.CartPage;
import interfaces.platform.IPlatformSpecific;
import interfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformContinueShopping extends BaseLogger implements ITask<PerformContinueShopping>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.WEB;
    }

    @Override
    public PerformContinueShopping execute(Object... args) {
        CartPage cartPage = new CartPage();

        Click.butScrollFirst(cartPage.getContinueShoppingButton());
        return this;
    }
}
