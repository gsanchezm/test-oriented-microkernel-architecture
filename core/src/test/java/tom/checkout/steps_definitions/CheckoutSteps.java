package tom.checkout.steps_definitions;

import authentication.tasks.PerformAuthentication;
import authentication.tasks.PerformUrlNavigation;
import cart.validations.IsProductRemovedFromCart;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import services.tasks.TaskResolver;
import services.validations.ValidationResolver;
import tom.authentication.dao.UserCredentials;
import tom.services.TestContext;
import tom.utils.SharedSteps;

import static org.assertj.core.api.BDDAssertions.then;

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

    @When("he is on checkout page")
    public void heIsOnCheckoutPage() {
    }

    @Given("he has items in the cart")
    public void heHasItemsInTheCart() {
    }

    @When("he initiates the checkout")
    public void heInitiatesTheCheckout() {
    }

    @Then("the checkout step one screen should be displayed")
    public void theCheckoutStepOneScreenShouldBeDisplayed() {
    }

    @Given("he is on checkout step one")
    public void heIsOnCheckoutStepOne() {
    }

    @When("he submits {string}, {string}, and {string}")
    public void heSubmitsPersonalInfo(String firstName, String lastName, String postalCode) {
        // You can now use: firstName, lastName, postalCode
    }

    @Then("the checkout step two screen should be displayed")
    public void theCheckoutStepTwoScreenShouldBeDisplayed() {
    }

    @Given("he is on checkout step two")
    public void heIsOnCheckoutStepTwo() {
    }

    @And("the payment method should be {string}")
    public void thePaymentMethodShouldBe(String arg0, String arg1) {
    }

    @And("the shipping method should be {string}")
    public void theShippingMethodShouldBe(String arg0, String arg1) {
    }

    @And("the item total should be {}")
    public void theItemTotalShouldBe(String arg0) {
    }

    @And("the tax should be {}")
    public void theTaxShouldBe(String arg0) {
    }

    @And("the final total should be {}")
    public void theFinalTotalShouldBe(String arg0) {
    }

    @When("he finishes the checkout")
    public void heFinishesTheCheckout() {
    }

    @Then("the confirmation screen should be displayed")
    public void theConfirmationScreenShouldBeDisplayed() {
    }

    @When("he cancels the checkout")
    public void heCancelsTheCheckout() {
    }

    @Then("the cart screen should be displayed")
    public void theCartScreenShouldBeDisplayed() {
    }

    @Then("it should include {string} priced at <price{int}>")
    public void itShouldIncludePricedAt(String arg0, String arg1, String arg2) {
    }
}
