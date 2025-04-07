package authentication.validations;

import general.pages.LoginPage;
import interfaces.validations.IValidation;
import org.openqa.selenium.WebElement;
import utils.BaseLogger;

import java.util.List;

public class IsUserOnAuthentication extends BaseLogger implements IValidation {

    @Override
    public boolean validate(Object... args) {
        /*WaitUntil.pageLoaded();

        LoginPage loginPage = new LoginPage();

        List<WebElement> loginElements = List.of(loginPage.getUsernameInput(),
                loginPage.getPasswordInput(),
                loginPage.getLoginButton());
        return WaitUntil.allElementsExist(loginElements);*/
        return false;
    }
}
