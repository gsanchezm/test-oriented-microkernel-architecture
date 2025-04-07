package checkout.tasks;

import config.TOMException;
import framework.actions.Enter;
import general.pages.CheckoutStepOnePage;
import interfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformFillYourInformation extends BaseLogger implements ITask {
    @Override
    public ITask execute(Object... args) {

        if (args.length == 0 || args[0] == null) {
            throw new TOMException("Expected user credentials data, but got none");
        }

        // Parse arguments dynamically
        String firstName = (String) args[0];
        String lastName = (String) args[1];
        String postalCode = (String) args[2];

        CheckoutStepOnePage checkoutStepOnePage = new CheckoutStepOnePage();

        Enter.text(checkoutStepOnePage.getFirstNameInput(), firstName);
        Enter.text(checkoutStepOnePage.getLastNameInput(), lastName);
        Enter.text(checkoutStepOnePage.getPostalCodeInput(), postalCode);
        return this;
    }
}
