package general.pages;

import framework.core.BasePage;
import org.openqa.selenium.By;

public class CheckoutPage extends BasePage {
    protected By firstNameInput = By.name("firstName");
    protected By lastNameInput = By.name("lastName");
    protected By postalCodeInput = By.id("postal-code");
    protected By continueButton = By.id("continue");
}
