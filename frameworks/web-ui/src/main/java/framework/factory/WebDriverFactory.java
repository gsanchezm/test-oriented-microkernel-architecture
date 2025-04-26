package framework.factory;

import config.TOMException;
import framework.core.IDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.reflections.Reflections;
import utils.BaseLogger;

import java.util.Optional;
import java.util.Set;

public class WebDriverFactory extends BaseLogger {

    private static final WebDriverFactory INSTANCE = new WebDriverFactory();
    private final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    private WebDriverFactory() {
        // Private constructor to enforce Singleton pattern
    }

    public static WebDriverFactory getInstance() {
        return INSTANCE;
    }

    public WebDriver getWebDriver() {
        return webDriver.get();
    }

    public void setWebDriver(String browser) {
        logger.info("🌐 Creating WebDriver: " + browser);
        try {
            String normalizedBrowser = browser.toLowerCase().replace("_responsive", "").replace(" responsive", "").trim();

            // Get all available driver implementations
            Set<Class<? extends IDriver>> driverInterfaces = new Reflections(IDriver.class).getSubTypesOf(IDriver.class);

            // Find the matching driver class
            Class<? extends IDriver> matchedDriver = driverInterfaces.stream()
                    .filter(driver -> driver.getSimpleName().toLowerCase().contains(normalizedBrowser))
                    .findFirst()
                    .orElseThrow(() -> new TOMException("No WebDriver found for browser: " + browser));

            // Instantiate the driver dynamically
            IDriver driverInstance = matchedDriver.getDeclaredConstructor().newInstance();
            webDriver.set(driverInstance.createDriver());

            // It's close to iPhone X, Galaxy S21, Pixel 5
            if(browser.toLowerCase().contains("responsive")){
                logger.info("📱 Responsive mode detected: setting window size to 375x812");
                webDriver.get().manage().window().setSize(new Dimension(375, 812));
                return;
            }

            // Maximize browser window
            webDriver.get().manage().window().maximize();
            logger.info("🖥️ Window maximized successfully");

        } catch (ReflectiveOperationException e) {
            throw new TOMException("Error initializing WebDriver for browser: " + browser, e);
        }
    }

    public void removeWebDriver() {
        Optional.ofNullable(webDriver.get()).ifPresent(driver -> {
            try {
                driver.quit();
            } catch (Exception e) {
                throw new TOMException("Unable to remove WebDriver | Exception: " + e.getMessage(), e);
            } finally {
                webDriver.remove();
            }
        });
    }

}