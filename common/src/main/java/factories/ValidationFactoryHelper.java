package factories;

import services.IValidation;
import services.IValidationFactory;

public class ValidationFactoryHelper {
    public static <T extends IValidation<T>> IValidationFactory<T> of(Class<T> validationClass) {
        return new IValidationFactory<>() {
            @Override
            public T create() {
                try {
                    return validationClass.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("Failed to instantiate validation: " + validationClass.getName(), e);
                }
            }

            @Override
            public Class<T> getValidationClass() {
                return validationClass;
            }
        };
    }
}