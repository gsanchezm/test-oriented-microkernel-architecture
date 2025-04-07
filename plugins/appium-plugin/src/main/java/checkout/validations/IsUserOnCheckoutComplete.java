package checkout.validations;

import general.pages.CheckoutCompletePage;
import interfaces.validations.IValidation;
import org.openqa.selenium.WebElement;
import utils.BaseLogger;

import java.util.List;

public class IsUserOnCheckoutComplete extends BaseLogger implements IValidation {
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
