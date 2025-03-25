package tom.services;

import tom.plugin_manager.PluginManager;
import intarfaces.validations.IValidation;
import intarfaces.tasks.ITask;
import java.util.List;

public class TestContext {

    private final List<ITask<?>> registeredTasks;
    private final List<IValidation<?>> registeredValidations;

    public TestContext() {
        // Retrieve tasks & validations from enabled plugins
        this.registeredTasks = PluginManager.getRegisteredTasks();
        this.registeredValidations = PluginManager.getRegisteredValidations();
    }

    public List<ITask<?>> getRegisteredTasks() {
        return registeredTasks;
    }

    public List<IValidation<?>> getRegisteredValidations() {
        return registeredValidations;
    }
}
