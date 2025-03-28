package inventory.tasks;

import config.FrameworkException;
import framework.actions.Selection;
import general.pages.ProductsPage;
import intarfaces.tasks.ITask;
import utils.BaseLogger;

public class PerformProductsSort extends BaseLogger implements ITask {
    @Override
    public ITask execute(Object... args) {
        if (args.length == 0 || args[0] == null) {
            throw new FrameworkException("Expected sort option, but got none");
        }

        String sort = (String) args[0];

        ProductsPage productsPage = new ProductsPage();

        Selection.option(productsPage.getSortContainer(), sort);

        logger.info("Sort selection is: {}", sort);

        return this;
    }
}
