package factories;

import config.FrameworkException;
import intarfaces.validations.IValidation;
import intarfaces.validations.IValidationFactory;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ValidationFactoryHelper {
    public static <V extends IValidation<V>> IValidationFactory<V> of(Class<V> validationClass) {
        return new IValidationFactory<V>() {
            @Override
            public V create() {
                try {
                    return validationClass.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("Failed to create validation instance: " + validationClass.getSimpleName(), e);
                }
            }

            @Override
            public Class<V> getValidationClass() {
                return validationClass;
            }
        };
    }

    public static List<IValidationFactory<? extends IValidation<?>>> loadValidations(List<String> basePackages) {
        List<IValidationFactory<? extends IValidation<?>>> factories = new ArrayList<>();

        for (String basePackage : basePackages) {
            Reflections reflections = new Reflections(basePackage);
            Set<Class<? extends IValidation<?>>> validationClasses =
                    reflections.getSubTypesOf((Class<IValidation<?>>)(Class<?>) IValidation.class);

            for (Class<? extends IValidation<?>> cls : validationClasses) {
                if (!cls.isInterface() && !Modifier.isAbstract(cls.getModifiers())) {
                    factories.add(of((Class) cls));
                }
            }
        }
        return factories;
    }

}