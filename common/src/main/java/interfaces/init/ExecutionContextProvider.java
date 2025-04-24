package interfaces.init;

import enums.PlatformType;
import interfaces.tasks.ITask;
import interfaces.validations.IValidation;

import java.util.List;

public interface ExecutionContextProvider {
    List<ITask<?>> getTasks();
    List<IValidation<?>> getValidations();
    PlatformType getCurrentPlatform();
}
