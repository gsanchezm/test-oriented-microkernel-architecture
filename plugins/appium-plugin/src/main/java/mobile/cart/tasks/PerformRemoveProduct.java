package mobile.cart.tasks;

import config.TOMException;
import enums.PlatformType;
import general.pages.CartPage;
import interfaces.platform.IPlatformSpecific;
import interfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformRemoveProduct extends BaseLogger implements ITask<PerformRemoveProduct>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.MOBILE;
    }

    @Override
    public PerformRemoveProduct execute(Object... args) {
        if (args.length == 0 || args[0] == null) {
            throw new TOMException("Expected Item message data, but got none");
        }

        String expectedItemName = (String) args[0];

        CartPage cartPage = new CartPage();

       /* for (WebElement item : cartPage.getCartProductItems()) {
            if (item.getText().contains(expectedItemName)) {
                Click.on(item.findElement(cartPage.removeButton));
                logger.info("Item '{}' was successfully removed from the cart.", expectedItemName);
                break;
            }
        }*/

        return this;
    }
}
