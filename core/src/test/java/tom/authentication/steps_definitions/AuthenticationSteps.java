package tom.authentication.steps_definitions;

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

    @When("SauceLab user submit empty credentials")
    public void iSubmitEmptyCredentials() {
        TaskResolver.of("PerformAuthentication")
                .with(EMPTY)
                .with(EMPTY)
                .execute();
    }

    @When("SauceLab user submit locked credentials")
    public void saucelabUserSubmitLockedCredentials() {
        UserInformation getUser = getLockedUser();
        user.set(new UserInformation(getUser.getUsername(), getUser.getPassword(), EMPTY, EMPTY, EMPTY));
        TaskResolver.of("PerformAuthentication")
                .with(user.get().getUsername())
                .with(user.get().getPassword())
                .execute();
    }

    @Then("the system should grant access")
    public void theSystemShouldGrantAccessOrShowAnError() {
        then(ValidationResolver.of("IsUserOnInventory").validate()).isTrue();
    }

    @When("he log out")
    public void iLogOut() {
        TaskResolver.of("PerformCloseCurrentSession").execute();
    }

    @Then("the system should return to the login page")
    public void theSystemShouldReturnToTheLoginPage() {
        then(ValidationResolver.of("IsUserOnAuthentication").validate()).isTrue();
    }

    @Then("the system should show the error {string}")
    public void theSystemShouldShowTheError(String errorText) {
        then(ValidationResolver.of("IsAuthErrorMessageDisplayed").
                with(errorText).validate()).isTrue();
    }
}
