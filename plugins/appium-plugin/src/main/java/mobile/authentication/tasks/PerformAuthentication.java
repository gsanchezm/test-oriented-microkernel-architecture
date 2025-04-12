package mobile.authentication.tasks;

import config.TOMException;
import framework.actions.Tap;
import framework.actions.Type;
import general.screens.HamburgerMenuScreen;
import general.screens.LoginScreen;
import interfaces.tasks.ITask;
import utils.BaseLogger;

import static framework.actions.MobileWaitUntil.retryAction;

public class PerformAuthentication extends BaseLogger implements ITask {

    @Override
    public PerformAuthentication execute(Object... args) {
        if (args.length == 0 || args[0] == null) {
            throw new TOMException("Expected user credentials data, but got none");
        }

        // Parse arguments dynamically
        String username = (String) args[0]; // First parameter is username
        String password = (String) args[1]; // Second parameter is password

        String LoginMenu = "Log In";

        // Re-create page object every time to avoid stale references

        HamburgerMenuScreen hamburgerMenuScreen = new HamburgerMenuScreen();

        // Perform login actions
        Tap.on(hamburgerMenuScreen.getHamburgerMenu());
        Tap.onElementWithText(hamburgerMenuScreen.getAllMenuItems(), LoginMenu);

        LoginScreen loginScreen = new LoginScreen();

        retryAction(() ->Type.text(loginScreen.getUsernameInput(), username), 2);
        retryAction(() ->Type.text(loginScreen.getPasswordInput(), password),2);
        Tap.on(loginScreen.getLoginButton());

        return this;
    }
}
