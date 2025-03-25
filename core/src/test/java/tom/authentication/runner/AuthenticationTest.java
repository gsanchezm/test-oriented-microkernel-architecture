package tom.authentication.runner;

import tom.plugin_manager.PluginManager;
import tom.services.TestContext;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.TestNGCucumberRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import intarfaces.init.ICleanUp;
import intarfaces.init.IInitialize;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import enums.PlatformType;

@CucumberOptions(
        plugin = {
                "pretty",
                "com.aventstack.chaintest.plugins.ChainTestCucumberListener:"
        },
        features = "src/test/java/tom/authentication/features",
        glue = "tom.authentication.steps_definitions"
)
public class AuthenticationTest extends AbstractTestNGCucumberTests {
    TestNGCucumberRunner testNGCucumberRunner;
    private static final Logger logger = LogManager.getLogger(AuthenticationTest.class);
    private ICleanUp cleaner;
    private static TestContext testContext;

    @BeforeClass
    @Parameters({"platform", "driver"})
    public void initializeExecution(String platform, String driver) {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
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