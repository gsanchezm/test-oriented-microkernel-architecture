package framework.actions;

import framework.factory.AppiumDriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;

import static config.Constants.WAIT_TIMEOUT;

public class MobileWaitUntil {
    protected static final Logger logger = LogManager.getLogger(MobileWaitUntil.class);
    private static final Duration RETRY_WAIT = Duration.ofMillis(300);

    public static boolean elementExists(Supplier<WebElement> supplier) {
        logger.info("🔍 Verifying if the element exists...");

        WebDriverWait wait = new WebDriverWait(AppiumDriverFactory.getInstance().getAppiumDriver(), WAIT_TIMEOUT);

        try {
            return wait.until(driver -> {
                try {
                    WebElement element = supplier.get();
                    if (element != null && element.isDisplayed()) {
                        logger.info("✅ Element found and visible");
                        return true;
                    }
                    logger.debug("⏳ Element is not displayed yet");
                    return false;
                } catch (Exception e) {
                    logger.debug("⚠️ Element not available yet: {}", e.getMessage());
                    return false;
                }
            });
        } catch (Exception e) {
            logger.error("❌ Element was not found or visible after {} seconds. This might cause flaky test failures.", WAIT_TIMEOUT);
            return false;
        }
    }

    public static boolean elementNotDisplayed(WebElement element) {
        WebDriverWait wait = new WebDriverWait(AppiumDriverFactory.getInstance().getAppiumDriver(), WAIT_TIMEOUT);
        try {
            logger.info("🔍 Verifying if element is NOT displayed");
            return wait.until(ExpectedConditions.invisibilityOf(element));
        } catch (Exception e) {
            logger.warn("⚠️ Element was expected to be hidden but is still visible or could not be found: {}", e.getMessage());
            return false;
        }
    }

    public static boolean allElementsExist(List<WebElement> elements) {
        WebDriverWait wait = new WebDriverWait(AppiumDriverFactory.getInstance().getAppiumDriver(), WAIT_TIMEOUT);
        return elements.stream().allMatch(el -> {
            try {
                logger.info("🔍 Checking visibility for element: {}", el.getText());
                wait.until(ExpectedConditions.visibilityOf(el));
                return true;
            } catch (Exception e) {
                logger.warn("⚠️ Element not visible or missing: {}", e.getMessage());
                return false;
            }
        });
    }

    public static boolean textIsPresent(WebElement element, String text) {
        WebDriverWait wait = new WebDriverWait(AppiumDriverFactory.getInstance().getAppiumDriver(), WAIT_TIMEOUT);
        try {
            logger.info("🔍 Waiting for element to contain exact text: '{}'", text);
            return wait.until(driver -> text.equals(element.getText()));
        } catch (Exception e) {
            logger.warn("⚠️ Exact text not matched. Trying partial match: {}", e.getMessage());
            try {
                return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
            } catch (Exception ex) {
                logger.error("❌ Text '{}' not found in element after timeout: {}", text, ex.getMessage());
                return false;
            }
        }
    }

    public static void elementClickable(WebElement element) {
        try {
            logger.info("🔍 Waiting for element to be clickable");
            new WebDriverWait(AppiumDriverFactory.getInstance().getAppiumDriver(), WAIT_TIMEOUT)
                    .until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            logger.info("✅ Element clicked successfully");
        } catch (Exception e) {
            logger.error("❌ Failed to click on element: {}", e.getMessage());
            throw e;
        }
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

    protected static void waitForRetry(WebDriver driver) {
        // Dummy wait with timeout and polling to yield control without Thread.sleep
        new FluentWait<>(driver)
                .withTimeout(RETRY_WAIT)
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(Exception.class)
                .until(d -> true); // just wait for the time to pass
    }

    public static void retryAction(Runnable action, int retries) {
        int attempts = 0;
        while (attempts <= retries) {
            try {
                action.run();
                return;
            } catch (Exception e) {
                logger.warn("Retryable action failed: {}. Retrying... ({}/{})", e.getMessage(), attempts + 1, retries);
                attempts++;
            }
        }
        throw new RuntimeException("❌ Action failed after retries");
    }

}
