package framework.actions;

import org.openqa.selenium.WebElement;

import java.util.Optional;

public class Obtain extends WaitUntil{
    public static String text(WebElement element) {
        return Optional.ofNullable(element)
                .map(el -> {
                    elementExists(el);
                    logger.info("Getting text from element: {}", el.getAccessibleName());
                    return "input".equals(el.getTagName()) ? el.getDomProperty("value") : el.getText();
                })
                .orElse("");
    }
}
