package inventory.tasks;

import config.TOMException;
import general.pages.ProductsPage;
import interfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformProductsSort extends BaseLogger implements ITask {
    @Override
    public ITask execute(Object... args) {
        if (args.length == 0 || args[0] == null) {
            throw new TOMException("Expected sort option, but got none");
        }

        String sort = (String) args[0];

        ProductsPage productsPage = new ProductsPage();

        //Selection.option(productsPage.getSortContainer(), sort);

        logger.info("Sort selection is: {}", sort);

        return this;
    }
}
