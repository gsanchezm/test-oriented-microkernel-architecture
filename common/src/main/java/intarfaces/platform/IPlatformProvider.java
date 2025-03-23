package intarfaces.platform;

import enums.PlatformType;
import intarfaces.init.ICleanUp;
import intarfaces.init.IInitialize;

public interface IPlatformProvider {
    PlatformType getPlatformType(); // e.g., WEB, MOBILE, API
    IInitialize getInitializer();
    ICleanUp getCleaner();
}
