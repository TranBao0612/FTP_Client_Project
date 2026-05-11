package vgu.SoSe2026_Compnet2.ui.panel;

import vgu.SoSe2026_Compnet2.ui.object.ControlButton;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.application.Platform;

public class ControlButtonPanel extends VBox {
    public ControlButton connection;
    public ControlButton refresh;
    public ControlButton createFolder;
    public ControlButton delete;
    public ControlButton download;
    public ControlButton upload;

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
        disableAllExceptConnect();
    }

    /**
     * Disable all buttons except the Connect button. Should be called when there is no connection.
     */
    public void disableAllExceptConnect() {
        Platform.runLater(() -> {
            connection.setText("Connect");
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
            refresh.setDisable(false);
            createFolder.setDisable(false);
            delete.setDisable(false);
            download.setDisable(false);
            upload.setDisable(false);
        });
    }
}
