package web.checkout.validations;

import framework.actions.WaitUntil;
import general.pages.CheckoutCompletePage;
import interfaces.validations.IValidation;
import org.openqa.selenium.WebElement;
import utils.BaseLogger;

import java.util.List;

public class IsUserOnCheckoutComplete extends BaseLogger implements IValidation {
    @Override
    public boolean validate(Object... args) {
        WaitUntil.pageLoaded();

        CheckoutCompletePage checkoutCompletePage = new CheckoutCompletePage();

        List<WebElement> checkoutCompleteElements = List.of(
                checkoutCompletePage.getOrderImage(),
                checkoutCompletePage.getHeaderLabel(),
                checkoutCompletePage.getBodyTextLabel(),
                checkoutCompletePage.getBackHomeButton()
        );

        return WaitUntil.allElementsExist(checkoutCompleteElements);
    }
}
