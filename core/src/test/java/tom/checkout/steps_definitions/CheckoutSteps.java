package tom.checkout.steps_definitions;

import mobile.authentication.tasks.PerformAuthentication;
import mobile.authentication.tasks.PerformResetAppState;
import mobile.authentication.tasks.PerformOpenSauceApp;
import mobile.cart.tasks.PerformCheckout;
import mobile.cart.tasks.PerformContinueShopping;
import mobile.cart.tasks.PerformNavigationToCart;
import mobile.cart.validations.IsUserOnCart;
import mobile.checkout.tasks.PerformCancelCheckout;
import mobile.checkout.tasks.PerformContinueToStepTwo;
import mobile.checkout.tasks.PerformFillYourInformation;
import mobile.checkout.tasks.PerformFinishCheckout;
import mobile.checkout.validations.IsSummaryInformationDisplayed;
import mobile.checkout.validations.IsUserOnCheckoutStepOne;
import mobile.checkout.validations.IsUserOnCheckoutComplete;
import mobile.checkout.validations.IsUserOnCheckoutStepTwo;
import mobile.inventory.tasks.PerformAddItemToCart;
import mobile.inventory.validations.IsProductAddedToCart;
import mobile.inventory.validations.IsUserOnInventory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import services.tasks.TaskResolver;
import services.validations.ValidationResolver;
import tom.authentication.dao.UserInformation;
import tom.inventory.dao.Product;
import tom.services.TestContext;
import tom.utils.SharedSteps;

import java.util.ArrayList;
import java.util.List;

import static config.Constants.EMPTY;
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
        TaskResolver.of(taskMap, PerformOpenSauceApp.class).execute();
    }

    @When("SauceLab user submit credentials {string} and {string}")
    public void iSubmitCredentials(String username, String password) {
        UserInformation getUser = getUserData();
        user.set(new UserInformation(getUser.getUsername(), getUser.getPassword(), EMPTY, EMPTY, EMPTY));
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
        UserInformation user = new UserInformation(EMPTY, EMPTY, firstName,lastName,postalCode);
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
        UserInformation user = getUserData();
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
