package tom.checkout.runner;

import io.cucumber.testng.CucumberOptions;
import tom.utils.BaseTestRunner;

@CucumberOptions(
        plugin = {
                "pretty",
        },
        features = "src/test/java/tom/checkout/features",
        glue = "tom.checkout.steps_definitions"
)
public class CheckoutTest extends BaseTestRunner {
}
