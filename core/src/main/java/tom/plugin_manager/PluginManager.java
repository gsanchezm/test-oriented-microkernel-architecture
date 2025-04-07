package tom.plugin_manager;

import config.TOMException;
import intarfaces.platform.IPlatformProvider;
import intarfaces.plugins.IPlugin;
import enums.PlatformType;
import intarfaces.init.ICleanUp;
import intarfaces.init.IInitialize;
import intarfaces.tasks.ITask;
import intarfaces.tasks.ITaskFactory;
import intarfaces.validations.IValidation;
import intarfaces.validations.IValidationFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static general.config.Constants.RESOURCES_FOLDER;

public class PluginManager {
    private static final Logger logger = LogManager.getLogger(PluginManager.class);
    private static final String CONFIG_FILE = RESOURCES_FOLDER + "/plugins.properties";
    private static final Map<String, Boolean> PLUGINS = new ConcurrentHashMap<>();
    private static final List<ITask<?>> TASKS = new ArrayList<>();
    private static final List<IValidation<?>> VALIDATIONS = new ArrayList<>();
    private static final Map<PlatformType, IInitialize> INITIALIZERS = new ConcurrentHashMap<>();
    private static final Map<PlatformType, ICleanUp> CLEANERS = new ConcurrentHashMap<>();
    private static boolean alreadyLoaded = false;

    /**
     * Load and execute all enabled plugins, and register their tasks & validations.
     */
    public static void loadPlugins() {
        synchronized (PluginManager.class) {
            if (alreadyLoaded) {
                logger.info("🔁 Plugins already loaded.");
                return;
            }

            logger.info("🚀 Loading plugins (thread-safe)...");
            readPluginConfig();

            PLUGINS.forEach((pluginName, enabled) -> {
                logger.info("🔍 Checking plugin: {} -> {}", pluginName, enabled);
                registerPlugin(pluginName);
            });

            alreadyLoaded = true;
        }

    }

    /**
     * Reads the plugins.properties file and stores plugin class names with their enabled status.
     */
    private static void readPluginConfig() {
        File file = new File(CONFIG_FILE);
        if (!file.exists()) {
            logger.error("⚠️ Config file not found: " + CONFIG_FILE);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split("=");
                if (parts.length != 2) {
                    logger.error("❌ Invalid config line: " + line);
                    continue;
                }

                String className = parts[0].trim();
                boolean enabled = Boolean.parseBoolean(parts[1].trim());

                PLUGINS.put(className, enabled);
                logger.info("🧩 Plugin config loaded: " + className + " = " + enabled);
            }
        } catch (IOException e) {
            throw new TOMException("Error reading config: " + CONFIG_FILE, e);
        }
    }

    /**
     * Dynamically loads a plugin class, executes it, and registers its tasks & validations.
     *
     * @param className Fully qualified class name of the plugin.
     */
    private static void registerPlugin(String className) {
        if (!Boolean.TRUE.equals(PLUGINS.get(className))) {
            logger.info("⛔ Skipping plugin (disabled): " + className);
            return;
        }

        try {
            logger.info("📦 Attempting to load class: " + className);
            Class<?> pluginClass = Class.forName(className, true, Thread.currentThread().getContextClassLoader());
            Object pluginInstance = pluginClass.getDeclaredConstructor().newInstance();

            if (pluginInstance instanceof IPlatformProvider provider) {
                PlatformType platform = provider.getPlatformType();
                INITIALIZERS.putIfAbsent(platform, provider.getInitializer());
                CLEANERS.putIfAbsent(platform, provider.getCleaner());
                logger.info("🔧 Registered initializer and cleaner for: " + platform);
            }

            if (pluginInstance instanceof IPlugin plugin) {
                plugin.getTaskFactories().forEach(ITaskFactory::registerTask);
                TASKS.addAll(plugin.getTaskFactories().stream().map(ITaskFactory::create).toList());
                VALIDATIONS.addAll(plugin.getValidationFactories().stream().map(IValidationFactory::create).toList());
                logger.info("✅ Registered tasks and validations from: " + className);
            }

        } catch (Exception e) {
            logger.error("❌ Failed to load plugin: " + className);
            e.printStackTrace();
        }
    }
    
    /**
     * Get all registered tasks from enabled plugins.
     */
    public static List<ITask<?>> getRegisteredTasks() {
        return Collections.unmodifiableList(TASKS);
    }

    /**
     * Get all registered validations from enabled plugins.
     */
    public static List<IValidation<?>> getRegisteredValidations() {
        return Collections.unmodifiableList(VALIDATIONS);
    }

    /**
     * Returns the appropriate Initializer for a platform.
     */
    public static IInitialize getInitializer(PlatformType platform) {
        if (!INITIALIZERS.containsKey(platform)) {
            logger.info("🚫 No initializer found for platform: " + platform);
        }
        return Optional.ofNullable(INITIALIZERS.get(platform))
                .orElseThrow(() -> new TOMException("No initializer found for platform: " + platform + ". Is the plugin enabled?"));
    }

    /**
     * Returns the appropriate Cleaner for a platform.
     */
    public static ICleanUp getCleaner(PlatformType platform) {
        return Optional.ofNullable(CLEANERS.get(platform))
                .orElseThrow(() -> new TOMException("No cleaner found for platform: " + platform + ". Is the plugin enabled?"));
    }

    /**
     * Returns a list of all loaded plugins.
     */
    public static Map<String, Boolean> getLoadedPlugins() {
        return Collections.unmodifiableMap(PLUGINS);
    }

    public static boolean isPlatformEnabled(PlatformType platform) {
        return INITIALIZERS.containsKey(platform) && CLEANERS.containsKey(platform);
    }
}