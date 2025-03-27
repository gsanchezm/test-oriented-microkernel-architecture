package tom.authentication.runner;

import io.cucumber.testng.CucumberOptions;
import tom.utils.BaseTestRunner;

@CucumberOptions(
        plugin = {
                "pretty",
        },
        features = "src/test/java/tom/authentication/features",
        glue = "tom.authentication.steps_definitions"
)
public class AuthenticationTest extends BaseTestRunner {
}