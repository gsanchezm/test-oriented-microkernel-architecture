package web.checkout.tasks;

import enums.PlatformType;
import framework.actions.Click;
import general.pages.CheckoutStepTwoPage;
import interfaces.platform.IPlatformSpecific;
import interfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformFinishCheckout extends BaseLogger implements ITask<PerformFinishCheckout>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.WEB;
    }

    @Override
    public PerformFinishCheckout execute(Object... args) {
        CheckoutStepTwoPage checkoutStepTwoPage = new CheckoutStepTwoPage();
        Click.on(checkoutStepTwoPage.getFinishButton());
        return this;
    }
}
