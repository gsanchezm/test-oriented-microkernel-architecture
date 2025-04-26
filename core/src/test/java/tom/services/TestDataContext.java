package tom.services;

public class TestDataContext {
    private static final ThreadLocal<String> platformThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<String> platformVariantThreadLocal = new ThreadLocal<>();

    public static void setPlatform(String value) {
        platformThreadLocal.set(value.toLowerCase());
    }

    public static String getPlatform() {
        return platformThreadLocal.get();
    }

    public static void clearPlatform() {
        platformThreadLocal.remove();
    }

    public static void setPlatformVariant(String value) {
        platformVariantThreadLocal.set(value.toLowerCase());
    }

    public static String getPlatformVariant() {
        return platformVariantThreadLocal.get();
    }

    public static void clearPlatformVariant() {
        platformVariantThreadLocal.remove();
    }
}
