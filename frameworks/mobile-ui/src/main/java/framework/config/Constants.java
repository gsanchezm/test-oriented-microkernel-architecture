package framework.config;

import java.nio.file.Path;

import static config.Constants.RESOURCES_FOLDER;

public class Constants {
    // Data folders and files
    public static final Path JSONS_FOLDER = RESOURCES_FOLDER.resolve("testdata/jsons/");
    public static final Path WORKBOOKS_FOLDER = RESOURCES_FOLDER.resolve("testdata/workbooks/");
    public static final Path QUERIES_FOLDER = RESOURCES_FOLDER.resolve("testdata/queries/");

    // Configuration file path
    public static final Path CONFIG_PATH = RESOURCES_FOLDER.resolve("config.properties");



}