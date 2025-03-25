package services.validations;

import intarfaces.validations.IValidation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ValidationResolver {
    private static final Logger logger = LogManager.getLogger(ValidationResolver.class);

    public static Map<Class<?>, IValidation<?>> toValidationMap(List<IValidation<?>> validations) {
        return validations.stream()
                .collect(Collectors.toMap(validation -> validation.getClass(), Function.identity()));
    }

    @SuppressWarnings("unchecked")
    public static <V extends IValidation<?>> V getValidation(Map<Class<?>, IValidation<?>> validationMap, Class<V> validationClass) {
        return (V) validationMap.get(validationClass);
    }

    public static <V extends IValidation<?>> FluentValidationExecutor<V> executeValidation(Map<Class<?>, IValidation<?>> validationMap, Class<V> validationClass) {
        V validation = getValidation(validationMap, validationClass);
        if (validation == null) {
            logger.warn("Validation not found: {}. It may be due to plugin deactivation.", validationClass.getSimpleName());
            return new FluentValidationExecutor<>(null);
        }
        return new FluentValidationExecutor<>(validation);
    }
}