package framework.actions;

import framework.factory.AppiumDriverFactory;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;
import java.util.Collections;
import java.util.Optional;

public class Keyboard extends MobileWaitUntil{

    public static void hide() {
        RemoteWebDriver driver = AppiumDriverFactory.getInstance().getAppiumDriver();

        Optional<WebElement> passwordsKey = driver.findElements(AppiumBy.className("XCUIElementTypeButton"))
                .stream()
                .filter(e -> {
                    String label = e.getAttribute("label");
                    return label != null && label.equalsIgnoreCase("Passwords");
                })
                .findFirst();

        if (passwordsKey.isPresent()) {
            WebElement el = passwordsKey.get();
            Rectangle rect = el.getRect();
            int centerX = rect.getX() + rect.getWidth() / 2;
            int centerY = rect.getY() + rect.getHeight() / 2;
            performTap(driver, centerX, centerY);
        } else {
            logger.warn("⚠️ 'Passwords' button not found, fallback to screen tap.");
            fallbackTap(driver);
        }
    }

    private static void fallbackTap(RemoteWebDriver driver) {
        Dimension screenSize = driver.manage().window().getSize();
        int fallbackX = screenSize.getWidth() / 2;
        int fallbackY = (int) (screenSize.getHeight() * 0.98); // near bottom-right corner
        performTap(driver, fallbackX, fallbackY);
    }

    private static void performTap(RemoteWebDriver driver, int x, int y) {
        var finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        var tap = new Sequence(finger, 1);

        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(tap));
        logger.info("📱 Tap performed at coordinates: {}, {}", x, y);
    }
}