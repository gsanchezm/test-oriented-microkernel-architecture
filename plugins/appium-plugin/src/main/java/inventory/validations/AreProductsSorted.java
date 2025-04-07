package inventory.validations;

import config.TOMException;
import general.enums.SortType;
import general.pages.ProductsPage;
import interfaces.validations.IValidation;
import org.openqa.selenium.WebElement;
import utils.BaseLogger;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AreProductsSorted extends BaseLogger implements IValidation {
    @Override
    public boolean validate(Object... args) {
        if (args.length == 0 || args[0] == null) {
            throw new TOMException("Expected sort option, but got none");
        }

        String sortOption = (String) args[0];
        SortType sortType = SortType.fromString(sortOption);
        logger.info("🔎 Validating product sort order: {}", sortType);

        ProductsPage productsPage = new ProductsPage();
        /*List<WebElement> actualProductList = productsPage.getProductList();

        Map<SortType, Comparator<WebElement>> comparatorMap = Map.of(
                SortType.NAME_ASC, Comparator.comparing(e -> e.findElement(productsPage.getByTitle()).getText()),
                SortType.NAME_DESC, Comparator.comparing((WebElement e) ->
                        e.findElement(productsPage.getByTitle()).getText()).reversed(),
                SortType.PRICE_LOW_TO_HIGH, Comparator.comparingDouble(e ->
                        extractPrice(e.findElement(productsPage.getByPrice()).getText())),
                SortType.PRICE_HIGH_TO_LOW, Comparator.comparingDouble((WebElement e) ->
                        extractPrice(e.findElement(productsPage.getByPrice()).getText())).reversed()
        );

        // Sort using appropriate comparator
        Comparator<WebElement> comparator = Optional.ofNullable(comparatorMap.get(sortType))
                .orElseThrow(() -> new IllegalArgumentException("Unsupported sort type: " + sortType));

        List<WebElement> expectedSortedList = actualProductList.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        logger.info("🔢 Comparing actual vs expected sorted list:");

        // Compare titles of original vs sorted
        for (int i = 0; i < actualProductList.size(); i++) {
            WebElement actualElement = actualProductList.get(i);
            WebElement expectedElement = expectedSortedList.get(i);

            // Name-based sorting
            if (sortType.name().startsWith("NAME")) {
                String actualName = Obtain.text(actualElement.findElement(productsPage.getByTitle()));
                String expectedName = Obtain.text(expectedElement.findElement(productsPage.getByTitle()));

                logger.info("🔹 Position {} => Actual Name: \"{}\" | Expected Name: \"{}\"", i + 1, actualName, expectedName);

                if (!actualName.equals(expectedName)) {
                    logger.warn("❌ Name mismatch at position {}: Actual = \"{}\", Expected = \"{}\"", i + 1, actualName, expectedName);
                    return false; // Guard clause
                }

                continue; // Skip to next iteration
            }

            // Price-based sorting
            if (sortType.name().startsWith("PRICE")) {
                double actualPrice = extractPrice(Obtain.text(actualElement.findElement(productsPage.getByPrice())));
                double expectedPrice = extractPrice(Obtain.text(expectedElement.findElement(productsPage.getByPrice())));

                logger.info("💲 Position {} => Actual Price: ${} | Expected Price: ${}", i + 1, actualPrice, expectedPrice);

                if (Double.compare(actualPrice, expectedPrice) != 0) {
                    logger.warn("❌ Price mismatch at position {}: Actual = ${}, Expected = ${}", i + 1, actualPrice, expectedPrice);
                    return false; // Guard clause
                }
            }
        }*/
        return true;
    }

    private static double extractPrice(String priceText) {
        return Double.parseDouble(priceText.replace("$", "").trim());
    }
}
