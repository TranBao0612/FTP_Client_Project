package vgu.SoSe2026_Compnet2.controller;

import vgu.SoSe2026_Compnet2.ui.panel.LogConsole;
import vgu.SoSe2026_Compnet2.data.FileMetadata;
import vgu.SoSe2026_Compnet2.service.Connection;
import javafx.application.Platform;
import java.util.List;

public class RefreshHandler implements Runnable {
    Controller controller;

    public RefreshHandler(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        controller.disableRequiredDataConnectionButtons();
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
                Platform.runLater(() -> {
                    controller.controlPanel.enableAll();
                });
            }
        }).start();
    }

    /**
     * 
     * @param errorMessage
     */
    private void updateUIOnSuccess(String currentDirectory, List<FileMetadata> fileList) {
        Platform.runLater(() -> {
            controller.serverFilePanel.reload(currentDirectory, fileList);
            controller.logConsole.log("Refresh successful.", LogConsole.TYPE_INFO);
        });
    } 
    /**
     * If refresh fails, only log the error message
     * @param errorMessage
     */
    private void updateUIOnFailure(String errorMessage) {
        Platform.runLater(() -> {
            controller.logConsole.log(errorMessage, LogConsole.TYPE_ERROR);
            controller.logConsole.log("Refresh failed.", LogConsole.TYPE_ERROR);
        });
    }
    
}
