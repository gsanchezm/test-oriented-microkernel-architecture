package general.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CheckoutStepTwoPage extends CheckoutStepOnePage {
    protected final By summaryInfoText = By.cssSelector(".summary_info .summary_value_label");
    protected final By summaryAmountsText = By.cssSelector(".summary_info .summary_subtotal_label, .summary_tax_label, .summary_total_label");
    protected final By finishButton = By.id("finish");

    public WebElement getFinishButton() {
        return getDriver().findElement(finishButton);
    }

    public List<String> getSummaryInfoTextLines() {
        return getDriver().findElements(summaryInfoText)
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public List<String> getSummaryAmountsTextLines() {
        return getDriver().findElements(summaryAmountsText)
                .stream()
                .map(WebElement::getText)
                .toList();
    }

}
