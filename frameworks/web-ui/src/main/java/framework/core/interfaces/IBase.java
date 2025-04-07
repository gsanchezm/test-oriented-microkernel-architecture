package framework.core.interfaces;

public interface IBase {
    <T extends IBase> T getInstance(Class<T> classToInstantiate);
}
