package vgu.SoSe2026_Compnet2.controller;

import vgu.SoSe2026_Compnet2.data.FileMetadata;
import vgu.SoSe2026_Compnet2.ui.panel.LogConsole;
import javafx.scene.input.MouseButton;
import javafx.scene.control.TableRow;

/**
 * Creaate Handler for changing directory, including changing to parent & child directory
 */
public final class ChangeDirectoryHandler {
    private ChangeDirectoryHandler() {
        // Private constructor to prevent instantiation
    }

    /**
     * Change to the parent directory.
     * @param controller instances stores all UI components
     * @return change directory to parent directory handler
     */
    public static Runnable toParent(Controller controller) {
        return () -> {
            controller.inExecutingStateUI();
            new Thread(() -> {
                boolean success = Command.cdup(controller);
                if (success) {
                    updateUIOnSuccess(controller);
                    // Refresh to update the file table and current directory label.
                    new RefreshHandler(controller).run();
                } else {
                    updateUIOnFailure(controller);
                }
            }).start();
        };
    }

    /**
     * Change to the child directory specified by the given subfolder name.
     * @param controller instances stores all UI components
     * @param child the name of the child directory to change into
     * @return change directory to child directory handler
     */
    private static Runnable cdToChild(Controller controller, String child) {
        return () -> {
            controller.inExecutingStateUI();
            new Thread(() -> {
                boolean success = Command.cwd(controller, child);
                if (success) {
                    updateUIOnSuccess(controller);
                    // Refresh to update the file table and current directory label.
                    new RefreshHandler(controller).run();
                }
                else {
                    updateUIOnFailure(controller);
                }
            }).start();
        };
    }

    /**
     * Set up the file table in the server file panel to allow 
     *      changing to child directories by double-clicking on folders and
     *      clearing selection when right-clicking on any row.
     * @param controller instances stores all UI components
     */
    public static void setCdToChild(Controller controller) {
        controller.serverFilePanel.fileTable.setRowFactory(tv -> {
            TableRow<FileMetadata> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                // Clear selection when clicking on secondary mouse button
                if (event.getButton() == MouseButton.SECONDARY) {
                    controller.serverFilePanel.fileTable.getSelectionModel().clearSelection();
                }
                // Double-click and primary mouse button to open folder
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    if (row.getItem() != null && row.getItem().getType() == FileMetadata.FileType.DIRECTORY) {
                        cdToChild(controller, row.getItem().getName()).run();
                    }
                }
            });
            return row;
        });
    }

    /**
     * Log error to reflect a failed attempt to change directory and re-eanable execution.
     * @param controller instances stores all UI components
     */
    private static void updateUIOnFailure(Controller controller) {
        controller.readyStateUI();
        controller.logConsole.log("Failed to change directory.", LogConsole.TYPE_ERROR);
    }

    /**
     * Log success to reflect a successful attempt to change directory. 
     * @param controller instances stores all UI components
     */
    private static void updateUIOnSuccess(Controller controller) {
        controller.readyStateUI();
        controller.logConsole.log("Changed directory successfully.", LogConsole.TYPE_INFO);
    }
}
