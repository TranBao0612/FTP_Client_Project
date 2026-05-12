package vgu.SoSe2026_Compnet2.util;

import vgu.SoSe2026_Compnet2.ui.panel.FilePanel;
import vgu.SoSe2026_Compnet2.data.FileMetadata;
import javafx.scene.control.Alert;

/**
 * Utility class for validating the selected file in a FilePanel.
 */
public class ValidateSelectedFile {
    /**
     * Validate the selected file in the given FilePanel. 
     * @param filePanel the FilePanel to validate the selected file from
     * @return null if no file is selected or if the selected item is a directory, otherwise returns the metadata of the selected file
     */
    public static FileMetadata validate(FilePanel filePanel) {
        FileMetadata metadata = filePanel.getSelectedFile();
        if (metadata == null) {
            createAlert("Please select a file.");
            return null;
        } else if (metadata.getType() == FileMetadata.FileType.DIRECTORY) {
            createAlert("Selected item is a folder. Please select a file.");
            return null;
        }
        return metadata;

    }

    /**
     * Create and show an alert with the given message.
     * @param message the message to display in the alert
     * @return the created alert
     */
    private static Alert createAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("File Selection Error");
        alert.setContentText(message);
        alert.showAndWait();
        return alert;
    }
}
