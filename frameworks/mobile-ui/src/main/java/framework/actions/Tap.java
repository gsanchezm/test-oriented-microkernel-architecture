package framework.actions;

import framework.config.RetryHelper;
import framework.factory.AppiumDriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;

public class Tap {

    private static final int MAX_RETRIES = 3;
    private static final Duration RETRY_DELAY = Duration.ofMillis(600);

    public static void on(WebElement element) {
        RetryHelper.retry(MAX_RETRIES, RETRY_DELAY, attempt -> {
            performTap(element);
            return null;
        });
    }

    public static void onElementWithText(List<WebElement> elements, String expectedText) {
        Optional<WebElement> found = elements.stream()
                .filter(e -> expectedText.equalsIgnoreCase(e.getText()))
                .findFirst();

        if (found.isEmpty()) {
            throw new RuntimeException("❌ Element not found with text: " + expectedText);
        }

        on(found.get());
    }

    private static void performTap(WebElement element) {
        var driver = AppiumDriverFactory.getInstance().getAppiumDriver();

        var finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        var tap = new Sequence(finger, 1);

        var rect = element.getRect();
        int centerX = rect.getX() + rect.getWidth() / 2;
        int centerY = rect.getY() + rect.getHeight() / 2;

        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, centerY));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(singletonList(tap));
    }
}
