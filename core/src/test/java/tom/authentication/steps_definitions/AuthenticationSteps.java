package tom.authentication.steps_definitions;

import authentication.tasks.PerformAuthentication;
import authentication.tasks.PerformUrlNavigation;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import intarfaces.validations.IValidation;
import intarfaces.tasks.ITask;
import tom.authentication.dao.UserCredentials;
import tom.authentication.runner.AuthenticationTest;
import tom.services.TestContext;
import services.tasks.TaskResolver;

import java.util.List;
import java.util.Map;

public class AuthenticationSteps {
    private final TestContext testContext;
    private final Map<Class<?>, ITask<?>> taskMap;
    private final List<IValidation<?>> loginValidations;

    public AuthenticationSteps() {
        // Get the TestContext from TestRunner (Singleton Pattern)
        this.testContext = AuthenticationTest.getTestContext();
        this.taskMap = TaskResolver.toTaskMap(testContext.getAuthenticationTasks());
        this.loginValidations = testContext.getLoginValidations();
    }

    @Given("the login page is displayed")
    public void the_login_page_is_displayed() {
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
    }

    @Then("the system should grant access")
    public void the_system_should_grant_access_or_show_an_error() {
    }

    @Then("the system should show an error")
    public void the_system_should_show_an_error() {
    }

    @Given("SauceLab user am logged in as {string}")
    public void i_am_logged_in_as(String username) {
    }

    @When("he log out")
    public void i_log_out() {
    }

    @Then("the system should return to the login page")
    public void the_system_should_return_to_the_login_page() {
    }
}
