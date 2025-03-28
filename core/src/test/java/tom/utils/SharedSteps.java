package tom.utils;

import authentication.tasks.PerformAuthentication;
import intarfaces.tasks.ITask;
import intarfaces.validations.IValidation;
import services.tasks.TaskResolver;
import services.validations.ValidationResolver;
import tom.authentication.dao.UserCredentials;
import tom.inventory.dao.ProductInfo;
import tom.services.TestContext;

import java.util.Map;

public class SharedSteps {
    protected final TestContext testContext;
    protected final Map<Class<?>, ITask<?>> taskMap;
    protected final Map<Class<?>, IValidation<?>> validationMap;
    protected UserCredentials user;
    protected ProductInfo productInfo;

    public SharedSteps(TestContext testContext) {
        this.testContext = testContext;
        this.taskMap = TaskResolver.toTaskMap(testContext.getRegisteredTasks());
        this.validationMap = ValidationResolver.toValidationMap(testContext.getRegisteredValidations());
    }

    public void authenticatedUser(String username, String password){
        user = new UserCredentials(username, password);
        TaskResolver.of(taskMap, PerformAuthentication.class)
                .with(user.getUsername())
                .with(user.getPassword())
                .execute();
    }
}