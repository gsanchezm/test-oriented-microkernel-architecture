package framework.actions;

import framework.config.RetryHelper;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class Extract {

    private static final int MAX_RETRIES = 3;
    private static final Duration RETRY_DELAY = Duration.ofMillis(600);

    public static String text(Supplier<WebElement> elementSupplier) {
        return RetryHelper.retry(MAX_RETRIES, RETRY_DELAY, attempt -> elementSupplier.get().getText());
    }

    public static String attribute(Supplier<WebElement> elementSupplier, String attribute) {
        return RetryHelper.retry(MAX_RETRIES, RETRY_DELAY, attempt -> elementSupplier.get().getAttribute(attribute));
    }

    public static Optional<String> fromList(
            Supplier<List<WebElement>> elementsSupplier,
            String expectedText,
            Function<WebElement, String> extractor,
            boolean exactMatch
    ) {
        return RetryHelper.retry(MAX_RETRIES, RETRY_DELAY, attempt -> {
            List<WebElement> elements = elementsSupplier.get();

            return elements.stream()
                    .map(extractor)
                    .filter(value -> exactMatch
                            ? expectedText.equalsIgnoreCase(value)
                            : value.contains(expectedText))
                    .findFirst();
        });
    }

    public static List<String> allTexts(Supplier<List<WebElement>> elementsSupplier) {
        return RetryHelper.retry(MAX_RETRIES, RETRY_DELAY, attempt ->
                elementsSupplier.get().stream()
                        .map(WebElement::getText)
                        .toList()
        );
    }
}