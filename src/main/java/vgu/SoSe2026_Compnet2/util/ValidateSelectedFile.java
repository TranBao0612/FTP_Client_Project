package vgu.SoSe2026_Compnet2.util;

import vgu.SoSe2026_Compnet2.ui.panel.FilePanel;
import vgu.SoSe2026_Compnet2.data.FileMetadata;

/**
 * Utility class for validating the selected file in a FilePanel with alert dialogs when validation fails.
 */
public final class ValidateSelectedFile {
    /**
     * Validate if the selected item in the given FilePanel is a file. 
     * @param filePanel the FilePanel to validate the selected file from
     * @return null if no file is selected or if the selected item is a directory, otherwise returns the metadata of the selected file
     */
    public static FileMetadata isFile(FilePanel filePanel) {
        FileMetadata metadata = filePanel.getSelectedFile();
        if (metadata == null) {
            ErrorAlert.show("No file selected", "Please select a file.");
            return null;
        } else if (metadata.getType() == FileMetadata.FileType.DIRECTORY) {
            ErrorAlert.show("Invalid selection", "Selected item is a folder. Please select a file.");
            return null;
        }
        return metadata;
    }

    /**
     * Validate if there is a selected item in the given FilePanel.
     * @param filePanel the FilePanel to validate the selected file from
     * @return the metadata of the selected file if an item is selected, null otherwise
     */
    public static FileMetadata hasSelection(FilePanel filePanel) {
        FileMetadata metadata = filePanel.getSelectedFile();
        if (metadata == null) {
            ErrorAlert.show("No item selected", "Please select an item.");
            return null;
        }
        return metadata;
    }
}
