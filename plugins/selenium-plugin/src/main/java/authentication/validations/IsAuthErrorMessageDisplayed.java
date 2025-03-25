package authentication.validations;

import config.FrameworkException;
import framework.actions.Obtain;
import general.pages.LoginPage;
import intarfaces.validations.IValidation;
import utils.BaseLogger;

public class IsAuthErrorMessageDisplayed extends BaseLogger implements IValidation {
    @Override
    public boolean validate(Object... args) {
        if (args.length == 0 || args[0] == null) {
            throw new FrameworkException("Expected error message data, but got none");
        }

        String errorMessage = null;

        if (args.length > 0) errorMessage = (String) args[0];

        // Validate arguments
        if (errorMessage == null) {
            throw new FrameworkException("Error message is required!");
        }

        // Re-create page object every time to avoid stale references
        LoginPage loginPage = new LoginPage();

        if (Obtain.text(loginPage.getErrorLabel()).equals(errorMessage)){
            return true;
        };

        return false;

    }
}
