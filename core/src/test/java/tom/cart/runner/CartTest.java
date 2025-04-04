package tom.cart.runner;

import io.cucumber.testng.CucumberOptions;
import tom.utils.BaseTestRunner;

@CucumberOptions(
        plugin = {
                "pretty",
        },
        features = "src/test/java/tom/cart/features",
        glue = "tom.cart.steps_definitions"
)
public class CartTest extends BaseTestRunner {
}
