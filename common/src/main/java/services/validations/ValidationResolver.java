package services.validations;

import interfaces.validations.IValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.BaseResolver;
import utils.ExecutionContextRegistry;

import java.util.List;
import java.util.Optional;

public class ValidationResolver {
    private static final Logger logger = LogManager.getLogger(ValidationResolver.class);

    public static <V extends IValidation<?>> V getValidation(Class<V> validationClass) {
        @SuppressWarnings("unchecked")
        List<V> validations = (List<V>) ExecutionContextRegistry.get().getValidations();
        return BaseResolver.resolve(validations, validationClass);
    }

    public static <V extends IValidation<?>> FluentValidationExecutor<V> of(Class<V> validationClass) {
        V validation = getValidation(validationClass);
        return new FluentValidationExecutor<>(validation);
    }

    public static FluentValidationExecutor<?> of(String simpleClassName) {
        IValidation<?> validation = BaseResolver.resolveByName(ExecutionContextRegistry.get().getValidations(), simpleClassName);
        return new FluentValidationExecutor<>(validation);
    }
}