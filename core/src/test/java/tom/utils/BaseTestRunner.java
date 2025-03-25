package tom.utils;

import enums.PlatformType;
import intarfaces.init.ICleanUp;
import intarfaces.init.IInitialize;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.TestNGCucumberRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import tom.plugin_manager.PluginManager;
import tom.services.TestContext;

public abstract class BaseTestRunner extends AbstractTestNGCucumberTests {

    TestNGCucumberRunner testNGCucumberRunner;
    protected static final Logger logger = LogManager.getLogger(BaseTestRunner.class);
    protected ICleanUp cleaner;
    protected static TestContext testContext;

    @BeforeClass
    @Parameters({"platform", "driver"})
    public void initializeExecution(String platform, String driver) {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
        logger.info("TOM Java Test Execution Engine Running...");
        logger.info("Platform Selected: {}", platform);
        logger.info("Driver Selected: {}", driver);

        PluginManager.loadPlugins();

        PlatformType platformType = PlatformType.valueOf(platform.toUpperCase());

        if (PluginManager.isPlatformEnabled(platformType)) {
            IInitialize initializer = PluginManager.getInitializer(platformType);
            initializer.initialize(driver);
            cleaner = PluginManager.getCleaner(platformType);
        } else {
            logger.warn("\u26A0\uFE0F Platform {} is not enabled. Skipping WebDriver initialization.", platformType);
        }

        testContext = new TestContext();

        logger.info("Plugins Loaded: {}", PluginManager.getLoadedPlugins());
        logger.info("Total Tasks Registered: {}", testContext.getRegisteredTasks().size());
        logger.info("Total Validations Registered: {}", testContext.getRegisteredValidations().size());
    }

    @AfterClass
    public void cleanUpExecution() {
        if (cleaner != null) {
            cleaner.cleanUp();
        }
    }

    public static TestContext getTestContext() {
        return testContext;
    }
}
