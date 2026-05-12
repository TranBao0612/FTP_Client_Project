package vgu.SoSe2026_Compnet2.controller;

import vgu.SoSe2026_Compnet2.util.ValidateSelectedFile;
import vgu.SoSe2026_Compnet2.data.FileMetadata;
import vgu.SoSe2026_Compnet2.ui.panel.LogConsole;

/**
 * Handles the delete operation for files and folders in the server file panel.
 */
public class DeleteHandler implements Runnable{
    Controller controller;

    /**
     * Handle the logic when the delete button is clicked. <br>
     * The delete process includes:
     *      validate the selected item,
     *      send a command to the server to delete the selected file or folder in current directory,
     *      and refresh the UI after the delete operation is completed.
     * @param controller
     */
    public DeleteHandler(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        // Disable UI interaction while executing the delete operation
        controller.inExecutingStateUI();
        // Validate that an item is selected in the server file panel before attempting to delete
        FileMetadata selectedFile = ValidateSelectedFile.hasSelection(controller.serverFilePanel);
        if (selectedFile == null) {
            controller.readyStateUI();
            return;
        }
        // Send a command to the server to delete the selected file or folder in current directory.
        FileMetadata.FileType type = selectedFile.getType();
        String name = selectedFile.getName();
        new Thread(() -> {
            if (type == FileMetadata.FileType.FILE)
                updateUI(Command.dele(controller, name), true);
            else
                updateUI(Command.rmd(controller, name), false);
            // Refresh after the delete operation is completed
            new RefreshHandler(controller).run();
        }).start();
    }

    /**
     * Update the UI based on the result of the delete operation.
     * @param isSuccess indicates whether the delete operation was successful
     * @param isFile indicates whether the deleted item is a file or a folder, used for logging purposes
     */
    private void updateUI(boolean isSuccess, boolean isFile) {
        String itemType = isFile ? "file" : "folder";
        if (isSuccess) 
            controller.logConsole.log("Deleted " + itemType + " successfully.", LogConsole.TYPE_INFO);
        else
            controller.logConsole.log("Failed to delete " + itemType + ".", LogConsole.TYPE_ERROR);
        controller.readyStateUI();
    }
}
