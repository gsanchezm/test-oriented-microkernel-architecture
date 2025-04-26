package mobile.authentication.tasks;

import enums.PlatformType;
import framework.actions.MobileWaitUntil;
import framework.actions.Tap;
import general.screens.HamburgerMenuScreen;
import general.screens.LogOutScreen;
import interfaces.platform.IPlatformSpecific;
import interfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformCloseCurrentSession extends BaseLogger implements ITask<PerformCloseCurrentSession>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.MOBILE;
    }

    @Override
    public PerformCloseCurrentSession execute(Object... args) {

        String platformVariant = args.length > 0 && args[0] != null
                ? args[0].toString().toLowerCase()
                : "android"; // default fallback

        String LOGOUT_MENU = "Log Out";
        String LOGIN_MENU = "Login";

        // Re-create page object every time to avoid stale references
        HamburgerMenuScreen hamburgerMenuScreen = new HamburgerMenuScreen();

        // Perform LogOut Actions
        MobileWaitUntil.elementExists(hamburgerMenuScreen.getHamburgerMenu());
        Tap.on(hamburgerMenuScreen.getHamburgerMenu());

        if(platformVariant.equalsIgnoreCase("ios")){
            Tap.onElementWithText(hamburgerMenuScreen::getAllMenuItems, LOGIN_MENU);
            return this;
        }

        Tap.onElementWithText(hamburgerMenuScreen::getAllMenuItems, LOGOUT_MENU);

        LogOutScreen logOutScreen = new LogOutScreen();
        MobileWaitUntil.elementExists(logOutScreen.getLogOutLabel());
        Tap.on(logOutScreen.getLogOutButton());

        return this;
    }
}
