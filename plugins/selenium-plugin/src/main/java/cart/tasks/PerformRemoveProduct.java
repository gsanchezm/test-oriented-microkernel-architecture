package cart.tasks;

import config.FrameworkException;
import framework.actions.Click;
import general.pages.CartPage;
import intarfaces.tasks.ITask;
import org.openqa.selenium.WebElement;
import utils.BaseLogger;

public class PerformRemoveProduct extends BaseLogger implements ITask {
    @Override
    public ITask execute(Object... args) {
        if (args.length == 0 || args[0] == null) {
            throw new FrameworkException("Expected Item message data, but got none");
        }

        String expectedItemName = (String) args[0];

        CartPage cartPage = new CartPage();

        for (WebElement item : cartPage.getCartProductItems()) {
            if (item.getText().contains(expectedItemName)) {
                Click.on(item.findElement(cartPage.removeButton));
                logger.info("Item '{}' was successfully removed from the cart.", expectedItemName);
                break;
            }
        }

        return this;
    }
}
