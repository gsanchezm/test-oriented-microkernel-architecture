package tom.services;

import tom.plugin_manager.PluginManager;
import intarfaces.validations.IValidation;
import intarfaces.tasks.ITask;
import java.util.List;

public class TestContext {

    private final List<ITask<?>> authenticationTasks;
    private final List<IValidation<?>> authenticationValidations;

    public TestContext() {

        // Retrieve tasks & validations from enabled plugins
        this.authenticationTasks = PluginManager.getRegisteredTasks();
        this.authenticationValidations = PluginManager.getRegisteredValidations();
    }

    public List<ITask<?>> getAuthenticationTasks() {
        return authenticationTasks;
    }

    public List<IValidation<?>> getAuthenticationValidations() {
        return authenticationValidations;
    }
}
