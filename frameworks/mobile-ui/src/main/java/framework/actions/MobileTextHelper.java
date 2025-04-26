package framework.actions;

import org.openqa.selenium.WebElement;

public class MobileTextHelper {
    public static String getVisibleText(WebElement element) {
        String text = element.getText();
        if (!text.isBlank()) return text;

        try {
            return element.getAttribute("name"); // Fallback para iOS
        } catch (Exception e) {
            return "";
        }
    }
}
