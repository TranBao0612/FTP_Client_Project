package vgu.SoSe2026_Compnet2.ui;

import vgu.SoSe2026_Compnet2.constants.UIMetrics;
import vgu.SoSe2026_Compnet2.ui.object.ControlButton;
import vgu.SoSe2026_Compnet2.ui.object.DirectoryDisplayer;
import vgu.SoSe2026_Compnet2.ui.object.FolderSummary;
import vgu.SoSe2026_Compnet2.ui.object.LogText;

import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * Manual test class for testing the CSS styling UI components.
 */
public class StyleTest extends Application {
    @Test
    public void testStyle() {
        launch(new String[0]);
    }

    @Override
    public void start(Stage primaryStage) {
        // Change the argument to test different components
        Node testComponent = selectTestComponent(3); 
        BorderPane root = new BorderPane();
        // Change the method to test different layout styles
        root.setBottom(testComponent);

        Scene scene = new Scene(root, 900, 600);
        UIMetrics.applyStylesheet(scene);

        primaryStage.setScene(scene);
        primaryStage.show();

        // // Additional test for LogConsole to demonstrate adding logs with different types and colors
        // LogConsole logConsole = (LogConsole) testComponent;
        // logConsole.addLog("This is a command message.", LogConsole.TYPE_COMMAND);
        // new Thread(() -> {
        //     try {
        //         Thread.sleep(3000);
        //     } catch (InterruptedException e) {}

        //     javafx.application.Platform.runLater(() -> {
        //         for (int i = 0; i < 15; i++) {
        //             logConsole.addLog("This is a command message " + (i+1), LogConsole.TYPE_COMMAND);
        //         }
        //         logConsole.addLog("This is a response message.", LogConsole.TYPE_RESPONSE);
        //         logConsole.addLog("This is an error message.", LogConsole.TYPE_ERROR);
        //     });
        // }).start();
    }

    /**
     * Helper method to create a test action for UI components. <br>
      * 0 - Test ControlButton
      * 1 - Test DirectoryDisplayer
      * 2 - Test FolderSummary
      * 3 - Test LogConsole
      * default - Empty Pane (no styling), used for testing default styles or when no specific component is needed. <br>
     * @param type
     * @return
     */
    private Node selectTestComponent(int type) {
        switch(type) {
            case 0: 
                ControlButton button = new ControlButton("Test Button");
                button.addAction(() -> System.out.println("Button clicked!"));
                return button;
            case 1:
                return new DirectoryDisplayer("D:\\Documents\\Semester 4\\Computer Network 2\\Project\\FTP_Client_Project\\src\\main\\java\\vgu\\SoSe2026_Compnet2\\App.java");
            case 2:
                return new FolderSummary(2, 1, 4831307);
            case 3:
                return new LogText();
            default: 
                return new Pane();
        }
    }
}
