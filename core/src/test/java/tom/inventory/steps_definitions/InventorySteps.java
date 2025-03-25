package tom.inventory.steps_definitions;

import authentication.tasks.PerformUrlNavigation;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import services.tasks.TaskResolver;
import tom.inventory.runner.InventoryTest;
import tom.services.TestContext;
import tom.utils.SharedSteps;

public class InventorySteps extends SharedSteps {

    public InventorySteps(TestContext testContext) {
        super(testContext);
        this.testContext = InventoryTest.getTestContext();
    }

    @Given("the application is launched")
    public void launchApplication() {
        TaskResolver.of(taskMap, PerformUrlNavigation.class).execute();
    }

    @When("the user navigates to the inventory page")
    public void navigateToInventoryPage() {

    }

    @Then("the user should see a list of available products")
    public void verifyProductListVisible() {

    }

    @When("the user adds the product {string} to the cart")
    public void addProductToCart(String productName) {

    }

    @Then("the cart should reflect the item {string}")
    public void verifyItemInCart(String productName) {

    }

    @When("the user checks the price of {string}")
    public void checkProductPrice(String productName) {

    }

    @Then("the displayed price should be {string}")
    public void validateDisplayedPrice(String expectedPrice) {

    }

    @When("the user selects the sort option {string}")
    public void sortProducts(String sortOption) {

    }

    @Then("the products should be sorted accordingly")
    public void verifySortedOrder() {

    }

    @Given("the user is on the inventory page")
    public void theUserIsOnTheInventoryPage() {
    }
}
