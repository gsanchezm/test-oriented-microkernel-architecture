package general.config;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {

    // System root directory
    public static final Path SYSTEM_ROOT = Paths.get(System.getProperty("user.dir"));

    // Resources folders
    public static final Path RESOURCES_FOLDER = SYSTEM_ROOT.resolve("src/main/resources");

}