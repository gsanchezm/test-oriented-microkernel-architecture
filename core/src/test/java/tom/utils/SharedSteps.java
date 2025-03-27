package tom.utils;

import intarfaces.tasks.ITask;
import intarfaces.validations.IValidation;
import services.tasks.TaskResolver;
import services.validations.ValidationResolver;
import tom.services.TestContext;

import java.util.Map;

public class SharedSteps {
    protected final TestContext testContext;
    protected final Map<Class<?>, ITask<?>> taskMap;
    protected final Map<Class<?>, IValidation<?>> validationMap;

    public SharedSteps(TestContext testContext) {
        this.testContext = testContext;
        this.taskMap = TaskResolver.toTaskMap(testContext.getRegisteredTasks());
        this.validationMap = ValidationResolver.toValidationMap(testContext.getRegisteredValidations());
    }
}