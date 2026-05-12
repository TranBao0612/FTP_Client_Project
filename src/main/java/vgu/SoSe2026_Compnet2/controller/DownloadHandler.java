package vgu.SoSe2026_Compnet2.controller;

import vgu.SoSe2026_Compnet2.constants.Directory;
import vgu.SoSe2026_Compnet2.util.Connection;
import vgu.SoSe2026_Compnet2.util.ValidateSelectedFile;
import vgu.SoSe2026_Compnet2.data.FileMetadata;
import vgu.SoSe2026_Compnet2.ui.panel.LogConsole;
import java.io.File;

/**
 * Handles the download process for a selected file from the server to the user's local directory.
 */
public class DownloadHandler implements Runnable {
    Controller controller;

    /**
     * Handle the logic when the download button is clicked. <br>
     * The download process includes: 
     *      enter BINARY and PASV mode, establish data connection, 
     *      download file through data connection, 
     *      and refresh the UI after download.
     * @param controller
     */
    public DownloadHandler(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        // Disable UI interactions during the download process
        controller.inExecutingStateUI();
        // Validate that a file is selected in the user file panel before attempting to download
        FileMetadata selectedFile = ValidateSelectedFile.isFile(controller.serverFilePanel);
        if (selectedFile == null) {
            controller.readyStateUI();
            return;
        }
        String serverFile = selectedFile.getName();
        new Thread(() -> {
            // Setup prerequisites
            if (!Command.typeI(controller)) {
                updateUIOnFailed("Failed to set binary mode for file transfer.");
                return;
            }
            Connection dataConnection = Command.pasv(controller);
            String userFile = Directory.generateFilePath(controller.userFilePanel.getCurrentDirectory(), serverFile);
            if (dataConnection == null) {
                updateUIOnFailed("Failed to establish data connection for file transfer.");
                return;
            }
            // download file through data connection
            if (!Command.retr(controller, dataConnection, serverFile, userFile)) {
                File deleteCorruptedFile = new File(userFile);
                if (deleteCorruptedFile.exists())
                    deleteCorruptedFile.delete();
                updateUIOnFailed("Failed to download file: " + serverFile);
            } else {
                updateUIOnSuccess();
                // Refresh after the download operation is completed
                new RefreshHandler(controller).run();
            }
        }).start();

    }


    /**
     * Update the UI and log an error message if the download process fails.
     * @param errorMessage the error message to be logged in the console
     */
    private void updateUIOnFailed(String errorMessage) {
        controller.readyStateUI();
        controller.logConsole.log(errorMessage, LogConsole.TYPE_ERROR);
    }

    /**
     * Update the UI and log a success message if the download process succeeds.
     */
    private void updateUIOnSuccess() {
        controller.readyStateUI();
        controller.logConsole.log("File downloaded successfully.", LogConsole.TYPE_INFO);
    }
    
}
