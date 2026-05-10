package vgu.SoSe2026_Compnet2.ui.object;

import javafx.application.Application;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class FileBrowser extends Application {

    // Table and current directory
    private TableView<FileItem> table = new TableView<>();
    private File currentDir = new File("D:\\Documents\\");

    @Override
    public void start(Stage stage) {
        // Columns
        TableColumn<FileItem, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> data.getValue().nameProperty());

        TableColumn<FileItem, Long> sizeCol = new TableColumn<>("Size");
        sizeCol.setCellValueFactory(data -> data.getValue().sizeProperty().asObject());

        TableColumn<FileItem, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(data -> data.getValue().dateProperty());

        table.getColumns().addAll(nameCol, sizeCol, dateCol);
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        loadDirectory(currentDir);

        // Single click selection + double click navigation
        table.setRowFactory(tv -> {
            TableRow<FileItem> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {

                    if (row.isEmpty()) return;

                    FileItem item = row.getItem();

                    // ONLY allow folder navigation
                    if (item.getFile().isDirectory()) {
                        currentDir = item.getFile();
                        loadDirectory(currentDir);
                    }

                    // files: do nothing explicitly
                }
            });

            return row;
        });

        // Button to get selected file path
        Button getPathBtn = new Button("Get Selected Path");
        getPathBtn.setOnAction(e -> {
            FileItem selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                System.out.println("Selected Path: " + selected.getFile().getAbsolutePath());
            } else {
                System.out.println("No file selected");
            }
        });

        // Resfresh button
        Button refreshBtn = new Button("Refresh");
        refreshBtn.setOnAction(e -> refresh());

        BorderPane root = new BorderPane();
        root.setCenter(table);
        root.setBottom(new HBox(10, getPathBtn, refreshBtn));

        stage.setScene(new Scene(root, 700, 400));
        stage.setTitle("File Browser");
        stage.show();
    }

    private void loadDirectory(File dir) {
        File[] files = dir.listFiles();
        ObservableList<FileItem> items = FXCollections.observableArrayList();

        if (files != null) {
            // Sort by name (A → Z), directories and files together
            Arrays.sort(files, (f1, f2) ->
                    f1.getName().compareToIgnoreCase(f2.getName())
            );
            for (File f : files) {
                items.add(new FileItem(f));
            }
        }

        table.setItems(items);
    }

    private void refresh() {
        loadDirectory(currentDir);
    }

    // File model
    public static class FileItem {
        private final File file;
        private final SimpleStringProperty name;
        private final SimpleLongProperty size;
        private final SimpleStringProperty date;

        public FileItem(File file) {
            this.file = file;
            this.name = new SimpleStringProperty(file.getName());

            this.size = new SimpleLongProperty(
                    file.isDirectory() ? 0 : file.length()
            );

            String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm")
                    .format(new Date(file.lastModified()));

            this.date = new SimpleStringProperty(formattedDate);
        }

        public File getFile() {
            return file;
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public SimpleLongProperty sizeProperty() {
            return size;
        }

        public SimpleStringProperty dateProperty() {
            return date;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
