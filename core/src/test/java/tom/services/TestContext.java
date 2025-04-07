package tom.services;

import interfaces.tasks.ITask;
import interfaces.validations.IValidation;
import tom.plugin_manager.PluginManager;

import java.util.List;

public class TestContext {

    private static final ThreadLocal<TestContext> threadLocalContext =
            ThreadLocal.withInitial(TestContext::new);

    public static TestContext get() {
        return threadLocalContext.get();
    }

    public static void clear() {
        threadLocalContext.remove();
    }

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