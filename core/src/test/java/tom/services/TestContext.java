package tom.services;

import enums.PlatformType;
import interfaces.init.ExecutionContextProvider;
import interfaces.tasks.ITask;
import interfaces.validations.IValidation;
import tom.plugin_manager.PluginManager;

import java.util.List;

public class TestContext implements ExecutionContextProvider {

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

    @Override
    public List<ITask<?>> getTasks() {
        return TestContext.get().getRegisteredTasks();
    }

    @Override
    public List<IValidation<?>> getValidations() {
        return TestContext.get().getRegisteredValidations();
    }

    @Override
    public PlatformType getCurrentPlatform() {
        String platform = TestDataContext.getPlatform();
        if (platform == null) {
            throw new IllegalStateException("Platform is not set in TestDataContext.");
        }
        return PlatformType.valueOf(platform.toUpperCase());
    }
}