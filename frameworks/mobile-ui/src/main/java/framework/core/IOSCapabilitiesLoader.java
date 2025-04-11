package framework.core;

import config.TOMException;
import framework.core.interfaces.ICapabilitiesLoader;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IOSCapabilitiesLoader implements ICapabilitiesLoader {
    @Override
    public List<String> loadAllCapabilityFiles(String os) {
        try {
            // Should resolve to: "capabilities/ios"
            String capabilityFolderPath = String.format("capabilities/%s", os);
            URL folderUrl = getClass().getClassLoader().getResource(capabilityFolderPath);

            if (folderUrl == null) {
                throw new TOMException("📁 iOS capabilities folder not found in classpath: " + capabilityFolderPath);
            }

            File folder = new File(folderUrl.toURI());
            if (!folder.exists() || !folder.isDirectory()) {
                throw new TOMException("❌ Path is not a valid folder: " + folder.getAbsolutePath());
            }

            File[] jsonFiles = folder.listFiles((dir, name) -> name.endsWith(".json"));
            if (jsonFiles == null || jsonFiles.length == 0) {
                throw new TOMException("⚠️ No JSON capability files found in: " + folder.getAbsolutePath());
            }

            List<String> capabilityPaths = new ArrayList<>();
            for (File file : jsonFiles) {
                String classpathLocation = String.format("capabilities/%s/%s", os, file.getName());
                capabilityPaths.add(classpathLocation);
            }

            return capabilityPaths;

        } catch (Exception e) {
            throw new TOMException("🚨 Error loading iOS capabilities from resources", e);
        }
    }
}
