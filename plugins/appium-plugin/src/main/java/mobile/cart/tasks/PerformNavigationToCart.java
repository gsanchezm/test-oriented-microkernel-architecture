package mobile.cart.tasks;

import general.pages.CartPage;
import interfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformNavigationToCart extends BaseLogger implements ITask {

    @Override
    public ITask execute(Object... args) {
        CartPage cartPage = new CartPage();
        //Click.on(cartPage.getCarIcon());
        return this;
    }
}
