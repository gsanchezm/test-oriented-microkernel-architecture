package general.enums;

public enum MenuItems {
    ALL_ITEMS("All Items"),
    ABOUT("About"),
    LOGOUT("Logout"),
    RESET_APP_STATE("Reset App State");

    private final String text;

    MenuItems(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }

}
