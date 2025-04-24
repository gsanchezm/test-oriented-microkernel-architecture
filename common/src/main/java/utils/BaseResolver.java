package utils;

import config.TOMException;
import enums.PlatformType;
import interfaces.init.ExecutionContextProvider;
import interfaces.platform.IPlatformSpecific;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/*
public class BaseResolver {
    private static final Logger logger = LogManager.getLogger(BaseResolver.class);

    @SuppressWarnings("unchecked")
    public static <T> T resolve(List<?> items, Class<T> targetType) {
        ExecutionContextProvider context = ExecutionContextRegistry.get();
        PlatformType currentPlatform = context.getCurrentPlatform();

        Optional<?> match = items.stream()
                .filter(item -> item != null)
                .filter(item -> targetType.getSimpleName().equals(item.getClass().getSimpleName()))
                .filter(item -> item.getClass().getPackageName().toLowerCase().contains(currentPlatform.name().toLowerCase()))
                .findFirst();

        if (match.isEmpty()) {
            logger.warn("⚠️ {} not found for platform '{}'", targetType.getSimpleName(), currentPlatform);
            return null;
        }

        try {
            return (T) match.get();
        } catch (ClassCastException e) {
            logger.error("❌ Class cast error: Expected {}, but got {}",
                    targetType.getName(), match.get().getClass().getName());
            return null;
        }
    }
}*/

public class BaseResolver {

    public static <T> T resolve(List<T> instances, Class<T> targetClass) {
        PlatformType platform = ExecutionContextRegistry.get().getCurrentPlatform();
        return instances.stream()
                .filter(targetClass::isInstance)
                .map(targetClass::cast)
                .filter(instance -> !(instance instanceof IPlatformSpecific) || ((IPlatformSpecific) instance).getPlatform() == platform)
                .findFirst()
                .orElseThrow(() -> new TOMException("❌ No matching implementation for: " + targetClass.getSimpleName() + " on platform: " + platform));
    }

    public static <T> T resolveByName(List<T> instances, String classSimpleName) {
        PlatformType platform = ExecutionContextRegistry.get().getCurrentPlatform();
        return instances.stream()
                .filter(obj -> obj.getClass().getSimpleName().equalsIgnoreCase(classSimpleName))
                .filter(obj -> !(obj instanceof IPlatformSpecific) || ((IPlatformSpecific) obj).getPlatform() == platform)
                .map(obj -> (T) obj)
                .findFirst()
                .orElseThrow(() -> new TOMException("❌ No matching implementation for: " + classSimpleName + " on platform: " + platform));
    }
}