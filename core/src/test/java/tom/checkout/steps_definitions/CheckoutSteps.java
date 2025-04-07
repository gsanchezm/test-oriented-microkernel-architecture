package tom.checkout.steps_definitions;

import authentication.tasks.PerformAuthentication;
import authentication.tasks.PerformResetAppState;
import authentication.tasks.PerformUrlNavigation;
import cart.tasks.PerformCheckout;
import cart.tasks.PerformContinueShopping;
import cart.tasks.PerformNavigationToCart;
import cart.tasks.PerformRemoveProduct;
import cart.validations.IsUserOnCart;
import checkout.tasks.PerformCancelCheckout;
import checkout.tasks.PerformContinueToStepTwo;
import checkout.tasks.PerformFillYourInformation;
import checkout.tasks.PerformFinishCheckout;
import checkout.validations.IsSummaryInformationDisplayed;
import checkout.validations.IsUserOnCheckoutStepOne;
import checkout.validations.IsUserOnCheckoutComplete;
import checkout.validations.IsUserOnCheckoutStepTwo;
import inventory.tasks.PerformAddItemToCart;
import inventory.validations.IsProductAddedToCart;
import inventory.validations.IsUserOnInventory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import services.tasks.TaskResolver;
import services.validations.ValidationResolver;
import tom.authentication.dao.UserCredentials;
import tom.checkout.dao.Checkout;
import tom.inventory.dao.Product;
import tom.inventory.dao.ProductList;
import tom.services.TestContext;
import tom.utils.SharedSteps;
import utils.JsonDataLoader;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

public class CheckoutSteps extends SharedSteps{

    private final List<Product> selectedProducts = new ArrayList<>();
    private final List<Product> allProducts = new ArrayList<>();;

    private static final List<String> titlesToSelect = List.of(
            "Sauce Labs Bolt T-Shirt",
            "Sauce Labs Onesie",
            "Test.allTheThings() T-Shirt (Red)"
    );

    public CheckoutSteps(TestContext testContext) {
        super(testContext);
    }

    @Given("the application is launched")
    public void theApplicationIsLaunched() {
        loadSelectedProducts();
        TaskResolver.of(taskMap, PerformUrlNavigation.class).execute();
    }

    @When("SauceLab user submit credentials {string} and {string}")
    public void iSubmitCredentials(String username, String password) {
        user.set(new UserCredentials(username, password));
        TaskResolver.of(taskMap, PerformAuthentication.class)
                .with(user.get().getUsername())
                .with(user.get().getPassword())
                .execute();
    }

    @Given("he has items in the cart")
    public void heHasItemsInTheCart() {
        addSelectedProductsToCart();

        TaskResolver.of(taskMap, PerformNavigationToCart.class).execute();
    }

    @And("the app is on clean status")
    public void theAppIsOnCleanStatus() {
        resetAppAndReturnToStore();
        addSelectedProductsToCart();
        proceedToCheckout();
    }

    @When("he is on checkout step one")
    public void heIsOnCheckoutStepOne() {
        TaskResolver.of(taskMap, PerformCheckout.class).execute();
        then(ValidationResolver.of(validationMap, IsUserOnCheckoutStepOne.class).validate()).isTrue();
    }

    @When("he submits {string}, {string}, and {string}")
    public void heSubmitsPersonalInfo(String firstName, String lastName, String postalCode) {
        Checkout user = new Checkout(firstName,lastName,postalCode);
        TaskResolver.of(taskMap, PerformFillYourInformation.class)
                .with(user.getFirstName()).with(user.getLastName()).with(user.getPostalCode())
                .execute();

        TaskResolver.of(taskMap, PerformContinueToStepTwo.class).execute();
    }

    @Then("next step is checkout step two")
    public void nextStepIsCheckoutStepTwo() {

        then(ValidationResolver.of(validationMap, IsUserOnCheckoutStepTwo.class).validate()).isTrue();
    }

    @And("he is on checkout step two")
    public void heIsOnCheckoutStepTwo() {
        Checkout user = getCheckoutData("standard_user");
        TaskResolver.of(taskMap, PerformFillYourInformation.class)
                .with(user.getFirstName()).with(user.getLastName()).with(user.getPostalCode())
                .execute();

        TaskResolver.of(taskMap, PerformContinueToStepTwo.class).execute();

        then(ValidationResolver.of(validationMap, IsUserOnCheckoutStepTwo.class).validate()).isTrue();
    }

    @And("include {string}, {string}, {string}, {string} and {string}")
    public void includeAnd(String paymentMethod, String shippingMethod, String itemTotal, String tax, String total) {
        then(ValidationResolver.of(validationMap, IsSummaryInformationDisplayed.class)
                .with(paymentMethod).with(shippingMethod).with(itemTotal)
                .with(tax).with(total).validate()).isTrue();
    }

    @When("he finishes the checkout")
    public void heFinishesTheCheckout() {
        TaskResolver.of(taskMap, PerformFinishCheckout.class).execute();
    }

    @Then("the confirmation should be displayed")
    public void theConfirmationShouldBeDisplayed() {
        then(ValidationResolver.of(validationMap, IsUserOnCheckoutComplete.class).validate()).isTrue();
    }

    @When("he cancels the checkout")
    public void heCancelsTheCheckout() {
        TaskResolver.of(taskMap, PerformCancelCheckout.class).execute();
    }

    @Then("the cart stage should be displayed")
    public void theCartStageShouldBeDisplayed() {
        then(ValidationResolver.of(validationMap, IsUserOnCart.class).validate()).isTrue();
    }

    @Given("he is on the inventory page")
    public void theUserIsOnTheInventoryPage() {
        ValidationResolver.of(validationMap, IsUserOnInventory.class).validate();
    }

    @Then("it should include the products added previously")
    public void itShouldIncludeTheProductsAddedPreviously() {
        selectedProducts.forEach(p -> {
                    then(ValidationResolver.of(validationMap, IsProductAddedToCart.class)
                            .with(p.getTitle())
                            .validate())
                            .isTrue();
                }
        );
    }

    // ✅ Safely load products when the test is already running
    private void loadSelectedProducts() {
        selectedProducts.addAll(getProductsByTitles(titlesToSelect));
    }

    private void resetAppAndReturnToStore() {
        TaskResolver.of(taskMap, PerformCancelCheckout.class).execute();
        TaskResolver.of(taskMap, PerformContinueShopping.class).execute();
        TaskResolver.of(taskMap, PerformResetAppState.class).execute();
    }

    private void addSelectedProductsToCart() {
        selectedProducts.forEach(p ->
                TaskResolver.of(taskMap, PerformAddItemToCart.class)
                        .with(p.getTitle())
                        .execute()
        );
    }

    private void proceedToCheckout() {
        TaskResolver.of(taskMap, PerformNavigationToCart.class).execute();
        TaskResolver.of(taskMap, PerformCheckout.class).execute();
    }

}
