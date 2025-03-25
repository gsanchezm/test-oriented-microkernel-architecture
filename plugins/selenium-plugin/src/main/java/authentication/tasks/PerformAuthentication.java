package authentication.tasks;

import config.FrameworkException;

import framework.actions.Click;
import framework.actions.Enter;
import general.pages.LoginPage;
import intarfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformAuthentication extends BaseLogger implements ITask {

    @Override
    public PerformAuthentication execute(Object... args) {
        if (args.length == 0 || args[0] == null) {
            throw new FrameworkException("Expected user credentials data, but got none");
        }

        String username = null;
        String password = null;

        // Parse arguments dynamically
        if (args.length > 0) username = (String) args[0]; // First parameter is username
        if (args.length > 1) password = (String) args[1]; // Second parameter is password

        // Validate arguments
        if (username == null || password == null) {
            throw new FrameworkException("Username and Password are required!");
        }

        // Re-create page object every time to avoid stale references
        LoginPage loginPage = new LoginPage();

        // Perform login actions
        Enter.text(loginPage.getUsernameInput(), username);
        Enter.text(loginPage.getPasswordInput(), password);
        Click.on(loginPage.getLoginButton());

        return this;
    }
}
