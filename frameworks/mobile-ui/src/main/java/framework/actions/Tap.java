package framework.actions;

import config.TOMException;
import framework.factory.AppiumDriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static config.Constants.WAIT_TIMEOUT;

public class Tap extends MobileWaitUntil {
    public static void on(WebElement element) {
        Optional.ofNullable(element).ifPresent(el -> {
            logger.info("👆 Tapping on element");
            elementClickable(el);


            // W3C touch action tap
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence tap = new Sequence(finger, 1)
                    .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.fromElement(el), 0, 0))
                    .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                    .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            AppiumDriverFactory.getInstance().getAppiumDriver().perform(List.of(tap));
        });
    }

    /**
     * Taps on the first element in the list that matches the given text using WebDriverWait.
     */
    public static void onElementWithText(List<WebElement> elements, String expectedText) {
        WebDriverWait wait = new WebDriverWait(AppiumDriverFactory.getInstance().getAppiumDriver(), WAIT_TIMEOUT);
        WebElement element = wait.until(visibleElementWithText(elements, expectedText));

        if (element == null) {
            throw new TOMException("❌ Menu item not found with text: " + expectedText);
        }

        Tap.on(element);
    }

    private static ExpectedCondition<WebElement> visibleElementWithText(List<WebElement> elements, String expectedText) {
        return driver -> {
            for (WebElement el : elements) {
                try {
                    String text = el.getText().trim();
                    if (text.equalsIgnoreCase(expectedText)) {
                        return el;
                    }
                } catch (Exception ignored) {
                    // Skip stale or detached elements
                }
            }
            return null;
        };
    }
}
