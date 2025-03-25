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
import tom.utils.BaseTestRunner;

@CucumberOptions(
        plugin = {
                "pretty",
                "com.aventstack.chaintest.plugins.ChainTestCucumberListener:"
        },
        features = "src/test/java/tom/authentication/features",
        glue = "tom.authentication.steps_definitions"
)
public class AuthenticationTest extends BaseTestRunner {
}