package authentication.tasks;

import config.FrameworkException;

import framework.actions.Click;
import framework.actions.Enter;
import pages.LoginPage;
import services.ITask;

public class PerformAuthentication implements ITask  {

    @Override
    public PerformAuthentication execute(Object... args) {
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
