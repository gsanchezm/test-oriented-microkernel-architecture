package tom.authentication.steps_definitions;

import authentication.tasks.PerformAuthentication;
import authentication.tasks.PerformCloseCurrentSession;
import authentication.tasks.PerformUrlNavigation;
import authentication.validations.IsAuthErrorMessageDisplayed;
import authentication.validations.IsUserOnAuthenticationPage;
import inventory.validations.IsUserOnInventory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import services.validations.ValidationResolver;
import tom.authentication.dao.UserCredentials;
import tom.authentication.runner.AuthenticationTest;
import tom.services.TestContext;
import services.tasks.TaskResolver;
import tom.utils.SharedSteps;

import static org.assertj.core.api.BDDAssertions.then;

public class AuthenticationSteps extends SharedSteps {

    public AuthenticationSteps(TestContext testContext) {
        super(testContext);
        this.testContext = AuthenticationTest.getTestContext();
    }

    @Given("the application is launched")
    public void theApplicationIsLaunched() {
        TaskResolver.of(taskMap, PerformUrlNavigation.class).execute();
    }

    @When("SauceLab user submit credentials {string} and {string}")
    public void i_submit_credentials(String username, String password) {
        UserCredentials user = new UserCredentials(username, password);
        TaskResolver.of(taskMap, PerformAuthentication.class)
                .with(user.getUsername())
                .with(user.getPassword())
                .execute();
    }

    @When("SauceLab user submit empty credentials")
    public void i_submit_empty_credentials() {
        TaskResolver.of(taskMap, PerformAuthentication.class)
                .with("")
                .with("")
                .execute();
    }

    @Then("the system should grant access")
    public void the_system_should_grant_access_or_show_an_error() {
        then(ValidationResolver.of(validationMap, IsUserOnInventory.class).validate()).isTrue();
    }

    @When("he log out")
    public void i_log_out() {
        TaskResolver.of(taskMap, PerformCloseCurrentSession.class).execute();
    }

    @Then("the system should return to the login page")
    public void the_system_should_return_to_the_login_page() {
        then(ValidationResolver.of(validationMap, IsUserOnAuthenticationPage.class).validate()).isTrue();
    }

    @Then("the system should show the error {string}")
    public void theSystemShouldShowTheError(String errorText) {
        then(ValidationResolver.of(validationMap, IsAuthErrorMessageDisplayed.class).
                with(errorText).validate()).isTrue();
    }
}
