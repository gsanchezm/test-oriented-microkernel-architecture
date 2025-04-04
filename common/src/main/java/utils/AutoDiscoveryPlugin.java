package utils;

import intarfaces.plugins.IPlugin;
import intarfaces.tasks.ITask;
import intarfaces.tasks.ITaskFactory;
import intarfaces.validations.IValidation;
import intarfaces.validations.IValidationFactory;

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
