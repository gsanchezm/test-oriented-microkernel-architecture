package web.checkout.tasks;

import enums.PlatformType;
import framework.actions.Click;
import general.pages.CheckoutStepOnePage;
import interfaces.platform.IPlatformSpecific;
import interfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformContinueToStepTwo extends BaseLogger implements ITask<PerformContinueToStepTwo> , IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.WEB;
    }

    @Override
    public PerformContinueToStepTwo execute(Object... args) {
        CheckoutStepOnePage checkoutStepOnePage = new CheckoutStepOnePage();
        Click.on(checkoutStepOnePage.getContinueButton());
        return this;
    }
}
