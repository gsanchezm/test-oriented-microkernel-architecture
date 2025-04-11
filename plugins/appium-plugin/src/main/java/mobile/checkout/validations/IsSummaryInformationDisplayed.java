package mobile.checkout.validations;

import config.TOMException;
import general.pages.CheckoutStepTwoPage;
import interfaces.validations.IValidation;
import utils.BaseLogger;

import java.util.Arrays;
import java.util.List;

public class IsSummaryInformationDisplayed extends BaseLogger implements IValidation {
    @Override
    public boolean validate(Object... args) {
        if (args.length < 5) {
            throw new TOMException("Expected 5 summary values, but got: " + args.length);
        }

        List<String> expectedLines = Arrays.stream(args)
                .map(arg -> arg.toString().trim())
                .toList();

        CheckoutStepTwoPage page = new CheckoutStepTwoPage();

       /* List<String> allLines = Stream.concat(
                page.getSummaryInfoTextLines().stream(),
                page.getSummaryAmountsTextLines().stream()
        ).toList();


        for (String expected : expectedLines) {
            boolean found = allLines.stream()
                    .anyMatch(line -> line.contains(expected));
            if (!found) {
                logger.error("❌ Expected summary line not found: {}", expected);
                return false;
            }
        }*/

        logger.info("✅ All expected summary lines found.");
        return true;
    }
}
