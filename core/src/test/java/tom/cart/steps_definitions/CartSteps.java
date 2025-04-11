package tom.cart.steps_definitions;

import mobile.authentication.tasks.PerformAuthentication;
import mobile.authentication.tasks.PerformOpenSauceApp;
import mobile.cart.tasks.PerformCheckout;
import mobile.cart.tasks.PerformNavigationToCart;
import mobile.cart.tasks.PerformRemoveProduct;
import mobile.cart.validations.IsProductRemovedFromCart;
import mobile.checkout.validations.IsUserOnCheckoutStepOne;
import mobile.inventory.tasks.PerformAddItemToCart;
import mobile.inventory.validations.IsProductAddedToCart;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import services.tasks.TaskResolver;
import services.validations.ValidationResolver;
import tom.authentication.dao.UserInformation;
import tom.inventory.dao.Product;
import tom.services.TestContext;
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

    @When("he has added items to the cart")
    public void heHasAddedItemsToTheCart() throws IOException {
        selectedProducts.forEach(p -> TaskResolver.of(taskMap, PerformAddItemToCart.class)
                .with(p.getTitle())
                .execute());

        TaskResolver.of(taskMap, PerformNavigationToCart.class).execute();
    }

    @Then("the cart should display all added items with correct title and price")
    public void theCartShouldDisplayAllAddedItemsWithCorrectTitleAndPrice() {
        selectedProducts.forEach(p -> {
                    then(ValidationResolver.of(validationMap, IsProductAddedToCart.class)
                            .with(p.getTitle())
                            .validate())
                            .isTrue();
                }
        );
    }

    @When("he removes a product")
    public void heRemovesAProduct() {
        selectedProducts.forEach(p -> {
                    TaskResolver.of(taskMap, PerformRemoveProduct.class)
                            .with(p.getTitle())
                            .execute();
                }
        );
    }

    @Then("the product should no longer appear in the cart")
    public void theProductShouldNoLongerAppearInTheCart() {
        then(ValidationResolver.of(validationMap, IsProductRemovedFromCart.class)
                .validate())
                .isTrue();
    }

    @When("he proceed to checkout")
    public void heProceedToCheckout() {
        TaskResolver.of(taskMap, PerformCheckout.class).execute();
    }

    @Then("the checkout page should be displayed")
    public void theCheckoutPageShouldBeDisplayed() {
        then(ValidationResolver.of(validationMap, IsUserOnCheckoutStepOne.class).validate()).isTrue();

    }

    // ✅ Safely load products when the test is already running
    private void loadSelectedProducts() {
        selectedProducts.addAll(getProductsBelowPrice(20.00));
    }
}
