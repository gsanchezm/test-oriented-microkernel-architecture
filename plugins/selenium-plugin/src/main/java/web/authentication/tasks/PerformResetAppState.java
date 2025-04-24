package web.authentication.tasks;

import enums.PlatformType;
import framework.actions.Click;
import framework.actions.Refresh;
import framework.actions.WaitUntil;
import general.enums.MenuItems;
import general.pages.LeftMenuPage;
import interfaces.platform.IPlatformSpecific;
import interfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformResetAppState extends BaseLogger implements ITask<PerformResetAppState>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.WEB;
    }

    @Override
    public PerformResetAppState execute(Object... args) {
        LeftMenuPage leftMenuPage = new LeftMenuPage();
        Click.on(leftMenuPage.getBurguerMenu());
        WaitUntil.allElementsExist(leftMenuPage.getMenuItemList());

        leftMenuPage.getMenuItemList().stream()
                .filter(e -> e.getText().equals(MenuItems.RESET_APP_STATE.toString()))
                .findFirst()
                .ifPresent(Click::on);
        Refresh.page();
        return this;
    }
}
