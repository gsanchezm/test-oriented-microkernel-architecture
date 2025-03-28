package tom.inventory.steps_definitions;

import authentication.tasks.PerformAuthentication;
import authentication.tasks.PerformUrlNavigation;
import inventory.tasks.PerformAddItemToCart;
import inventory.tasks.PerformProductsSort;
import inventory.validations.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import services.tasks.TaskResolver;
import services.validations.ValidationResolver;
import tom.authentication.dao.UserCredentials;
import tom.inventory.dao.ProductInfo;
import tom.services.TestContext;
import tom.utils.SharedSteps;

import static org.assertj.core.api.BDDAssertions.then;

public class InventorySteps extends SharedSteps {

    public InventorySteps(TestContext testContext) {
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

    @Given("he is on the inventory page")
    public void theUserIsOnTheInventoryPage() {
        ValidationResolver.of(validationMap, IsUserOnInventory.class).validate();
    }

    @Then("the user should see a list of available products")
    public void verifyProductListVisible() {
        then(ValidationResolver.of(validationMap, AreProductsDisplayed.class).validate()).isTrue();
    }

    @When("the user adds the product {string} to the cart")
    public void addProductToCart(String productName) {
        productInfo = new ProductInfo(productName,"","");
        TaskResolver.of(taskMap, PerformAddItemToCart.class).with(productInfo.getTitle()).execute();
    }

    @Then("the cart should reflect the item {string}")
    public void verifyItemInCart(String productName) {
        productInfo = new ProductInfo(productName,"","");
        then(ValidationResolver.of(validationMap, IsProductAddedToCart.class).with(productInfo.getTitle()).validate()).isTrue();
    }

    @When("the user selects the sort option {string}")
    public void sortProducts(String sortOption) {
        TaskResolver.of(taskMap, PerformProductsSort.class).with(sortOption).execute();

    }

    @Then("the products should be sorted accordingly to {string}")
    public void theProductsShouldBeSortedAccordinglyTo(String sortOption) {
        then(ValidationResolver.of(validationMap, AreProductsSorted.class).with(sortOption).validate()).isTrue();
    }

    @Then("the displayed {string} price should be {string}")
    public void theDisplayedPriceShouldBe(String productName, String expectedPrice) {
        productInfo = new ProductInfo(productName, "", expectedPrice);
        then(ValidationResolver.of(validationMap, IsProductInformationDisplayed.class).
                with(productInfo.getTitle()).
                with(productInfo.getDescription()).
                with(productInfo.getPrice())
                .validate()).isTrue();
    }
}
