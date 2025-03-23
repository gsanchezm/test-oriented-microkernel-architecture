package tom.services;

import tom.plugin_manager.PluginManager;
import services.IValidation;
import services.ITask;
import java.util.List;

public class TestContext {

    private final List<ITask<?>> authenticationTasks;
    private final List<IValidation<?>> loginValidations;

    public TestContext() {

        // Retrieve tasks & validations from enabled plugins
        this.authenticationTasks = PluginManager.getRegisteredTasks();
        this.loginValidations = PluginManager.getRegisteredValidations();
    }

    public List<ITask<?>> getAuthenticationTasks() {
        return authenticationTasks;
    }

    public List<IValidation<?>> getLoginValidations() {
        return loginValidations;
    }
}
