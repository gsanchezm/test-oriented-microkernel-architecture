package general.pages;

import org.openqa.selenium.By;

import framework.core.BasePage;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {
    protected final By usernameInput = By.id("user-name");
    protected final By passwordInput = By.id("password");
    protected final By loginButton = By.name("login-button");
    protected final By errorLabel = By.cssSelector("h3[data-test='error']");

    public WebElement getUsernameInput() {
        return getDriver().findElement(usernameInput);
    }

    public WebElement getPasswordInput() {
        return getDriver().findElement(passwordInput);
    }

    public WebElement getLoginButton() {
        return getDriver().findElement(loginButton);
    }

    public WebElement getErrorLabel(){
        return getDriver().findElement(errorLabel);
    }
}