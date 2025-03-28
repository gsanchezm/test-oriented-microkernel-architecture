package tom.inventory.runner;

import io.cucumber.testng.CucumberOptions;
import tom.utils.BaseTestRunner;

@CucumberOptions(
        plugin = {
                "pretty",
        },
        features = "src/test/java/tom/inventory/features",
        glue = "tom.inventory.steps_definitions"
)
public class InventoryTest extends BaseTestRunner {

}
