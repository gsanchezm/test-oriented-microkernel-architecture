package framework.actions;

import config.FrameworkException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Optional;

public class Selection extends WaitUntil{

    public static void option(WebElement element, String selection) {
        Optional.ofNullable(element).ifPresent(el -> {
            WaitUntil.elementExists(el);

            Select select = new Select(el);
            logger.info("{} is selected from dropdown list", el.getAccessibleName());
            if (!trySelect(select, selection)) {
                throw new FrameworkException("Invalid selection: " + selection);
            }
        });
    }

    private static boolean trySelect(Select select, String selection) {
        return tryMethod(() -> select.selectByVisibleText(selection))
                || tryMethod(() -> select.selectByValue(selection))
                || tryMethod(() -> select.selectByIndex(Integer.parseInt(selection)));
    }

    private static boolean tryMethod(Runnable action) {
        try {
            action.run();
            return true;
        } catch (WebDriverException | NumberFormatException ignored) {
            return false;
        }
    }
}
