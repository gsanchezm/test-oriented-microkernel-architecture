package factories;

import config.FrameworkException;
import intarfaces.validations.IValidation;
import intarfaces.validations.IValidationFactory;

public class ValidationFactoryHelper {
    public static <T extends IValidation<T>> IValidationFactory<T> of(Class<T> validationClass) {
        return new IValidationFactory<>() {
            @Override
            public T create() {
                try {
                    return validationClass.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new FrameworkException("Failed to instantiate validation: " + validationClass.getName(), e);
                }
            }

            @Override
            public Class<T> getValidationClass() {
                return validationClass;
            }
        };
    }
}