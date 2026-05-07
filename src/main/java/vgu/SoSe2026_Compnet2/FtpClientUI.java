package vgu.SoSe2026_Compnet2;

import vgu.SoSe2026_Compnet2.constants.UIComponent;
import vgu.SoSe2026_Compnet2.ui.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class FtpClientUI extends Application {
    // Control button panel
    private ControlButton disconnect;
    private ControlButton refresh;
    private ControlButton createFolder;
    private ControlButton delete;
    private ControlButton download;
    private ControlButton upload;

    // Log console
    private LogConsole logConsole;


    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        // Central control button panel
        initializeControlButtonPanel();
        VBox controlPanel = new VBox(5, disconnect, refresh, createFolder, delete, download, upload);
        controlPanel.setAlignment(Pos.CENTER);
        root.setCenter(controlPanel);

        // Bottom log area
        logConsole = new LogConsole();
        root.setBottom(logConsole);

        // Main Window
        Scene scene = new Scene(root, UIComponent.MAIN_WINDOW_WIDTH, UIComponent.MAIN_WINDOW_HEIGHT);
        UIComponent.applyStylesheet(scene);
        stage.setTitle("FTP Client Project");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void initializeControlButtonPanel() {
        disconnect = new ControlButton("Disconnect", () -> {});
        refresh = new ControlButton("Refresh", () -> {});
        createFolder = new ControlButton("Create Folder", () -> {});
        delete = new ControlButton("Delete", () -> {});
        download = new ControlButton("Download >", () -> {});
        upload = new ControlButton("< Upload", () -> {});
    }
}