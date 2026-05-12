package vgu.SoSe2026_Compnet2.constants;

import java.nio.file.Path;

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
     * @return A string representing the combined file path, formatted according to the operating system's conventions.
     */
    public static final String generateFilePath (String directory, String filename) {
        return Path.of(directory).resolve(filename).toString();
    }
}
