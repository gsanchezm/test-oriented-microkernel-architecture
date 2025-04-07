package interfaces.platform;

import enums.PlatformType;
import interfaces.init.ICleanUp;
import interfaces.init.IInitializeBase;

public interface IPlatformProvider {
    PlatformType getPlatformType(); // e.g., WEB, MOBILE, API
    IInitializeBase getInitializer();
    ICleanUp getCleaner();
}
