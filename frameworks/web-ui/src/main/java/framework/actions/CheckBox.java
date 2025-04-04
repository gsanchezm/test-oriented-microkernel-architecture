package framework.actions;

import org.openqa.selenium.WebElement;

import java.util.Optional;

public class CheckBox extends Click{

    /**
     * Verifies if a checkbox is selected.
     *
     * @param checkbox WebElement of the checkbox
     * @return true if checkbox is selected, otherwise false
     */
    public static boolean isSelected(WebElement checkbox) {
        return Optional.ofNullable(checkbox)
                .map(WebElement::isSelected)
                .orElse(false);
    }

    /**
     * Clicks on the checkbox if it's not already selected.
     *
     * @param checkbox WebElement of the checkbox
     */
    public static void select(WebElement checkbox) {
        Optional.ofNullable(checkbox)
                .filter(el -> !el.isSelected()) // Click only if not already selected
                .ifPresent(CheckBox::on);
    }

    /**
     * Unchecks the checkbox if it's selected.
     *
     * @param checkbox WebElement of the checkbox
     */
    public static void uncheck(WebElement checkbox) {
        Optional.ofNullable(checkbox)
                .filter(WebElement::isSelected) // Click only if it's selected
                .ifPresent(CheckBox::on);
    }
}