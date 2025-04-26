package mobile.authentication.tasks;

import config.TOMException;
import enums.PlatformType;
import framework.actions.Keyboard;
import framework.actions.MobileWaitUntil;
import framework.actions.Tap;
import framework.actions.Type;
import general.screens.HamburgerMenuScreen;
import general.screens.LoginScreen;
import interfaces.platform.IPlatformSpecific;
import interfaces.tasks.ITask;
import utils.BaseLogger;

import java.util.Map;

import static framework.actions.MobileWaitUntil.retryAction;

public class PerformAuthentication extends BaseLogger implements ITask<PerformAuthentication>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.MOBILE;
    }

    @Override
    public PerformAuthentication execute(Object... args) {
        if (args.length < 2 || args[0] == null || args[1] == null) {
            throw new TOMException("Expected user credentials data, but got none");
        }

        String username = (String) args[0];
        String password = (String) args[1];
        String platformVariant = args.length > 2 && args[2] != null
                ? args[2].toString().toLowerCase()
                : "android"; // default fallback

        // Dynamic labels
        final String LOGIN_MENU = LOGIN_LABELS.getOrDefault(platformVariant.toLowerCase(), "Log In");
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
            return proceedToLogin(username, password, platformVariant);
        }

        logger.info("➡️ User is not logged in. Navigating to login screen...");
        Tap.onElementWithText(hamburgerMenuScreen::getAllMenuItems, LOGIN_MENU);

        return proceedToLogin(username, password, platformVariant);
    }

    private static final Map<String, String> LOGIN_LABELS = Map.of(
            "ios", "Login",
            "android", "Log In"
    );

    private PerformAuthentication proceedToLogin(String username, String password, String platformVariant) {
        LoginScreen loginScreen = new LoginScreen();

        retryAction(() -> Type.text(loginScreen.getUsernameInput(), username), 2);
        retryAction(() -> Type.text(loginScreen.getPasswordInput(), password), 2);

        if ("ios".equalsIgnoreCase(platformVariant)
                && isNotBlank(username)
                && isNotBlank(password)) {
            Keyboard.hide();
        }

        Tap.on(loginScreen.getLoginButton());
        return this;
    }

    private boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }

}
