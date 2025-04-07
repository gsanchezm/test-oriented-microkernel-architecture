package plugin;

import general.config.WebCleanUpClass;
import general.config.WebInitializeClass;
import factories.TaskFactoryHelper;
import factories.ValidationFactoryHelper;
import interfaces.platform.IPlatformProvider;
import interfaces.plugins.IPlugin;
import enums.PlatformType;
import interfaces.init.ICleanUp;
import framework.core.interfaces.IWebInitialize;
import interfaces.tasks.ITask;
import interfaces.tasks.ITaskFactory;
import interfaces.validations.IValidation;
import interfaces.validations.IValidationFactory;
import utils.BaseLogger;

import java.util.List;

public class SeleniumPlugin extends BaseLogger implements IPlugin, IPlatformProvider {

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
        return PlatformType.WEB;
    }

    @Override
    public IWebInitialize getInitializer() {
        return new WebInitializeClass();
    }

    @Override
    public ICleanUp getCleaner() {
        return new WebCleanUpClass();
    }
}