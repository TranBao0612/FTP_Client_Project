package vgu.SoSe2026_Compnet2.ui.panel;

import vgu.SoSe2026_Compnet2.ui.object.ControlButton;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.application.Platform;

/**
 * VBox containing the control buttons for the FTP client application.
 */
public class ControlButtonPanel extends VBox {
    public ControlButton connection;
    public ControlButton refresh;
    public ControlButton createFolder;
    public ControlButton delete;
    public ControlButton download;
    public ControlButton upload;

    /**
     * VBox containing the control buttons for the FTP client application, 
     *      including Connect, Refresh, Create Folder, Delete, Download, and Upload.
     */
    public ControlButtonPanel() {
        super(5);

        connection = new ControlButton("Connect");
        refresh = new ControlButton("Refresh");
        createFolder = new ControlButton("< Create Folder");
        delete = new ControlButton("< Delete");
        download = new ControlButton("Download >");
        upload = new ControlButton("< Upload");

        getChildren().addAll(connection, refresh, createFolder, delete, download, upload);
        setAlignment(Pos.CENTER);
    }

    /**
     * Disable all buttons except the Connect button. 
     * If text is set to "Connect", the Connect button will not be disabled.
     * @param setTextToConnect whether to set the Connect button text to "Connect" or "Disconnect"
     */
    public void disableAll(boolean setTextToConnect) {
        Platform.runLater(() -> {
            if (setTextToConnect) {
                connection.setText("Connect");
                connection.setDisable(false);
            } else {
                connection.setText("Disconnect");
                connection.setDisable(true);
            }
            refresh.setDisable(true);
            createFolder.setDisable(true);
            delete.setDisable(true);
            download.setDisable(true);
            upload.setDisable(true);
        });
    }

    /**
     * Enable all buttons. Should be called after a successful connection is established.
     */
    public void enableAll() {
        Platform.runLater(() -> {
            connection.setText("Disconnect");
            connection.setDisable(false);
            refresh.setDisable(false);
            createFolder.setDisable(false);
            delete.setDisable(false);
            download.setDisable(false);
            upload.setDisable(false);
        });
    }
}
