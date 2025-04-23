package mobile.authentication.tasks;

import framework.actions.MobileWaitUntil;
import framework.actions.Tap;
import general.screens.HamburgerMenuScreen;
import general.screens.LogOutScreen;
import interfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformCloseCurrentSession extends BaseLogger implements ITask {

    @Override
    public PerformCloseCurrentSession execute(Object... args) {
        String LogOutMenu = "Log Out";

        // Re-create page object every time to avoid stale references
        HamburgerMenuScreen hamburgerMenuScreen = new HamburgerMenuScreen();

        // Perform LogOut Actions
        MobileWaitUntil.elementExists(hamburgerMenuScreen.getHamburgerMenu());
        Tap.on(hamburgerMenuScreen.getHamburgerMenu());
        Tap.onElementWithText(hamburgerMenuScreen::getAllMenuItems, LogOutMenu);

        LogOutScreen logOutScreen = new LogOutScreen();
        MobileWaitUntil.elementExists(logOutScreen.getLogOutLabel());
        Tap.on(logOutScreen.getLogOutButton());

        return this;
    }
}
