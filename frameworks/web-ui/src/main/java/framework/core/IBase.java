package framework.core;

public interface IBase {
    <T extends IBase> T getInstance(Class<T> classToInstantiate);
}
