package tom.utils;

import config.TOMException;
import enums.PlatformType;
import general.config.MobileInitializerHandler;
import general.config.WebInitializerHandler;
import interfaces.init.ICleanUp;
import interfaces.init.IInitializeBase;
import interfaces.platform.IPlatformInitializerHandler;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.TestNGCucumberRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import tom.plugin_manager.PluginManager;
import tom.services.TestContext;
import tom.services.TestDataContext;

import java.util.List;

public abstract class BaseTestRunner extends AbstractTestNGCucumberTests {

    protected TestNGCucumberRunner testNGCucumberRunner;
    protected static final Logger logger = LogManager.getLogger(BaseTestRunner.class);
    protected ICleanUp cleaner;
    private final List<IPlatformInitializerHandler> initializerHandlers = List.of(
            new WebInitializerHandler(),
            new MobileInitializerHandler()
            //new ApiInitializerHandler()
            //new PerformanceInitializerHandler()
            // add more handlers as needed
    );


    @BeforeClass
    public void initContextPerClass() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @BeforeTest
    @Parameters({"platform", "platformVariant"})
    public void initializeExecution(String platform, String platformVariant) {
        String runnerName = this.getClass().getSimpleName();
        logger.info("🧪 Runner detected: {}", runnerName);
        logger.info("TOM Java Test Execution Engine Running...");
        logger.info("Platform Selected: {}", platform);
        logger.info("Driver Selected: {}", platformVariant);

        PlatformType platformType = PlatformType.valueOf(platform.toUpperCase());
        TestDataContext.setPlatform(platform.toLowerCase());

        if (PluginManager.isPlatformEnabled(platformType)) {
            IInitializeBase initializer = PluginManager.getInitializer(platformType);

            initializerHandlers.stream()
                    .filter(handler -> handler.supports(platformType))
                    .findFirst()
                    .orElseThrow(() -> new TOMException("No handler found for platform: " + platformType))
                    .initialize(initializer, platformVariant);

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
            cleaner.cleanUp();
        }
        TestContext.clear();
    }

    @BeforeSuite(alwaysRun = true)
    public void initPlugins() {
        PluginManager.loadPlugins();
    }
}