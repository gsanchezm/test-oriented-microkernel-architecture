package tom.inventory.steps_definitions;

import authentication.tasks.PerformAuthentication;
import authentication.tasks.PerformUrlNavigation;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import services.tasks.TaskResolver;
import tom.authentication.dao.UserCredentials;
import tom.services.TestContext;
import tom.utils.SharedSteps;

public class InventorySteps extends SharedSteps {

    public InventorySteps(TestContext testContext) {
        super(testContext);
    }

    @Given("the application is launched")
    public void theApplicationIsLaunched() {
        TaskResolver.of(taskMap, PerformUrlNavigation.class).execute();
    }

    @When("SauceLab user submit credentials {string} and {string}")
    public void i_submit_credentials(String username, String password) {
        UserCredentials user = new UserCredentials(username, password);
        TaskResolver.of(taskMap, PerformAuthentication.class)
                .with(user.getUsername())
                .with(user.getPassword())
                .execute();
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
