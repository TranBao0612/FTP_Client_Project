package vgu.SoSe2026_Compnet2.util;

import javafx.scene.control.TextInputDialog;

/**
 * Utility class for displaying a dialog to request a folder name from the user 
 *      when creating a new folder on the FTP server.
 */
public final class RequestFolderName extends TextInputDialog {
    /**
     * Displays a dialog to request a folder name from the user.
     * @return The folder name entered by the user, or null if the dialog was cancelled.
     */
    public static String request() {
        RequestFolderName dialog = new RequestFolderName();
        return dialog.showAndWait().orElse(null);
    }

    /**
     * Custom input dialog to request folder name.
     */
    private RequestFolderName() {
        setTitle("FTP Service - Create Remote Folder");
        setHeaderText("Enter the name of the new folder: ");
        setContentText("Folder Name: ");
    }
}
