package cart.tasks;

import framework.actions.Click;
import general.pages.CartPage;
import intarfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformContinueShopping extends BaseLogger implements ITask {
    @Override
    public ITask execute(Object... args) {
        CartPage cartPage = new CartPage();

        Click.butScrollFirst(cartPage.getContinueShoppingButton());
        return this;
    }
}
