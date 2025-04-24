package tom.checkout.steps_definitions;

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
        TaskResolver.of("PerformOpenSauceApp").execute();
    }

    @When("SauceLab user submit credentials")
    public void iSubmitCredentials() {
        UserInformation getUser = getDefaultUser();
        user.set(new UserInformation(getUser.getUsername(), getUser.getPassword(), EMPTY, EMPTY, EMPTY));
        TaskResolver.of("PerformAuthentication")
                .with(user.get().getUsername())
                .with(user.get().getPassword())
                .execute();
    }

    @Given("he has items in the cart")
    public void heHasItemsInTheCart() {
        addSelectedProductsToCart();

        TaskResolver.of("PerformNavigationToCart").execute();
    }

    @And("the app is on clean status")
    public void theAppIsOnCleanStatus() {
        resetAppAndReturnToStore();
        addSelectedProductsToCart();
        proceedToCheckout();
    }

    @When("he is on checkout step one")
    public void heIsOnCheckoutStepOne() {
        TaskResolver.of("PerformCheckout").execute();
        then(ValidationResolver.of("IsUserOnCheckoutStepOne").validate()).isTrue();
    }

    @When("he submits {string}, {string}, and {string}")
    public void heSubmitsPersonalInfo(String firstName, String lastName, String postalCode) {
        UserInformation user = new UserInformation(EMPTY, EMPTY, firstName,lastName,postalCode);
        TaskResolver.of("PerformFillYourInformation")
                .with(user.getFirstName()).with(user.getLastName()).with(user.getPostalCode())
                .execute();

        TaskResolver.of("PerformContinueToStepTwo").execute();
    }

    @Then("next step is checkout step two")
    public void nextStepIsCheckoutStepTwo() {
        then(ValidationResolver.of("IsUserOnCheckoutStepTwo").validate()).isTrue();
    }

    @And("he is on checkout step two")
    public void heIsOnCheckoutStepTwo() {
        UserInformation user = getDefaultUser();
        TaskResolver.of("PerformFillYourInformation")
                .with(user.getFirstName()).with(user.getLastName()).with(user.getPostalCode())
                .execute();

        TaskResolver.of("PerformContinueToStepTwo").execute();

        then(ValidationResolver.of("IsUserOnCheckoutStepTwo").validate()).isTrue();
    }

    @And("include {string}, {string}, {string}, {string} and {string}")
    public void includeAnd(String paymentMethod, String shippingMethod, String itemTotal, String tax, String total) {
        then(ValidationResolver.of("IsSummaryInformationDisplayed")
                .with(paymentMethod).with(shippingMethod).with(itemTotal)
                .with(tax).with(total).validate()).isTrue();
    }

    @When("he finishes the checkout")
    public void heFinishesTheCheckout() {
        TaskResolver.of("PerformFinishCheckout").execute();
    }

    @Then("the confirmation should be displayed")
    public void theConfirmationShouldBeDisplayed() {
        then(ValidationResolver.of("IsUserOnCheckoutComplete").validate()).isTrue();
    }

    @When("he cancels the checkout")
    public void heCancelsTheCheckout() {
        TaskResolver.of("PerformCancelCheckout").execute();
    }

    @Then("the cart stage should be displayed")
    public void theCartStageShouldBeDisplayed() {
        then(ValidationResolver.of("IsUserOnCart").validate()).isTrue();
    }

    @Given("he is on the inventory page")
    public void theUserIsOnTheInventoryPage() {
        ValidationResolver.of("IsUserOnInventory").validate();
    }

    @Then("it should include the products added previously")
    public void itShouldIncludeTheProductsAddedPreviously() {
        selectedProducts.forEach(p -> {
                    then(ValidationResolver.of("IsProductAddedToCart")
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
        TaskResolver.of("PerformCancelCheckout").execute();
        TaskResolver.of("PerformContinueShopping").execute();
        TaskResolver.of("PerformResetAppState").execute();
    }

    private void addSelectedProductsToCart() {
        selectedProducts.forEach(p ->
                TaskResolver.of("PerformAddItemToCart")
                        .with(p.getTitle())
                        .execute()
        );
    }

    private void proceedToCheckout() {
        TaskResolver.of("PerformNavigationToCart").execute();
        TaskResolver.of("PerformCheckout").execute();
    }

}
