package mobile.authentication.tasks;

import interfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformResetAppState extends BaseLogger implements ITask {
    @Override
    public ITask execute(Object... args) {
        /*LeftMenuPage leftMenuPage = new LeftMenuPage();
        Click.on(leftMenuPage.getBurguerMenu());
        WaitUntil.allElementsExist(leftMenuPage.getMenuItemList());

        leftMenuPage.getMenuItemList().stream()
                .filter(e -> e.getText().equals(MenuItems.RESET_APP_STATE.toString()))
                .findFirst()
                .ifPresent(Click::on);
        Refresh.page();*/
        return this;
    }
}
