package services;

public interface IValidationFactory<T extends IValidation<T>> {
    T create();
    Class<T> getValidationClass();
}
