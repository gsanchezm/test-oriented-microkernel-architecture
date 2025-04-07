package interfaces.platform;

import enums.PlatformType;
import interfaces.init.IInitializeBase;

public interface IPlatformInitializerHandler {
    boolean supports(PlatformType platform);
    void initialize(IInitializeBase initializer, String platformVariant);
}
