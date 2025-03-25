package general.pages;

import org.openqa.selenium.By;

import framework.core.BasePage;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {
    protected By usernameInput = By.id("user-name");
    protected By passwordInput = By.id("password");
    protected By loginButton = By.name("login-button");

    public WebElement getUsernameInput() {
        return getDriver().findElement(usernameInput);
    }

    public WebElement getPasswordInput() {
        return getDriver().findElement(passwordInput);
    }

    public WebElement getLoginButton() {
        return getDriver().findElement(loginButton);
    }
}