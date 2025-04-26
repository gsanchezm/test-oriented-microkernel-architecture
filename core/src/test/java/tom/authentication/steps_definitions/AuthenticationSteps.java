package tom.authentication.steps_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import services.validations.ValidationResolver;
import tom.authentication.dao.UserInformation;
import tom.services.TestContext;
import services.tasks.TaskResolver;
import tom.services.TestDataContext;
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
                .with(TestDataContext.getPlatformVariant())
                .execute();
    }

    @When("SauceLab user submit empty credentials")
    public void iSubmitEmptyCredentials() {
        TaskResolver.of("PerformAuthentication")
                .with(EMPTY)
                .with(EMPTY)
                .with(TestDataContext.getPlatformVariant())
                .execute();
    }

    @When("SauceLab user submit locked credentials")
    public void saucelabUserSubmitLockedCredentials() {
        UserInformation getUser = getLockedUser();
        user.set(new UserInformation(getUser.getUsername(), getUser.getPassword(), EMPTY, EMPTY, EMPTY));
        TaskResolver.of("PerformAuthentication")
                .with(user.get().getUsername())
                .with(user.get().getPassword())
                .with(TestDataContext.getPlatformVariant())
                .execute();
    }

    @Then("the system should grant access")
    public void theSystemShouldGrantAccess() {
        then(ValidationResolver.of("IsUserOnInventory").validate()).isTrue();
    }

    @When("he log out")
    public void iLogOut() {
        TaskResolver.of("PerformCloseCurrentSession")
                .with(TestDataContext.getPlatformVariant())
                .execute();
    }

    @Then("the system should return to the login page")
    public void theSystemShouldReturnToTheLoginPage() {
        then(ValidationResolver.of("IsUserOnAuthentication").validate()).isTrue();
    }

    @Then("the system should show the error {string}")
    public void theSystemShouldShowTheError(String errorText) {
        if (TestDataContext.getPlatformVariant().equalsIgnoreCase("ios")
                && errorText.contains("locked out")) {return;}
            then(ValidationResolver.of("IsAuthErrorMessageDisplayed").
                    with(errorText)
                    .with(TestDataContext.getPlatformVariant())
                    .validate())
                    .isTrue();
    }
}
