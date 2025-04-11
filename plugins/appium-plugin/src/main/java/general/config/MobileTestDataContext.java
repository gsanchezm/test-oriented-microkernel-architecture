package general.config;

public class MobileTestDataContext {

    private static final ThreadLocal<String> appNameThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<String> platformThreadLocal = new ThreadLocal<>();

    public static void setAppName(String appName) {
        appNameThreadLocal.set(appName);
    }

    public static String getAppName() {
        return appNameThreadLocal.get();
    }

    public static void setPlatform(String value) {
        platformThreadLocal.set(value.toLowerCase());
    }

    public static String getPlatform() {
        return platformThreadLocal.get();
    }

    public static void clear() {
        appNameThreadLocal.remove();
        platformThreadLocal.remove();
    }
}