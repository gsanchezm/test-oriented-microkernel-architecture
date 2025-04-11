package web.checkout.tasks;

import framework.actions.Click;
import general.pages.CheckoutStepOnePage;
import interfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformCancelCheckout extends BaseLogger implements ITask {
    @Override
    public ITask execute(Object... args) {
        CheckoutStepOnePage checkoutStepOnePage = new CheckoutStepOnePage();
        Click.on(checkoutStepOnePage.getCancelButton());
        logger.info("Canceled Checkout process");
        return this;
    }
}
