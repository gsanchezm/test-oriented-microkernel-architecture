package plugin;

import general.config.WebCleanUpClass;
import general.config.WebInitializeClass;
import factories.TaskFactoryHelper;
import factories.ValidationFactoryHelper;
import intarfaces.platform.IPlatformProvider;
import intarfaces.plugins.IPlugin;
import enums.PlatformType;
import intarfaces.init.ICleanUp;
import intarfaces.init.IInitialize;
import intarfaces.tasks.ITask;
import intarfaces.tasks.ITaskFactory;
import intarfaces.validations.IValidation;
import intarfaces.validations.IValidationFactory;
import utils.BaseLogger;

import java.util.List;

public class SeleniumPlugin extends BaseLogger implements IPlugin, IPlatformProvider {

    @Override
    public List<ITaskFactory<? extends ITask<?>>> getTaskFactories() {
        return TaskFactoryHelper.loadTasks(List.of(
                "authentication.tasks",
                "inventory.tasks"
        ));
    }

    @Override
    public List<IValidationFactory<? extends IValidation<?>>> getValidationFactories() {
        return ValidationFactoryHelper.loadValidations(List.of(
                "authentication.validations",
                "inventory.validations"
        ));
    }

    @Override
    public PlatformType getPlatformType() {
        return PlatformType.WEB;
    }

    @Override
    public IInitialize getInitializer() {
        return new WebInitializeClass();
    }

    @Override
    public ICleanUp getCleaner() {
        return new WebCleanUpClass();
    }
}