package framework.core.interfaces;

import framework.core.AppiumServer;
import interfaces.init.IInitializeBase;

public interface IMobileInitialize extends IInitializeBase {
    void initialize(String appNameKey, String deviceKey, AppiumServer server);
}
