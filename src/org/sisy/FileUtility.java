package org.sisy;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Utility class for extracting
 * data about files and directories.
 *
 * @author Valdemar Lindberg
 */
public class FileUtility {

    /**
     * Expand path.
     *
     * @param path valid path.
     * @return non-null terminated string.
     */
    public static String expandPath(String path) {
        if (path.startsWith(path + File.separator)) {
            return System.getProperty("user.home") + path.substring(1);
        }
        return path;
    }

    /**
     * Get all subdirectories of specified directory.
     *
     * @param path directory path.
     * @return string array of directories.
     */
    public static String[] getAllSubDirectory(String path) {

        File file = new File(path);
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });

        return directories;
    }

    /**
     * Get all files in directory.
     *
     * @param directory
     * @return non-null string array.
     */
    public static String[] getAllFiles(String directory) {
        File file = new File(directory);
        String[] files = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isFile();
            }
        });

        return files;
    }
}
