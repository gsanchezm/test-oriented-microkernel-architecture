package tom.utils;

import enums.PlatformType;
import intarfaces.init.ICleanUp;
import intarfaces.init.IInitialize;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.TestNGCucumberRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import tom.plugin_manager.PluginManager;
import tom.services.TestContext;

public abstract class BaseTestRunner extends AbstractTestNGCucumberTests {

    protected TestNGCucumberRunner testNGCucumberRunner;
    protected static final Logger logger = LogManager.getLogger(BaseTestRunner.class);
    protected ICleanUp cleaner;

    @BeforeClass
    public void initContextPerClass() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @BeforeTest
    @Parameters({"platform", "driver"})
    public void initializeExecution(String platform, String driver) {
        String runnerName = this.getClass().getSimpleName();
        logger.info("🧪 Runner detected: {}", runnerName);
        logger.info("TOM Java Test Execution Engine Running...");
        logger.info("Platform Selected: {}", platform);
        logger.info("Driver Selected: {}", driver);

        PluginManager.loadPlugins();

        PlatformType platformType = PlatformType.valueOf(platform.toUpperCase());

        if (PluginManager.isPlatformEnabled(platformType)) {
            IInitialize initializer = PluginManager.getInitializer(platformType);
            initializer.initialize(driver); // This sets WebDriver into ThreadLocal
            cleaner = PluginManager.getCleaner(platformType);
        } else {
            logger.warn("\u26A0\uFE0F Platform {} is not enabled. Skipping WebDriver initialization.", platformType);
        }

        TestContext.get(); // Sets up per-thread context

        logger.info("Plugins Loaded: {}", PluginManager.getLoadedPlugins());
        logger.info("Total Tasks Registered: {}", TestContext.get().getRegisteredTasks().size());
        logger.info("Total Validations Registered: {}", TestContext.get().getRegisteredValidations().size());
    }

    @AfterTest
    public void cleanUpExecution() {
        if (cleaner != null) {
            cleaner.cleanUp(); // Quits WebDriver in ThreadLocal
        }
        TestContext.clear();
    }
}