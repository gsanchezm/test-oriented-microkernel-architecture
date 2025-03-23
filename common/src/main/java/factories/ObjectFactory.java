package factories;
// Minimal DI Container to help instantiate objects after dependencies are ready

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ObjectFactory {
    private static final Map<Class<?>, Supplier<?>> registry = new HashMap<>();

    // Register a supplier (constructor or lambda)
    public static <T> void register(Class<T> clazz, Supplier<T> creator) {
        registry.put(clazz, creator);
    }

    // Get instance
    public static <T> T getInstance(Class<T> clazz) {
        Supplier<?> supplier = registry.get(clazz);
        if (supplier == null) {
            throw new IllegalStateException("No registered supplier for: " + clazz.getName());
        }
        return clazz.cast(supplier.get());
    }

    // clear registry (for test resets)
    public static void clear() {
        registry.clear();
    }
}