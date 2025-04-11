package plugin;

import enums.PlatformType;
import factories.TaskFactoryHelper;
import factories.ValidationFactoryHelper;
import framework.core.interfaces.IMobileInitialize;
import general.config.MobileCleanUpClass;
import general.config.MobileInitializeClass;
import interfaces.init.ICleanUp;
import interfaces.platform.IPlatformProvider;
import interfaces.plugins.IPlugin;
import interfaces.tasks.ITask;
import interfaces.tasks.ITaskFactory;
import interfaces.validations.IValidation;
import interfaces.validations.IValidationFactory;
import utils.BaseLogger;

import java.util.List;

public class AppiumPlugin extends BaseLogger implements IPlugin, IPlatformProvider {
    @Override
    public List<ITaskFactory<? extends ITask<?>>> getTaskFactories() {
        return TaskFactoryHelper.loadTasks(List.of(
                "mobile.authentication.tasks"/*,
                "mobile.inventory.tasks",
                "mobile.cart.tasks",
                "mobile.checkout.tasks"*/
        ));
    }

    @Override
    public List<IValidationFactory<? extends IValidation<?>>> getValidationFactories() {
        return ValidationFactoryHelper.loadValidations(List.of(
                "mobile.authentication.validations"/*,
                "mobile.inventory.validations",
                "mobile.cart.validations",
                "mobile.checkout.validations"*/
        ));
    }

    @Override
    public PlatformType getPlatformType() {
        return PlatformType.MOBILE;
    }

    @Override
    public IMobileInitialize getInitializer() {
        return new MobileInitializeClass();
    }

    @Override
    public ICleanUp getCleaner() {
        return new MobileCleanUpClass();
    }
}
