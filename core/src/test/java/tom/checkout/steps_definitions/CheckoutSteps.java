package tom.checkout.steps_definitions;

import authentication.tasks.PerformAuthentication;
import authentication.tasks.PerformUrlNavigation;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import services.tasks.TaskResolver;
import tom.authentication.dao.UserCredentials;
import tom.services.TestContext;
import tom.utils.SharedSteps;

public class CheckoutSteps extends SharedSteps {

    public CheckoutSteps(TestContext testContext) {
        super(testContext);
    }

    @Given("the application is launched")
    public void theApplicationIsLaunched() {
        TaskResolver.of(taskMap, PerformUrlNavigation.class).execute();
    }

    @When("SauceLab user submit credentials {string} and {string}")
    public void iSubmitCredentials(String username, String password) {
        user = new UserCredentials(username, password);
        TaskResolver.of(taskMap, PerformAuthentication.class)
                .with(user.getUsername())
                .with(user.getPassword())
                .execute();
    }

    @Then("the checkout page should be displayed")
    public void checkoutPageShouldBeVisible() {

    }

    @When("he has added items to the cart")
    public void heHasAddedItemsToTheCart() {
    }

    @Then("the cart should display all added items with correct title and price")
    public void theCartShouldDisplayAllAddedItemsWithCorrectTitleAndPrice() {
    }

    @When("he removes a product")
    public void heRemovesAProduct() {
    }

    @Then("the product should no longer appear in the cart")
    public void theProductShouldNoLongerAppearInTheCart() {
    }

    @When("he proceed to checkout")
    public void heProceedToCheckout() {
    }
}
