package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.FrameworkException;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JsonDataLoader {

    private static final ObjectMapper mapper = new ObjectMapper();

    // Load from classpath with wrapper class
    public static <T> T load(String resourcePath, Class<T> clazz) {
        try (InputStream is = getClassPathStream(resourcePath)) {
            return mapper.readValue(is, clazz);
        } catch (Exception e) {
            throw new FrameworkException("Failed to load JSON from classpath: " + resourcePath, e);
        }
    }

    // Load directly to List<T> from classpath
    public static <T> List<T> loadList(String resourcePath, TypeReference<List<T>> typeRef) {
        try (InputStream is = getClassPathStream(resourcePath)) {
            return mapper.readValue(is, typeRef);
        } catch (Exception e) {
            throw new FrameworkException("Failed to load JSON list from classpath: " + resourcePath, e);
        }
    }

    // Load JSON into Map<String, List<T>>
    public static <T> Map<String, List<T>> loadMapList(String resourcePath, TypeReference<Map<String, List<T>>> typeRef) {
        try (InputStream is = getClassPathStream(resourcePath)) {
            return mapper.readValue(is, typeRef);
        } catch (Exception e) {
            throw new FrameworkException("Failed to load JSON map list from classpath: " + resourcePath, e);
        }
    }

    // Load from file system
    public static <T> T loadFromFile(String filePath, Class<T> clazz) {
        try {
            return mapper.readValue(new File(filePath), clazz);
        } catch (Exception e) {
            throw new FrameworkException("Failed to load JSON from file: " + filePath, e);
        }
    }

    // Load list from file system
    public static <T> List<T> loadListFromFile(String filePath, TypeReference<List<T>> typeRef) {
        try {
            return mapper.readValue(Files.newInputStream(Paths.get(filePath)), typeRef);
        } catch (Exception e) {
            throw new FrameworkException("Failed to load JSON list from file: " + filePath, e);
        }
    }

    // Safe load with Optional
    public static <T> Optional<T> tryLoad(String resourcePath, Class<T> clazz) {
        try {
            return Optional.of(load(resourcePath, clazz));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Shortcut for loading from /data folder in resources
    public static <T> T loadFromData(String fileName, Class<T> clazz) {
        return load("data/" + fileName, clazz);
    }

    // Helper method to get classpath resource
    private static InputStream getClassPathStream(String path) throws Exception {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        if (is == null) throw new FrameworkException("Resource not found: " + path);
        return is;
    }
}
