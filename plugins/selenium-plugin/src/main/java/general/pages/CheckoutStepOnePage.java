package general.pages;

import framework.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CheckoutStepOnePage extends BasePage {
    protected final By firstNameInput = By.name("firstName");
    protected final By lastNameInput = By.name("lastName");
    protected final By postalCodeInput = By.id("postal-code");
    protected final By continueButton = By.id("continue");
    protected final By cancelButton = By.id("cancel");

    public WebElement getFirstNameInput() {
        return getDriver().findElement(firstNameInput);
    }

    public WebElement getLastNameInput() {
        return getDriver().findElement(lastNameInput);
    }

    public WebElement getPostalCodeInput() {
        return getDriver().findElement(postalCodeInput);
    }

    public WebElement getContinueButton() {
        return getDriver().findElement(continueButton);
    }

    public WebElement getCancelButton() {
        return getDriver().findElement(cancelButton);
    }
}
