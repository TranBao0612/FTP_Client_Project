package vgu.SoSe2026_Compnet2;

import vgu.SoSe2026_Compnet2.constants.UIMetrics;
import vgu.SoSe2026_Compnet2.data.ConnectionData;
import vgu.SoSe2026_Compnet2.service.*;
import vgu.SoSe2026_Compnet2.ui.panel.*;
import vgu.SoSe2026_Compnet2.controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class MainGUI extends Application {
    // Connection
    Connection connection = null;

    // Panels
    private ControlButtonPanel controlPanel = new ControlButtonPanel();
    private LogConsole logConsole = new LogConsole();

    // Controller
    private Controller controller = new Controller(connection, controlPanel, logConsole);


    @Override
    public void start(Stage stage) {


        // Top connection info panel
        ConnectionData connectionData = RequestConnectionInfo.request();
        if (connectionData == null) {
            // User cancelled the connection dialog, exit the application
            stage.close();
            return;
        }
        Label connectionInfoLabel = new Label("Connected to: " + connectionData.getServerURL() +
                " | Username: " + connectionData.getUsername() + " | Password: " + connectionData.getPassword());
        
        
        
        
        // Layout
        BorderPane root = new BorderPane();
        root.setTop(connectionInfoLabel);
        root.setCenter(controlPanel);
        root.setBottom(logConsole);

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