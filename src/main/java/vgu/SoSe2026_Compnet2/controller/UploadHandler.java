package vgu.SoSe2026_Compnet2.controller;

import vgu.SoSe2026_Compnet2.constants.Directory;
import vgu.SoSe2026_Compnet2.util.Connection;
import vgu.SoSe2026_Compnet2.util.ValidateSelectedFile;
import vgu.SoSe2026_Compnet2.data.FileMetadata;
import vgu.SoSe2026_Compnet2.ui.panel.LogConsole;
import java.io.File;

/**
 * Handles the upload process for a selected file from the user's local directory to the server.
 */
public class UploadHandler implements Runnable {
    Controller controller;

    /**
     * Handle the logic when the upload button is clicked. <br>
     * The upload process includes: 
     *      enter BINARY and PASV mode, establish data connection, 
     *      upload file through data connection, 
     *      and refresh the UI after upload.
     * @param controller
     */
    public UploadHandler(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        // Disable UI interactions during the upload process
        controller.inExecutingStateUI();
        // Validate that a file is selected in the user file panel before attempting to upload
        FileMetadata selectedFile = ValidateSelectedFile.validate(controller.userFilePanel);
        if (selectedFile == null) {
            controller.readyStateUI();
            return;
        }
        String userFilePath = controller.userFilePanel.getAbsolutePath(selectedFile);
        new Thread(() -> {
            // Setup prerequisites
            if (!Command.binaryMode(controller))
                updateUIOnFailed("Failed to set binary mode for file transfer.");
            Connection dataConnection = Command.pasv(controller);
            if (dataConnection == null)
                updateUIOnFailed("Failed to establish data connection for file transfer.");
            // Upload file through data connection
            if (!Command.stor(controller, dataConnection, selectedFile.getName(), userFilePath)) {
                updateUIOnFailed("Failed to upload file.");
            } else {
                updateUIOnSuccess();
                new RefreshHandler(controller).run();
            }
        }).start();

    }


    /**
     * Update the UI and log an error message if the download process fails.
     * @param errorMessage the error message to be logged in the console
     */
    private void updateUIOnFailed(String errorMessage) {
        controller.logConsole.log(errorMessage, LogConsole.TYPE_ERROR);
        controller.readyStateUI();
    }

    /**
    * Update the UI and log a success message if the download process succeeds.
    */
    private void updateUIOnSuccess() {
        controller.logConsole.log("File uploaded successfully.", LogConsole.TYPE_INFO);
        controller.readyStateUI();
    }
    
}
