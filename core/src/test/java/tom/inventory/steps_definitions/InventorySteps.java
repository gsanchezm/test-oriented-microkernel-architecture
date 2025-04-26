package tom.inventory.steps_definitions;

import io.cucumber.java.en.And;
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

import static config.Constants.EMPTY;
import static org.assertj.core.api.BDDAssertions.then;

public class InventorySteps extends SharedSteps {

    public InventorySteps(TestContext testContext) {
        super(testContext);
    }

    @Given("the application is launched")
    public void theApplicationIsLaunched() {
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

    @Given("he is on the inventory page")
    public void theUserIsOnTheInventoryPage() {
        ValidationResolver.of("IsUserOnInventory").validate();
    }

    @Then("the user should see a list of available products")
    public void verifyProductListVisible() {
        then(ValidationResolver.of("AreProductsDisplayed").validate()).isTrue();
    }

    @When("the user adds the product {string} to the cart")
    public void addProductToCart(String productName) {
        product.set(new Product(EMPTY, productName,EMPTY,EMPTY));
        TaskResolver.of("PerformAddItemToCart").with(product.get().getTitle()).execute();
    }

    @Then("the cart should reflect the item {string}")
    public void verifyItemInCart(String productName) {
        product.set(new Product(EMPTY, productName,EMPTY,EMPTY));
        TaskResolver.of("PerformNavigationToCart").execute();
        then(ValidationResolver.of("IsProductAddedToCart").with(product.get().getTitle()).validate()).isTrue();
    }

    @When("the user selects the sort option {string}")
    public void sortProducts(String sortOption) {
        TaskResolver.of("PerformProductsSort").with(sortOption).execute();

    }

    @Then("the products should be sorted accordingly to {string}")
    public void theProductsShouldBeSortedAccordinglyTo(String sortOption) {
        then(ValidationResolver.of("AreProductsSorted").with(sortOption).validate()).isTrue();
    }

    @Then("the displayed {string} price should be {string}")
    public void theDisplayedPriceShouldBe(String productName, String expectedPrice) {
        product.set(new Product(EMPTY,productName, EMPTY, expectedPrice));
        then(ValidationResolver.of("IsProductInformationDisplayed").
                with(product.get().getTitle()).
                with(product.get().getDescription()).
                with(product.get().getPrice())
                .validate()).isTrue();
    }

    @And("the {string} must be removed")
    public void theMustBeRemoved(String productName) {
        product.set(new Product(EMPTY,productName, EMPTY, EMPTY));
        TaskResolver.of("PerformRemoveProduct")
                .with(product.get().getTitle())
                .execute();
    }
}
