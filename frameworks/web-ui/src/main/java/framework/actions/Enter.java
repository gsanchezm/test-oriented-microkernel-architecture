package framework.actions;

import org.openqa.selenium.WebElement;

import java.util.Optional;

public class Enter extends WaitUntil{
    public static void text(WebElement element, String text) {
        Optional.ofNullable(element).ifPresent(el -> {
            logger.info("Typing: {} on element: {}",text, element.getAccessibleName());
            elementExists(el);
            el.clear();
            Optional.ofNullable(text).ifPresent(el::sendKeys);
        });
    }
}

