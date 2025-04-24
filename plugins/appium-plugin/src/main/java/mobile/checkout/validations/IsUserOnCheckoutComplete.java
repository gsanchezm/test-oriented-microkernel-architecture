package mobile.checkout.validations;

import enums.PlatformType;
import interfaces.platform.IPlatformSpecific;
import interfaces.validations.IValidation;
import utils.BaseLogger;

public class IsUserOnCheckoutComplete extends BaseLogger implements IValidation<IsUserOnCheckoutComplete> , IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.MOBILE;
    }

    @Override
    public boolean validate(Object... args) {
        return false;
        /*WaitUntil.pageLoaded();

        CheckoutCompletePage checkoutCompletePage = new CheckoutCompletePage();

        List<WebElement> checkoutCompleteElements = List.of(
                checkoutCompletePage.getOrderImage(),
                checkoutCompletePage.getHeaderLabel(),
                checkoutCompletePage.getBodyTextLabel(),
                checkoutCompletePage.getBackHomeButton()
        );

        return WaitUntil.allElementsExist(checkoutCompleteElements);*/
    }
}
