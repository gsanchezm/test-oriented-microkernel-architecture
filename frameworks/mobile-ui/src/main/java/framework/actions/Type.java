package framework.actions;

import framework.factory.AppiumDriverFactory;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.function.Supplier;

public class Type extends MobileWaitUntil {

    private static final int MAX_RETRIES = 2;

    public static void text(Supplier<WebElement> elementSupplier, String text) {
        for (int attempt = 0; attempt <= MAX_RETRIES; attempt++) {
            try {
                WebElement element = elementSupplier.get();
                logger.info("⌨️ Typing: '{}' on element", text);

                elementExists(elementSupplier); // ✅ Usa Supplier para esperas inteligentes
                element.clear();
                if (text != null && !text.isEmpty()) {
                    element.sendKeys(text);
                }
                return;
            } catch (StaleElementReferenceException e) {
                logger.warn("⚠️ StaleElementReferenceException while typing, attempt {}. Retrying...", attempt + 1);
                MobileWaitUntil.waitForRetry(AppiumDriverFactory.getInstance().getAppiumDriver());
            } catch (Exception e) {
                logger.error("❌ Failed to type text '{}' on element: {}", text, e.getMessage());
                throw e;
            }
        }
    }
}