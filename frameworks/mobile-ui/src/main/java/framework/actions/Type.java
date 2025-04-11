package framework.actions;

import org.openqa.selenium.WebElement;

import java.util.Optional;

public class Type extends MobileWaitUntil {
    public static void text(WebElement element, String text) {
        Optional.ofNullable(element).ifPresent(el -> {
            logger.info("⌨️ Typing: '{}' on element: {}", text, el.getAccessibleName());
            elementExists(el);
            el.clear();
            Optional.ofNullable(text).ifPresent(el::sendKeys);
        });
    }
}
