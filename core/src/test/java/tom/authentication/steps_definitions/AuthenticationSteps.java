package tom.authentication.steps_definitions;

import authentication.tasks.PerformAuthentication;
import authentication.tasks.PerformNavigation;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import services.IValidation;
import services.ITask;
import tom.authentication.dao.UserCredentials;
import tom.authentication.runner.AuthenticationTest;
import tom.services.TestContext;
import tom.utils.TaskUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AuthenticationSteps {
    private final TestContext testContext;
    private final Map<Class<?>, ITask<?>> taskMap;
    private final List<IValidation<?>> loginValidations;

    public AuthenticationSteps() {
        // Get the TestContext from TestRunner (Singleton Pattern)
        this.testContext = AuthenticationTest.getTestContext();
        this.taskMap = TaskUtils.toTaskMap(testContext.getAuthenticationTasks());
        this.loginValidations = testContext.getLoginValidations();
    }

    @Step("User navigates to login page")
    @Given("the login page is displayed")
    public void the_login_page_is_displayed() {
        TaskUtils.getTask(taskMap, PerformNavigation.class).execute();
    }

    @Step("SauceLab user submit credentials {0} and {1}")
    @When("SauceLab user submit credentials {string} and {string}")
    public void i_submit_credentials(String username, String password) {
        UserCredentials user = new UserCredentials(username, password);
        TaskUtils.getTask(taskMap, PerformAuthentication.class).execute(user.getUsername(), user.getPassword());
    }

    @Step("SauceLab user submit empty credentials")
    @When("SauceLab user submit empty credentials")
    public void i_submit_empty_credentials() {
    }

    @Step("The system should grant access")
    @Then("the system should grant access")
    public void the_system_should_grant_access_or_show_an_error() {
    }

    @Step("The system should show an error")
    @Then("the system should show an error")
    public void the_system_should_show_an_error() {
    }

    @Step("SauceLab user am logged in as {0}")
    @Given("SauceLab user am logged in as {string}")
    public void i_am_logged_in_as(String username) {
    }

    @Step("He log out")
    @When("he log out")
    public void i_log_out() {
    }

    @Step("The system should return to the login page")
    @Then("the system should return to the login page")
    public void the_system_should_return_to_the_login_page() {
    }
}
