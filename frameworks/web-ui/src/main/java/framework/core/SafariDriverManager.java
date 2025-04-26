package framework.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class SafariDriverManager implements IDriver {

    public SafariDriverManager() {
    }

    @Override
    public WebDriver createDriver() {
        return new SafariDriver();
    }
}
