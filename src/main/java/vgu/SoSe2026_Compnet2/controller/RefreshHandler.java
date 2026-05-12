package vgu.SoSe2026_Compnet2.controller;

import vgu.SoSe2026_Compnet2.ui.panel.LogConsole;
import vgu.SoSe2026_Compnet2.util.Connection;
import vgu.SoSe2026_Compnet2.data.FileMetadata;
import java.util.List;

/**
 * Handle the logic when the refresh button is clicked.
 */
public class RefreshHandler implements Runnable {
    Controller controller;

    /**
     * Handle the logic when the refresh button is clicked.
     * The refresh process includes: 
     *      retrieve the current working directory and file list from the server, 
     *      update the server file panel with the new file list, 
     *      and log the refresh status in the log console.
     * @param controller
     */
    public RefreshHandler(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        controller.inExecutingStateUI();
        new Thread(() -> {
            try {
                String currentDirectory = Command.pwd(controller);
                if (currentDirectory != null) {
                    // Enter passive mode to prepare for data transfer
                    Connection dataConnection = Command.pasv(controller);
                    if (dataConnection == null) {
                        updateUIOnFailure("Failed to enter passive mode.");
                        return;
                    }
                    // Retrieve file list from the server through data connection
                    List<FileMetadata> fileList = Command.list(controller, dataConnection);
                    if (fileList != null) {
                        updateUIOnSuccess(currentDirectory, fileList);
                    } else {
                        updateUIOnFailure("Failed to retrieve file list.");
                    }
                } else {
                    updateUIOnFailure("Failed to retrieve current directory.");
                }
            } catch (Exception e) {
                updateUIOnFailure("Error refreshing directory: " + e.getMessage());
            } finally {
                // Always refresh the user file panel regardless of whether retrieving current directory is successful or not.
                controller.userFilePanel.reload();
                controller.readyStateUI();
            }
        }).start();
    }

    /**
     * If refresh is successful, update the server file panel with the new file list and log the success message
     * @param currentDirectory the current working directory of the server
     * @param fileList the list of files' metadata in the current directory
     */
    private void updateUIOnSuccess(String currentDirectory, List<FileMetadata> fileList) {
        controller.serverFilePanel.reload(currentDirectory, fileList);
        controller.logConsole.log("Refresh successfully.", LogConsole.TYPE_INFO);
    } 
    /**
     * If refresh fails, only log the error message
     * @param errorMessage
     */
    private void updateUIOnFailure(String errorMessage) {
        controller.logConsole.log(errorMessage, LogConsole.TYPE_ERROR);
        controller.logConsole.log("Refresh failed.", LogConsole.TYPE_ERROR);
    }
    
}
