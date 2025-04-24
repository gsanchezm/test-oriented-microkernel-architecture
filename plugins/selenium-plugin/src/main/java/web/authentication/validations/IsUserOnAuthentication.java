package web.authentication.validations;

import enums.PlatformType;
import framework.actions.WaitUntil;
import interfaces.platform.IPlatformSpecific;
import interfaces.validations.IValidation;
import org.openqa.selenium.WebElement;
import general.pages.LoginPage;
import utils.BaseLogger;

import java.util.List;

public class IsUserOnAuthentication extends BaseLogger implements IValidation<IsUserOnAuthentication>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.WEB;
    }

    @Override
    public boolean validate(Object... args) {
        WaitUntil.pageLoaded();

        LoginPage loginPage = new LoginPage();

        List<WebElement> loginElements = List.of(loginPage.getUsernameInput(),
                loginPage.getPasswordInput(),
                loginPage.getLoginButton());
        return WaitUntil.allElementsExist(loginElements);
    }
}
