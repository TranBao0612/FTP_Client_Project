package vgu.SoSe2026_Compnet2.data;

/**
 * Represents the metadata of a file/directory, includes: type, name, size (in bytes), and last modified date.
 */
public class FileMetadata {
    public FileType type;
    public String name;
    public long sizeInByte;
    public String lastModified;

    /**
     * Constructor for FileMetadata.
     * @param type The type of the file, currently supports: file, directory, or unknown.
     * @param name The name of the file.
     * @param sizeInByte The size of the file in bytes.
     * @param lastModified The last modified time of the file.
     */
    public FileMetadata(FileType type, String name, long sizeInByte, String lastModified) {
        this.type = type;
        this.name = name;
        this.sizeInByte = sizeInByte;
        this.lastModified = lastModified;
    }

    /**
     * Retrieves file metadata from a line of Unix file description, which is the format returned by the LIST command in FTP. <br>
     * Format: [type (1 char) + permissions (9 chars)] [number of links] [ownerID] 
     *      [groupID] [size in bytes] [month] [day] [year or time] [file name] <br>
     * Example: -rw-r--r-- 1 0 0 1234 Aug 20 2004 example.txt <br>
     * Components are separated by unknown number of spaces, 
     *      and the file name can contain spaces, so we need to handle that case.
     * @param unixFileDescription Format: [type (1 char) + permissions (9 chars)] [number of links] [ownerID] [groupID] [size in bytes] [month] [day] [year or time] [file name]
     * @return metadata of the file/directory described by the input string. If the type is not a regular file or directory, it will be marked as UNKNOWN.
     */
    public static FileMetadata derivedFromUnixDescription(String unixFileDescription) {
        // Split the input string by spaces, but number of spaces is unknown.
        String[] parts = unixFileDescription.split("\\s+");
        // Type
        FileType fileType;
        switch (parts[0].charAt(0)) {
            case 'd':
                fileType = FileType.DIRECTORY;
                break;
            case '-':
                fileType = FileType.UNKNOWN;
                break;
            default:
                fileType = FileType.FILE;
        }
        // Size
        long sizeInByte = Long.parseLong(parts[4]);
        // Last Modified [month + day + year/time]
        String lastModified = String.format("%s %s %s", parts[5], parts[6], parts[7]);
        // File Name (the rest of the string after the 8th part, because file name can contain spaces)
        StringBuilder nameBuilder = new StringBuilder();
        for (int i = 8; i < parts.length; i++) {
            nameBuilder.append(parts[i]).append(" ");
        }
        String name = nameBuilder.toString().trim();
        // Return the metadata object
        return new FileMetadata(fileType, name, sizeInByte, lastModified);
    }

    /**
     * Returns a string representation of the file metadata, including type, size, last modified time, and name.
     * @return A string representation of the file metadata.
     */
    public String toString() {
        return String.format("Type: %s, Size: %d, Modified: %s, Name: %s", type, sizeInByte, lastModified, name);
    }

    /**
     * Represents the type of a Unix file, such as a regular file, directory.
     * Other types (like symbolic links, devices, etc.) will be marked as UNKNOWN and not be displayed on the file list.
     */
    public enum FileType {
        FILE,
        DIRECTORY,
        UNKNOWN
    }
}
