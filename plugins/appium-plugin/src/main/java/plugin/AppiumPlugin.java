package plugin;

import enums.PlatformType;
import intarfaces.init.ICleanUp;
import intarfaces.init.IInitialize;
import intarfaces.platform.IPlatformProvider;
import intarfaces.plugins.IPlugin;
import intarfaces.tasks.ITask;
import intarfaces.tasks.ITaskFactory;
import intarfaces.validations.IValidation;
import intarfaces.validations.IValidationFactory;
import utils.BaseLogger;

import java.util.List;

public class AppiumPlugin extends BaseLogger implements IPlugin, IPlatformProvider {
    @Override
    public PlatformType getPlatformType() {
        return PlatformType.MOBILE;
    }

    @Override
    public IInitialize getInitializer() {
        return null;
    }

    @Override
    public ICleanUp getCleaner() {
        return null;
    }

    @Override
    public List<ITaskFactory<? extends ITask<?>>> getTaskFactories() {
        return List.of();
    }

    @Override
    public List<IValidationFactory<? extends IValidation<?>>> getValidationFactories() {
        return List.of();
    }
}
