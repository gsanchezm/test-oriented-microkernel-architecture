package general.pages;

import framework.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CheckoutCompletePage extends BasePage {
    private final By orderImage = By.className("pony_express");
    private final By headerLabel = By.className("complete-header");
    private final By bodyTextLabel = By.className("complete-text");
    private final By backHomeButton = By.id("back-to-products");

    public WebElement getOrderImage() {
        return getDriver().findElement(orderImage);
    }

    public WebElement getHeaderLabel() {
        return getDriver().findElement(headerLabel);
    }

    public WebElement getBodyTextLabel() {
        return getDriver().findElement(bodyTextLabel);
    }

    public WebElement getBackHomeButton() {
        return getDriver().findElement(backHomeButton);
    }
}
