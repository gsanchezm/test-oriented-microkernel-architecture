package intarfaces.validations;

public interface IValidation<T extends IValidation<T>> {
    boolean validate();
}
