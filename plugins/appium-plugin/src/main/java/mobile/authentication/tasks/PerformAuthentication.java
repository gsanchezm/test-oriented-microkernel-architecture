package mobile.authentication.tasks;

import config.TOMException;
import framework.actions.MobileWaitUntil;
import framework.actions.Tap;
import framework.actions.Type;
import general.screens.HamburgerMenuScreen;
import general.screens.LoginScreen;
import interfaces.tasks.ITask;
import org.openqa.selenium.WebElement;
import utils.BaseLogger;

import java.time.Duration;
import java.util.function.Supplier;

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

        final String LOGIN_MENU = "Log In";
        final String LOGOUT_MENU = "Log Out";

        // Re-create page object every time to avoid stale references
        HamburgerMenuScreen hamburgerMenuScreen = new HamburgerMenuScreen();

        MobileWaitUntil.elementExists(hamburgerMenuScreen.getHamburgerMenu());
        Tap.on(hamburgerMenuScreen.getHamburgerMenu());

        boolean userIsLoggedIn = hamburgerMenuScreen.getAllMenuItems().stream()
                .anyMatch(el -> LOGOUT_MENU.equalsIgnoreCase(el.getText().trim()));

        if (userIsLoggedIn) {
            logger.info("🔐 Session detected. Logging out to clean state...");
            new PerformCloseCurrentSession().execute();
        } else {
            logger.info("➡️ User is not logged in. Navigating to login screen...");
            Tap.onElementWithText(hamburgerMenuScreen::getAllMenuItems, LOGIN_MENU);
        }

        LoginScreen loginScreen = new LoginScreen();

        retryAction(() ->Type.text(loginScreen.getUsernameInput(), username), 2);
        retryAction(() ->Type.text(loginScreen.getPasswordInput(), password),2);
        Tap.on(loginScreen.getLoginButton());

        return this;
    }
}
