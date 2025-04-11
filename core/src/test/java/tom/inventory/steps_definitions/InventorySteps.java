package tom.inventory.steps_definitions;

import mobile.authentication.tasks.PerformAuthentication;
import mobile.authentication.tasks.PerformOpenSauceApp;
import mobile.cart.tasks.PerformNavigationToCart;
import mobile.cart.tasks.PerformRemoveProduct;
import mobile.inventory.tasks.PerformAddItemToCart;
import mobile.inventory.tasks.PerformProductsSort;
import mobile.inventory.validations.IsProductAddedToCart;
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

import static config.Constants.EMPTY;
import static org.assertj.core.api.BDDAssertions.then;

public class InventorySteps extends SharedSteps {

    public InventorySteps(TestContext testContext) {
        super(testContext);
    }

    @Given("the application is launched")
    public void theApplicationIsLaunched() {
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

    @Given("he is on the inventory page")
    public void theUserIsOnTheInventoryPage() {
        ValidationResolver.of(validationMap, mobile.inventory.validations.IsUserOnInventory.class).validate();
    }

    @Then("the user should see a list of available products")
    public void verifyProductListVisible() {
        then(ValidationResolver.of(validationMap, mobile.inventory.validations.AreProductsDisplayed.class).validate()).isTrue();
    }

    @When("the user adds the product {string} to the cart")
    public void addProductToCart(String productName) {
        product.set(new Product(EMPTY, productName,EMPTY,EMPTY));
        TaskResolver.of(taskMap, PerformAddItemToCart.class).with(product.get().getTitle()).execute();
    }

    @Then("the cart should reflect the item {string}")
    public void verifyItemInCart(String productName) {
        product.set(new Product(EMPTY, productName,EMPTY,EMPTY));
        TaskResolver.of(taskMap, PerformNavigationToCart.class).execute();
        then(ValidationResolver.of(validationMap, IsProductAddedToCart.class).with(product.get().getTitle()).validate()).isTrue();
    }

    @When("the user selects the sort option {string}")
    public void sortProducts(String sortOption) {
        TaskResolver.of(taskMap, PerformProductsSort.class).with(sortOption).execute();

    }

    @Then("the products should be sorted accordingly to {string}")
    public void theProductsShouldBeSortedAccordinglyTo(String sortOption) {
        then(ValidationResolver.of(validationMap, mobile.inventory.validations.AreProductsSorted.class).with(sortOption).validate()).isTrue();
    }

    @Then("the displayed {string} price should be {string}")
    public void theDisplayedPriceShouldBe(String productName, String expectedPrice) {
        product.set(new Product(EMPTY,productName, EMPTY, expectedPrice));
        then(ValidationResolver.of(validationMap, mobile.inventory.validations.IsProductInformationDisplayed.class).
                with(product.get().getTitle()).
                with(product.get().getDescription()).
                with(product.get().getPrice())
                .validate()).isTrue();
    }

    @And("the {string} must be removed")
    public void theMustBeRemoved(String productName) {
        product.set(new Product(EMPTY,productName, EMPTY, EMPTY));
        TaskResolver.of(taskMap, PerformRemoveProduct.class)
                .with(product.get().getTitle())
                .execute();
    }
}
