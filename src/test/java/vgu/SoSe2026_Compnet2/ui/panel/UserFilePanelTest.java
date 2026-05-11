package vgu.SoSe2026_Compnet2.ui.panel;

import org.junit.jupiter.api.Test;

import vgu.SoSe2026_Compnet2.ui.object.ControlButton;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

public class UserFilePanelTest extends Application {
    @Test
    public void testFileTable() {
        launch(new String[0]);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create an instance of UserFilePanel to test the file table functionality
        UserFilePanel userFilePanel = new UserFilePanel();
        // Button to retrieve path of the current directory in the UserFilePanel
        ControlButton getSelectedFilePath = new ControlButton("Get Current Directory");
        getSelectedFilePath.addAction(() -> {
            String currentDir = userFilePanel.getSelectedFileAbsolutePath();
            System.out.println("Current Directory: " + (currentDir == null ? "No file selected" : currentDir));
        });
        // For testing purposes, we can create a simple scene with the FileTable
        Scene scene = new Scene(new HBox(userFilePanel, getSelectedFilePath), 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("File Table Test");
        primaryStage.show();
    }
}
