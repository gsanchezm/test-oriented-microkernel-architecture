package framework.actions;

import framework.factory.AppiumDriverFactory;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Optional;

import static config.Constants.WAIT_TIMEOUT;

public class MobileWaitUntil {
    protected static final Logger logger = LogManager.getLogger(MobileWaitUntil.class);

    public static boolean elementExists(WebElement element) {
        logger.info("Verify if element exists");

        return Optional.ofNullable(element)
                .map(el -> {
                    logger.info("Trying to wait on element");
                    return tryWait(el);
                })
                .orElse(false);
    }


    public static boolean elementNotDisplayed(WebElement element) {
        WebDriverWait wait = new WebDriverWait(AppiumDriverFactory.getInstance().getAppiumDriver(), WAIT_TIMEOUT);
        try {
            logger.info("Verifying if element is not displayed");
            return wait.until(ExpectedConditions.invisibilityOf(element));
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean allElementsExist(List<WebElement> elements) {
        WebDriverWait wait = new WebDriverWait(AppiumDriverFactory.getInstance().getAppiumDriver(), WAIT_TIMEOUT);
        return elements.stream().allMatch(el -> {
            try {
                logger.info("Verifying All elements are displayed {}: ", el.getText());
                wait.until(ExpectedConditions.visibilityOf(el));
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    public static boolean textIsPresent(WebElement element, String text) {
        try {
            return new WebDriverWait(AppiumDriverFactory.getInstance().getAppiumDriver(), WAIT_TIMEOUT)
                    .until(driver1 -> element.getText().equals(text));
        } catch (Exception e) {
            try {
                return new WebDriverWait(AppiumDriverFactory.getInstance().getAppiumDriver(), WAIT_TIMEOUT)
                        .until(ExpectedConditions.textToBePresentInElement(element, text));
            } catch (Exception ex) {
                return false;
            }
        }
    }

    public static void elementClickable(WebElement element) {
        new WebDriverWait(AppiumDriverFactory.getInstance().getAppiumDriver(), WAIT_TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    private static boolean tryWait(WebElement element) {
        try {
            new WebDriverWait(AppiumDriverFactory.getInstance().getAppiumDriver(), WAIT_TIMEOUT)
                    .until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}
