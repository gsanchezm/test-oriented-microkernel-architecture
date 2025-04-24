package web.authentication.validations;

import config.TOMException;
import enums.PlatformType;
import framework.actions.Obtain;
import general.pages.LoginPage;
import interfaces.platform.IPlatformSpecific;
import interfaces.validations.IValidation;
import utils.BaseLogger;

public class IsAuthErrorMessageDisplayed extends BaseLogger implements IValidation<IsAuthErrorMessageDisplayed>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.WEB;
    }

    @Override
    public boolean validate(Object... args) {
        if (args.length == 0 || args[0] == null) {
            throw new TOMException("Expected error message data, but got none");
        }

        String errorMessage = (String) args[0];

        // Re-create page object every time to avoid stale references
        LoginPage loginPage = new LoginPage();

        return Obtain.text(loginPage.getErrorLabel()).equals(errorMessage);
    }
}
