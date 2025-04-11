package tom.authentication.steps_definitions;

import mobile.authentication.tasks.PerformAuthentication;
import mobile.authentication.tasks.PerformCloseCurrentSession;
import mobile.authentication.tasks.PerformOpenSauceApp;
import mobile.authentication.validations.IsAuthErrorMessageDisplayed;
import mobile.authentication.validations.IsUserOnAuthentication;
import mobile.inventory.validations.IsUserOnInventory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import services.validations.ValidationResolver;
import tom.authentication.dao.UserInformation;
import tom.services.TestContext;
import services.tasks.TaskResolver;
import tom.utils.SharedSteps;

import static config.Constants.EMPTY;
import static org.assertj.core.api.BDDAssertions.then;

public class AuthenticationSteps extends SharedSteps {

    public AuthenticationSteps(TestContext testContext) {
        super(testContext);
    }

    @Given("the application is launched")
    public void theApplicationIsLaunched() {
        TaskResolver.of(taskMap, PerformOpenSauceApp.class).execute();
    }

    @When("SauceLab user submit credentials")
    public void iSubmitCredentials() {
        UserInformation getUser = getUserData();
        user.set(new UserInformation(getUser.getUsername(), getUser.getPassword(), EMPTY, EMPTY, EMPTY));
        TaskResolver.of(taskMap, PerformAuthentication.class)
                .with(user.get().getUsername())
                .with(user.get().getPassword())
                .execute();
    }

    @When("SauceLab user submit empty credentials")
    public void iSubmitEmptyCredentials() {
        TaskResolver.of(taskMap, PerformAuthentication.class)
                .with("")
                .with("")
                .execute();
    }

    @Then("the system should grant access")
    public void theSystemShouldGrantAccessOrShowAnError() {
        then(ValidationResolver.of(validationMap, IsUserOnInventory.class).validate()).isTrue();
    }

    @When("he log out")
    public void iLogOut() {
        TaskResolver.of(taskMap, PerformCloseCurrentSession.class).execute();
    }

    @Then("the system should return to the login page")
    public void theSystemShouldReturnToTheLoginPage() {
        then(ValidationResolver.of(validationMap, IsUserOnAuthentication.class).validate()).isTrue();
    }

    @Then("the system should show the error {string}")
    public void theSystemShouldShowTheError(String errorText) {
        then(ValidationResolver.of(validationMap, IsAuthErrorMessageDisplayed.class).
                with(errorText).validate()).isTrue();
    }
}
