package plugin;

import enums.PlatformType;
import interfaces.init.ICleanUp;
import interfaces.init.IInitializeBase;
import interfaces.platform.IPlatformProvider;
import interfaces.plugins.IPlugin;
import interfaces.tasks.ITask;
import interfaces.tasks.ITaskFactory;
import interfaces.validations.IValidation;
import interfaces.validations.IValidationFactory;
import utils.BaseLogger;

import java.util.List;

public class JMeterPlugin extends BaseLogger implements IPlugin, IPlatformProvider {
    @Override
    public PlatformType getPlatformType() {
        return PlatformType.PERFORMANCE;
    }

    @Override
    public IInitializeBase getInitializer() {
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
