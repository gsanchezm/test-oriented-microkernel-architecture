package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {
    private static final Properties properties = new Properties();

    private PropertyManager() {
        // Private constructor to prevent instantiation
    }

    public static Properties loadProperties(String fileName) {
        try (InputStream input = PropertyManager.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new FrameworkException("Unable to find properties file: " + fileName);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new FrameworkException("Failed to load properties file: " + fileName, e);
        }
        return properties;
    }
}