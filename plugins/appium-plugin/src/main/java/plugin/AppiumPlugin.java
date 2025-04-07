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
                "authentication.tasks",
                "inventory.tasks",
                "cart.tasks",
                "checkout.tasks"
        ));
    }

    @Override
    public List<IValidationFactory<? extends IValidation<?>>> getValidationFactories() {
        return ValidationFactoryHelper.loadValidations(List.of(
                "authentication.validations",
                "inventory.validations",
                "cart.validations",
                "checkout.validations"
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
