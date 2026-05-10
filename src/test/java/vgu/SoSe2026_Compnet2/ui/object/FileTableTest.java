package vgu.SoSe2026_Compnet2.ui.object;

import org.junit.jupiter.api.Test;

import vgu.SoSe2026_Compnet2.data.FileMetadata;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import java.util.List;

public class FileTableTest extends Application {
    @Test
    public void testFileTable() {
        launch(new String[0]);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // For testing purposes, we can create a simple scene with the FileTable
        FileTable fileTable = new FileTable();
        ScrollPane scrollPane = new ScrollPane(fileTable);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        Scene scene = new Scene(scrollPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("File Table Test");
        primaryStage.show();

        // We can also add some dummy data to the table for testing
        List<FileMetadata> dummyFiles = new java.util.ArrayList<>();
        dummyFiles.add(new FileMetadata(FileMetadata.FileType.DIRECTORY, "File", 0, "2024-01-01"));
        dummyFiles.add(new FileMetadata(FileMetadata.FileType.FILE, "File1.txt", 1024, "2024-01-02"));
        dummyFiles.add(new FileMetadata(FileMetadata.FileType.FILE, "File2.txt", 2048, "2024-01-03"));
        fileTable.setFiles(dummyFiles);

        new Thread(() -> {
            try {
                // Reload with new data after 5 seconds
                Thread.sleep(5000);
                dummyFiles.add(new FileMetadata(FileMetadata.FileType.FILE, "File3.txt", 4096, "2024-01-04"));
                javafx.application.Platform.runLater(() -> {
                    fileTable.setFiles(new java.util.ArrayList<>(dummyFiles));
                });

                // Clear the table after another 5 seconds
                Thread.sleep(5000);
                javafx.application.Platform.runLater(() -> {
                    fileTable.clear();
                });

                // Load data again to test functionality
                Thread.sleep(5000);
                javafx.application.Platform.runLater(() -> {
                    fileTable.setFiles(new java.util.ArrayList<>(dummyFiles));
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        
    }
}
