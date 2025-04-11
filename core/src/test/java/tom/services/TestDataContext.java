package tom.services;

public class TestDataContext {
    private static final ThreadLocal<String> platformThreadLocal = new ThreadLocal<>();

    public static void setPlatform(String value) {
        platformThreadLocal.set(value.toLowerCase());
    }

    public static String getPlatform() {
        return platformThreadLocal.get();
    }

    public static void clear() {
        platformThreadLocal.remove();
    }
}
