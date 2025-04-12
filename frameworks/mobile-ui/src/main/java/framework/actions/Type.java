package framework.actions;

import framework.factory.AppiumDriverFactory;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.Optional;

public class Type extends MobileWaitUntil {

    private static final int MAX_RETRIES = 2;

    public static void text(WebElement element, String text) {
        Optional.ofNullable(element).ifPresent(el -> {
            for (int attempt = 0; attempt <= MAX_RETRIES; attempt++) {
                try {
                    logger.info("⌨️ Typing: '{}' on element", text);
                    elementExists(el);
                    el.clear();
                    if (text != null && !text.isEmpty()) {
                        el.sendKeys(text);
                    }
                    return; // success
                } catch (StaleElementReferenceException e) {
                    logger.warn("⚠️ StaleElementReferenceException while typing, attempt {}. Retrying...", attempt + 1);
                    MobileWaitUntil.waitForRetry(AppiumDriverFactory.getInstance().getAppiumDriver()); // non-blocking wait
                } catch (Exception e) {
                    logger.error("❌ Failed to type text '{}' on element: {}", text, e.getMessage());
                    throw e;
                }
            }
        });
    }
}