package mobile.checkout.tasks;

import enums.PlatformType;
import general.pages.CheckoutStepOnePage;
import interfaces.platform.IPlatformSpecific;
import interfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformCancelCheckout extends BaseLogger implements ITask<PerformCancelCheckout>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.MOBILE;
    }

    @Override
    public PerformCancelCheckout execute(Object... args) {
        CheckoutStepOnePage checkoutStepOnePage = new CheckoutStepOnePage();
        //Click.on(checkoutStepOnePage.getCancelButton());
        logger.info("Canceled Checkout process");
        return this;
    }
}
