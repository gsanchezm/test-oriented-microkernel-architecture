package services;

public interface IValidation<T extends IValidation<T>> {
    boolean validate();
}
