package web.authentication.tasks;

import general.enums.MenuItems;
import framework.actions.Click;
import framework.actions.WaitUntil;
import interfaces.tasks.ITask;
import general.pages.LeftMenuPage;
import utils.BaseLogger;

public class PerformCloseCurrentSession extends BaseLogger implements ITask {

    @Override
    public PerformCloseCurrentSession execute(Object... args) {
        LeftMenuPage leftMenuPage = new LeftMenuPage();

        WaitUntil.pageLoaded();

        Click.on(leftMenuPage.getBurguerMenu());

        WaitUntil.allElementsExist(leftMenuPage.getMenuItemList());

        leftMenuPage.getMenuItemList().stream()
                .filter(e -> e.getText().equals(MenuItems.LOGOUT.toString()))
                .findFirst()
                .ifPresent(Click::on);

        return this;
    }
}
