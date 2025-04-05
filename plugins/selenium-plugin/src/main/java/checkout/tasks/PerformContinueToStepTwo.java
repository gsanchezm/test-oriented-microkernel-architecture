package checkout.tasks;

import framework.actions.Click;
import general.pages.CheckoutStepOnePage;
import intarfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformContinueToStepTwo extends BaseLogger implements ITask {
    @Override
    public ITask execute(Object... args) {
        CheckoutStepOnePage checkoutStepOnePage = new CheckoutStepOnePage();
        Click.on(checkoutStepOnePage.getContinueButton());
        return this;
    }
}
