package general.config;

import config.PropertyManager;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Constants {

    // System root directory
    public static final Path SYSTEM_ROOT = Paths.get(System.getProperty("user.dir"));

    // Resources folders
    public static final Path RESOURCES_FOLDER = SYSTEM_ROOT.resolve("src/main/resources");

    public static final Properties APPIUM_CONFIG = PropertyManager.loadProperties( "appium-config.properties");

    public static final String APP_PATH = "plugins/appium-plugin/src/main/resources/app/";

    public static final String CAPABILITIES_PATH = "capabilities/";

}