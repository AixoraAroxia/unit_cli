package com.unitTestGenerator.builders.interfaces;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface IFileManagerDelete {


  default boolean deleteFileNIO(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }

        try {
            Path path = Paths.get(filePath);
            if (!verificIsRegularFileAndExist(path)) {
                System.err.println("Path is not a regular file: " + filePath);
                return false;
            }
            return Files.deleteIfExists(path);

        } catch (SecurityException e) {
            System.err.println("Permission error when deleting: "  + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error deleting file: "  + e.getMessage());
            return false;
        }
    }

    default boolean deleteFileIO(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("File does not exist: " + filePath);
            return false;
        }
        if (!file.isFile()) {
            System.err.println("Path is not a file: " + filePath);
            return false;
        }
        return file.delete();
    }


    /***
     * Tests whether a file is a regular file with opaque content.
     * The options array may be used to indicate how symbolic links are handled for the case that the file is a symbolic link.
     * By default, symbolic links are followed and the file attribute of the final target of the link is read. If the option NOFOLLOW_LINKS is present then symbolic links are not followed.
     * Where it is required to distinguish an I/ O exception from the case that the file is not a regular file then the file attributes can be read with the readAttributes method and the file type tested with the BasicFileAttributes.
     * isRegularFile method.
     * @ Params:
     * path – the path to the file options – options indicating how symbolic links are handled
     * Returns:
     * true if the file is a regular file; false if the file does not exist, is not a regular file, or it cannot be determined if the file is a regular file or not.
     * Throws:
     * SecurityException – In the case of the default provider, and a security manager is installed, its checkRead method denies read access to the file.
     */
    default boolean verificIsRegularFileAndExist(Path path) {
        return !Files.isRegularFile(path);
    }
}
