package framework.actions;

import framework.factory.WebDriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.Optional;

public class Click extends WaitUntil{

    /**
     * Default Click: First tries normal click, falls back to JavaScript click.
     */
    public static void on(WebElement element) {
        Optional.ofNullable(element).ifPresent(el -> {
            try {
                logger.info("Clicking on element: {}", element.getAccessibleName());
                elementClickable(el);
            } catch (Exception e) {
                forceClick(el);
            }
        });
    }

    /**
     * Force Click using JavaScript Executor.
     */
    public static void forceClick(WebElement element) {
        logger.info("Clicking on element: {}", element.getAccessibleName());
        Optional.ofNullable(element).ifPresent(el ->
                ((JavascriptExecutor) WebDriverFactory.getInstance().getWebDriver()).executeScript("arguments[0].click();", el)
        );
    }

    /**
     * Click with retry: Tries up to 3 times if click fails.
     */
    public static void withRetry(WebElement element, int attempts) {
        for (int i = 0; i < attempts; i++) {
            try {
                logger.info("Clicking on element: {}", element.getAccessibleName());
                elementClickable(element);
                return; // Exit loop on success
            } catch (Exception e) {
                if (i == attempts - 1) {
                    forceClick(element); // Final fallback
                }
            }
        }
    }

    /**
     * Click after scrolling element into view.
     */
    public static void butScrollFirst(WebElement element) {
        logger.info("Clicking on element: {}", element.getAccessibleName());
        WaitUntil.elementExists(element);
        Optional.ofNullable(element).ifPresent(el -> {
            ((JavascriptExecutor) WebDriverFactory.getInstance().getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", el);
            on(el); // Perform regular click after scrolling
        });
    }

    /**
     * Click using Actions class (simulates real user click).
     */
    public static void actionClick(WebElement element) {
        logger.info("Clicking on element: {}", element.getAccessibleName());
        Actions actions = new Actions(WebDriverFactory.getInstance().getWebDriver());
        Optional.ofNullable(element).ifPresent(el -> actions.moveToElement(el).click().perform());
    }
}
