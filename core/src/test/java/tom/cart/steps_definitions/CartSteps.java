package tom.cart.steps_definitions;

import authentication.tasks.PerformAuthentication;
import authentication.tasks.PerformUrlNavigation;
import cart.tasks.PerformCheckout;
import cart.tasks.PerformNavigationToCart;
import cart.tasks.PerformRemoveProduct;
import cart.validations.IsProductRemovedFromCart;
import checkout.validations.IsUserOnCheckout;
import inventory.tasks.PerformAddItemToCart;
import inventory.validations.IsProductAddedToCart;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import services.tasks.TaskResolver;
import services.validations.ValidationResolver;
import tom.authentication.dao.UserCredentials;
import tom.inventory.dao.ProductInfo;
import tom.inventory.dao.ProductList;
import tom.services.TestContext;
import tom.utils.SharedSteps;
import utils.JsonDataLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

public class CartSteps extends SharedSteps {

    private final List<ProductInfo> selectedProducts = new ArrayList<>();

    public CartSteps(TestContext testContext) {
        super(testContext);
    }

    @Given("the application is launched")
    public void theApplicationIsLaunched() {
        loadSelectedProducts();
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
        then(ValidationResolver.of(validationMap, IsUserOnCheckout.class).validate()).isTrue();

    }

    // ✅ Safely load products when the test is already running
    private void loadSelectedProducts() {
        List<ProductInfo> allProducts = JsonDataLoader
                .loadFromData("products.json", ProductList.class)
                .getProducts();

        selectedProducts.addAll(allProducts.stream()
                .filter(p -> Double.parseDouble(p.getPrice()) < 20.00)
                .toList());
    }
}
