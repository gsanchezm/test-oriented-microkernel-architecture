package services;

import java.util.List;

public interface IPlugin {

    List<ITaskFactory<? extends ITask<?>>> getTaskFactories();

    List<IValidationFactory<? extends IValidation<?>>> getValidationFactories();
    /**
     * Returns a list of tasks that the plugin provides.
     */
    //List<ITask<?>> getTasks();

    /**
     * Returns a list of validations that the plugin provides.
     */
   // List<IValidation> getValidations();
}
