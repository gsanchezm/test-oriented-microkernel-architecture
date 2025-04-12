package framework.config;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.function.Function;

public class RetryHelper {

    public static <T> T retry(int maxAttempts, Duration timeout, Function<Integer, T> action) {
        StaleElementReferenceException lastException = null;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                return action.apply(attempt);
            } catch (StaleElementReferenceException e) {
                lastException = e;
                System.out.println("⚠️ Attempt " + attempt + " failed with stale element. Retrying...");
                new FluentWait<>(true)
                        .withTimeout(timeout)
                        .pollingEvery(Duration.ofMillis(200))
                        .ignoring(Exception.class)
                        .until(x -> false); // just wait; we don't care about the result
            }
        }
        throw new RuntimeException("❌ Element remained stale after " + maxAttempts + " attempts", lastException);
    }
}
