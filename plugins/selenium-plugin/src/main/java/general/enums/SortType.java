package general.enums;

import java.util.Arrays;

public enum SortType {
    NAME_ASC("Name (A to Z)"),
    NAME_DESC("Name (Z to A)"),
    PRICE_LOW_TO_HIGH("Price (low to high)"),
    PRICE_HIGH_TO_LOW("Price (high to low)");

    private final String displayText;

    SortType(final String displayText) {
        this.displayText = displayText;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return displayText;
    }

    public static SortType fromString(String text) {
        return Arrays.stream(SortType.values())
                .filter(type -> type.displayText.equalsIgnoreCase(text.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown sort type: " + text));
    }
}
