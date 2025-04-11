package web.authentication.tasks;

import config.TOMException;

import framework.actions.Click;
import framework.actions.Enter;
import general.pages.LoginPage;
import interfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformAuthentication extends BaseLogger implements ITask {

    @Override
    public PerformAuthentication execute(Object... args) {
        if (args.length == 0 || args[0] == null) {
            throw new TOMException("Expected user credentials data, but got none");
        }

        // Parse arguments dynamically
        String username = (String) args[0]; // First parameter is username
        String password = (String) args[1]; // Second parameter is password

        // Re-create page object every time to avoid stale references
        LoginPage loginPage = new LoginPage();

        // Perform login actions
        Enter.text(loginPage.getUsernameInput(), username);
        Enter.text(loginPage.getPasswordInput(), password);
        Click.on(loginPage.getLoginButton());

        return this;
    }
}
