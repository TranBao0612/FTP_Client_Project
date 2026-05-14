package vgu.SoSe2026_Compnet2.constants;

import java.nio.file.Path;
import java.io.File;

/**
 * Defines constant values related to directory paths and file management.
 */
public final class Directory {
    public static final String STYLE_CSS_PATH = "/css/style.css";
    public static final String DEFAULT_USER_FILE_PANEL_DIR = System.getProperty("user.home");

    /**
     * Generates a native file path by combining a directory and a filename.
     * @param directory The directory path to which the filename should be appended.
     * @param filename The name of the file to be appended to the directory path.
     * @param isDownloadOperation If true, add a counter to the filename if a file with the generated path already exists on local file system
     * @return A string representing the combined file path, formatted according to the operating system's conventions.
     */
    public static final String generateFilePath (String directory, String filename, boolean isDownloadOperation) {
        String filePath = Path.of(directory).resolve(filename).toString();
        File file = new File(filePath);
        if (!isDownloadOperation || !file.exists())
            return filePath;
        int counter = 1;
        String originalFilePath = filePath;
        while (file.exists()) {
            filePath = generateFilePathWithCounter(originalFilePath, counter);
            file = new File(filePath);
            counter++;
        }
        return filePath;
    }

    /**
     * Generates a file path by combining a directory and a filename.
     * @param directory The directory path to which the filename should be appended.
     * @param filename The name of the file to be appended to the directory path.
     * @return A string representing the combined file path, formatted according to the operating system's conventions.
     */
    public static final String generateFilePath (String directory, String filename) {
        return generateFilePath(directory, filename, false);
    }

    /**
     * Generates a new file path by appending a counter in parentheses to the original file path. <br>
     * If the original file path has an extension, the counter will be appended before the extension.
     * @param filePath The original file path to which the counter should be appended.
     * @param counter The counter value to be appended to the file path.
     * @return The new file path with the counter appended.
     */
    private static final String generateFilePathWithCounter(String filePath, int counter) {
        if(!haveExtension(filePath))
            return filePath + " (" + counter + ")";
        String nameWithoutExtension = filePath.substring(0, filePath.lastIndexOf('.'));
        String extension = filePath.substring(filePath.lastIndexOf('.'));
        return nameWithoutExtension + " (" + counter + ")" + extension;
    }

    /**
     * Checks if the given filename has an extension. <br>
     * If the filename contains a dot (.) that is not at the beginning or end of the filename, 
     *     it is considered to have an extension.
     * @param filename The name of the file to check for an extension.
     * @return true if the filename has an extension, false otherwise.
     */
    private static final boolean haveExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        return lastDotIndex != -1 && lastDotIndex != filename.length() - 1 && lastDotIndex != 0;
    }
}
