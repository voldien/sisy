package org.sisy;

import java.io.File;

/**
 * Utility class for extracting
 * data about files and directories.
 *
 * @author Valdemar Lindberg
 */
public class FileUtility {

    /**
     * Get file size in bytes.
     *
     * @param path file path.
     * @return size in bytes.
     */
    public static long getFileSize(String path) {
        File file = new File(path);
        return file.length();
    }

    /**
     * Expand path when using special character.
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
        return new File(path).list((current, name) -> {
            File f = new File(current, name);
            return f.isDirectory();
        });
    }

    /**
     * Check if directory path is a directory.
     *
     * @param directory directory path.
     * @return true if directory, false otherwise.
     */
    public static boolean isDirectory(String directory) {
        File file = new File(directory);
        return file.isDirectory();
    }

    /**
     * Get all files in directory.
     *
     * @param directory
     * @return non-null string array.
     */
    public static String[] getAllFiles(String directory){
        return new File(directory).list((current, name) -> {
            File f = new File(current, name);
            return f.isFile();
        });
    }
}
