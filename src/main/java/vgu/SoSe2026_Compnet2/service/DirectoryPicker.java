package vgu.SoSe2026_Compnet2.service;

import javafx.stage.DirectoryChooser;
import javafx.scene.Node;

public final class DirectoryPicker {
    private DirectoryPicker() {}

    
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
