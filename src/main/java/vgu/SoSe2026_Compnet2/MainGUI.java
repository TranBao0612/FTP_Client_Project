package vgu.SoSe2026_Compnet2;

import vgu.SoSe2026_Compnet2.constants.UIMetrics;
import vgu.SoSe2026_Compnet2.ui.panel.*;
import vgu.SoSe2026_Compnet2.util.*;
import vgu.SoSe2026_Compnet2.ui.object.ConnectionInfoLabel;
import vgu.SoSe2026_Compnet2.controller.Controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainGUI extends Application {
    // Connection + Host info
    Connection connection = null;
    ConnectionInfoLabel connectionInfoLabel = new ConnectionInfoLabel();

    // Panels
    private ControlButtonPanel controlPanel = new ControlButtonPanel();
    private UserFilePanel userFilePanel = new UserFilePanel();
    private ServerFilePanel serverFilePanel = new ServerFilePanel();
    private LogConsole logConsole = new LogConsole();

    // Controller
    @SuppressWarnings("unused")
    private Controller controller = new Controller(connection, connectionInfoLabel, controlPanel, 
                                                            userFilePanel, serverFilePanel, logConsole);


    @Override
    public void start(Stage stage) {
        // Layout
        BorderPane root = new BorderPane();
        root.setTop(connectionInfoLabel);
        root.setLeft(serverFilePanel);
        root.setRight(userFilePanel);
        root.setCenter(controlPanel);
        root.setBottom(logConsole);
        root.getStyleClass().add("main-window");

        // Main Window
        Scene scene = new Scene(root, UIMetrics.MAIN_WINDOW_WIDTH, UIMetrics.MAIN_WINDOW_HEIGHT);
        UIMetrics.applyStylesheet(scene);
        stage.setTitle("FTP Client Project");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}