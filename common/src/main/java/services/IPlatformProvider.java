package services;

import utils.PlatformType;

public interface IPlatformProvider {
    PlatformType getPlatformType(); // e.g., WEB, MOBILE, API
    IInitialize getInitializer();
    ICleanUp getCleaner();
}
