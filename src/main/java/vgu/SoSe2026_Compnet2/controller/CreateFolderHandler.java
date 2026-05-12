package vgu.SoSe2026_Compnet2.controller;

import vgu.SoSe2026_Compnet2.util.RequestFolderName;
import vgu.SoSe2026_Compnet2.ui.panel.LogConsole;

/**
 * Handler for creating a new folder on the server.
 */
public class CreateFolderHandler implements Runnable {
    Controller controller;

    /**
     * Handle the logic when the create folder button is clicked. <br>
     * The process includes:
     *      Pop up a dialog to get the name of the new folder from the user.
     *      Send a command to the server to create the new folder in the current directory.
     * @param controller
     */
    public CreateFolderHandler(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        // Disable UI interactions while executing the command
        controller.inExecutingStateUI();
        // Get folder name from user input
        String folderName = RequestFolderName.request();
        if (folderName == null) {
            controller.readyStateUI();
            return;
        }
        new Thread(() -> {
            // Send MKD command to server to create folder
            if (Command.mkd(controller, folderName)) {
                controller.logConsole.log("Folder created successfully.", LogConsole.TYPE_INFO);
                controller.readyStateUI();
                // Refresh the server file panel to show the new folder
                new RefreshHandler(controller).run();
            } else {
                controller.logConsole.log("Failed to create folder.", LogConsole.TYPE_ERROR);
            }
            // Re-enable UI interactions after command execution
            controller.readyStateUI();
        }).start();
    }
}
