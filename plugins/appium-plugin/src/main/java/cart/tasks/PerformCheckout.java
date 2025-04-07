package cart.tasks;

import general.pages.CartPage;
import interfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformCheckout extends BaseLogger implements ITask {
    @Override
    public ITask execute(Object... args) {
        CartPage cartPage = new CartPage();

        //Click.butScrollFirst(cartPage.getCheckoutButton());

        return this;
    }
}
