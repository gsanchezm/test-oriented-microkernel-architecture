package services.validations;

import intarfaces.validations.IValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class FluentValidationExecutor<V extends IValidation<?>> {
    private static final Logger logger = LogManager.getLogger(FluentValidationExecutor.class);
    private final V validation;
    private final List<Object> arguments = new ArrayList<>();

    public FluentValidationExecutor(V validation) {
        this.validation = validation;
    }

    public FluentValidationExecutor<V> with(Object arg) {
        arguments.add(arg);
        return this;
    }

    public boolean validate() {
        if (validation == null) {
            logger.info("⚠️  Validation was skipped due to plugin not being loaded.");
            return true;
        }
        return validation.validate(arguments.toArray());
    }
}
