package tom.authentication.runner;

import tom.plugin_manager.PluginManager;
import tom.services.TestContext;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.TestNGCucumberRunner;
import tom.exceptions.GlobalExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import services.ICleanUp;
import services.IInitialize;
import io.qameta.allure.Description;

import utils.PlatformType;

@CucumberOptions(
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        features = "src/test/java/tom/authentication/features",
        glue = "tom.authentication.steps_definitions"
)
public class AuthenticationTest extends AbstractTestNGCucumberTests {
    TestNGCucumberRunner testNGCucumberRunner;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationTest.class);
    private ICleanUp cleaner;
    private static TestContext testContext;

    @BeforeClass
    @Parameters({"platform", "driver"})
    @Description("Setup the test environment")
    public void initializeExecution(String platform, String driver) {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
        GlobalExceptionHandler.register();
        logger.info("TOM Java Test Execution Engine Running...");
        logger.info("Platform Selected: " + platform);
        logger.info("Driver Selected: " + driver);

        // Load plugins first (so PluginManager knows what is enabled/disabled)
        PluginManager.loadPlugins();

        // Convert platform parameter to Enum
        PlatformType platformType = PlatformType.valueOf(platform.toUpperCase());

        if (PluginManager.isPlatformEnabled(platformType)) {
            IInitialize initializer = PluginManager.getInitializer(platformType);
            initializer.initialize(driver); // ✅ Safe now
            cleaner = PluginManager.getCleaner(platformType);
        } else {
            logger.warn("⚠️ Platform " + platformType + " is not enabled. Skipping WebDriver initialization.");
        }

        testContext = new TestContext();

        logger.info("Plugins Loaded: " + PluginManager.getLoadedPlugins());
        logger.info("Total Tasks Registered: " + testContext.getAuthenticationTasks().size());
        logger.info("Total Validations Registered: " + testContext.getLoginValidations().size());
    }

    @AfterClass
    @Description("Clean up the test environment")
    public void cleanUpExecution() {
        testNGCucumberRunner.finish();
        if (cleaner != null) {
            cleaner.cleanUp();
        }
    }

    public static TestContext getTestContext() {
        return testContext;
    }
}