package config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class Constants {

    // System root directory
    public static final Path SYSTEM_ROOT = Paths.get(System.getProperty("user.dir"));

    // Resources folders
    public static final Path RESOURCES_FOLDER = SYSTEM_ROOT.resolve("src/main/resources");
    public static final Path RESULTS_FOLDER = SYSTEM_ROOT.resolve("results");

    // Data folders and files
    public static final Path JSONS_FOLDER = RESOURCES_FOLDER.resolve("testdata/jsons/");
    public static final Path WORKBOOKS_FOLDER = RESOURCES_FOLDER.resolve("testdata/workbooks/");
    public static final Path QUERIES_FOLDER = RESOURCES_FOLDER.resolve("testdata/queries/");

    // Configuration file path
    public static final Path CONFIG_PATH = RESOURCES_FOLDER.resolve("config.properties");

    public static final Duration WAIT_TIMEOUT = Duration.ofSeconds(15);
}