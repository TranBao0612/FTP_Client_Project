package vgu.SoSe2026_Compnet2.util;

import javafx.stage.DirectoryChooser;
import javafx.scene.Node;

/**
 * Utility class for picking a directory using a JavaFX DirectoryChooser dialog.
 */
public final class DirectoryPicker {
    private DirectoryPicker() {}

    /**
     * Displays a directory picker dialog and returns the absolute path of the selected directory.
     * @param callingNode A JavaFX Node to retrieve owner window for the dialog.
     * @return The absolute path of the selected directory, or null if no directory was selected.
     */
    public static String pick(Node callingNode) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");
        java.io.File selectedDirectory = directoryChooser.showDialog(callingNode.getScene().getWindow());
        if (selectedDirectory != null) {
            return selectedDirectory.getAbsolutePath();
        } else {
            return null;
        }
    }
}
