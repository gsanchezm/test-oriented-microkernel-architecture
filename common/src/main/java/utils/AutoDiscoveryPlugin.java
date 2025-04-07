package utils;

import interfaces.plugins.IPlugin;
import interfaces.tasks.ITask;
import interfaces.tasks.ITaskFactory;
import interfaces.validations.IValidation;
import interfaces.validations.IValidationFactory;

import java.util.Collections;
import java.util.List;

import factories.TaskFactoryHelper;
import factories.ValidationFactoryHelper;

public abstract class AutoDiscoveryPlugin extends BaseLogger implements IPlugin {

    /**
     * This should be overridden to define the base package for the plugin (e.g. "authentication")
     */
    protected abstract String getBasePackage();

    @Override
    public List<ITaskFactory<? extends ITask<?>>> getTaskFactories() {
        return TaskFactoryHelper.loadTasks(Collections.singletonList(getBasePackage() + ".tasks"));
    }

    @Override
    public List<IValidationFactory<? extends IValidation<?>>> getValidationFactories() {
        return ValidationFactoryHelper.loadValidations(Collections.singletonList(getBasePackage() + ".validations"));
    }
}
