package interfaces.validations;

public interface IValidation<T extends IValidation<T>> {
    boolean validate(Object... args);
}
