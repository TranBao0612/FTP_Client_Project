package vgu.SoSe2026_Compnet2.controller;

import vgu.SoSe2026_Compnet2.util.Connection;
import vgu.SoSe2026_Compnet2.util.ValidateSelectedFile;
import vgu.SoSe2026_Compnet2.data.FileMetadata;
import vgu.SoSe2026_Compnet2.ui.panel.LogConsole;

public class DownloadHandler implements Runnable {
    Controller controller;

    public DownloadHandler(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        // Validate that a file is selected in the user file panel before attempting to download
        FileMetadata selectedFile = ValidateSelectedFile.validate(controller.userFilePanel);
        if (selectedFile == null) {
            return;
        }
        // Disable UI if validation is successful and start the download process
        controller.inExecutingStateUI();
        new Thread(() -> {
            // Switch to binary mode before download
            if (!Command.binaryMode(controller))
                updateUIOnFailed("Failed to set binary mode for file transfer.");
            Connection dataConnection = Command.pasv(controller);
            if (dataConnection == null)
                updateUIOnFailed("Failed to establish data connection for file transfer.");
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
    
}
