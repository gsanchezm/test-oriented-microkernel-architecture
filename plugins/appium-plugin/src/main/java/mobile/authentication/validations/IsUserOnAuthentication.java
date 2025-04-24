package mobile.authentication.validations;

import enums.PlatformType;
import framework.actions.MobileWaitUntil;
import general.screens.LoginScreen;
import interfaces.platform.IPlatformSpecific;
import interfaces.validations.IValidation;
import org.openqa.selenium.WebElement;
import utils.BaseLogger;

import java.util.List;
import java.util.function.Supplier;

public class IsUserOnAuthentication extends BaseLogger implements IValidation<IsUserOnAuthentication>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.MOBILE;
    }

    @Override
    public boolean validate(Object... args) {

        LoginScreen loginScreen = new LoginScreen();

        List<Supplier<WebElement>> loginElements = List.of(loginScreen.getUsernameInput(),
                loginScreen.getPasswordInput(),
                loginScreen.getLoginButton());
        return MobileWaitUntil.allElementsExist(loginElements);
    }
}
