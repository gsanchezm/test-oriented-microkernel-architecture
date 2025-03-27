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

        String errorMessage = (String) args[0];

        // Re-create page object every time to avoid stale references
        LoginPage loginPage = new LoginPage();

        if (Obtain.text(loginPage.getErrorLabel()).equals(errorMessage)){
            return true;
        };

        return false;

    }
}
