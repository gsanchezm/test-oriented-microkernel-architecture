package interfaces.init;

public interface IBase {
    <T extends IBase> T getInstance(Class<T> classToInstantiate);
}
