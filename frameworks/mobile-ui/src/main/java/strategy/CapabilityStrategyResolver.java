package strategy;

import config.TOMException;
import framework.core.interfaces.PlatformCapabilityStrategy;

public class CapabilityStrategyResolver {
    public static PlatformCapabilityStrategy resolve(String platformName) {
        return switch (platformName.toLowerCase()) {
            case "android" -> new AndroidCapabilityStrategy();
            case "ios" -> new IOSCapabilityStrategy();
            default -> throw new TOMException("❌ Unsupported platformName: " + platformName);
        };
    }
}
