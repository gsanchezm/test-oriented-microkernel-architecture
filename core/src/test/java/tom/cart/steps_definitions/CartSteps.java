package tom.cart.steps_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import services.tasks.TaskResolver;
import services.validations.ValidationResolver;
import tom.authentication.dao.UserInformation;
import tom.inventory.dao.Product;
import tom.services.TestContext;
import tom.services.TestDataContext;
import tom.utils.SharedSteps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static config.Constants.EMPTY;
import static org.assertj.core.api.BDDAssertions.then;

public class CartSteps extends SharedSteps {

    private final List<Product> selectedProducts = new ArrayList<>();

    public CartSteps(TestContext testContext) {
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
                .with(TestDataContext.getPlatformVariant())
                .execute();
    }

    @When("he has added items to the cart")
    public void heHasAddedItemsToTheCart() throws IOException {
        selectedProducts.forEach(p -> TaskResolver.of("PerformAddItemToCart")
                .with(p.getTitle())
                .execute());

        TaskResolver.of("PerformNavigationToCart").execute();
    }

    @Then("the cart should display all added items with correct title and price")
    public void theCartShouldDisplayAllAddedItemsWithCorrectTitleAndPrice() {
        selectedProducts.forEach(p -> {
                    then(ValidationResolver.of("IsProductAddedToCart")
                            .with(p.getTitle())
                            .validate())
                            .isTrue();
                }
        );
    }

    @When("he removes a product")
    public void heRemovesAProduct() {
        selectedProducts.forEach(p -> {
                    TaskResolver.of("PerformRemoveProduct")
                            .with(p.getTitle())
                            .execute();
                }
        );
    }

    @Then("the product should no longer appear in the cart")
    public void theProductShouldNoLongerAppearInTheCart() {
        then(ValidationResolver.of("IsProductRemovedFromCart")
                .validate())
                .isTrue();
    }

    @When("he proceed to checkout")
    public void heProceedToCheckout() {
        TaskResolver.of("PerformCheckout").execute();
    }

    @Then("the checkout page should be displayed")
    public void theCheckoutPageShouldBeDisplayed() {
        then(ValidationResolver.of("IsUserOnCheckoutStepOne").validate()).isTrue();

    }

    // ✅ Safely load products when the test is already running
    private void loadSelectedProducts() {
        selectedProducts.addAll(getProductsBelowPrice(20.00));
    }
}