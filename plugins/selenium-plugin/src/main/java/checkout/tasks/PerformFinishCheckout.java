package checkout.tasks;

import framework.actions.Click;
import general.pages.CheckoutStepTwoPage;
import interfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformFinishCheckout extends BaseLogger implements ITask {
    @Override
    public ITask execute(Object... args) {
        CheckoutStepTwoPage checkoutStepTwoPage = new CheckoutStepTwoPage();
        Click.on(checkoutStepTwoPage.getFinishButton());
        return this;
    }
}
