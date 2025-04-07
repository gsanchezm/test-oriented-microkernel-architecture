package checkout.tasks;

import general.pages.CheckoutStepOnePage;
import interfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformContinueToStepTwo extends BaseLogger implements ITask {
    @Override
    public ITask execute(Object... args) {
        CheckoutStepOnePage checkoutStepOnePage = new CheckoutStepOnePage();
        //Click.on(checkoutStepOnePage.getContinueButton());
        return this;
    }
}
