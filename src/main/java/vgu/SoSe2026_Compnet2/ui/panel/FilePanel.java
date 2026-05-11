package vgu.SoSe2026_Compnet2.ui.panel;

import vgu.SoSe2026_Compnet2.ui.UIComponent;
import vgu.SoSe2026_Compnet2.constants.UIMetrics;
import vgu.SoSe2026_Compnet2.data.FileMetadata;
import vgu.SoSe2026_Compnet2.ui.object.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.application.Platform;
import java.util.List;
import java.nio.file.Path;

/**
 * Abstract class representing a file panel in the UI, which can be either a user file panel or a server file panel. 
 * The file panel consists of a directory displayer, a change directory button, a file table, and a folder summary.
 */
public abstract class FilePanel extends BorderPane implements UIComponent {
    public ControlButton changeDirButton = new ControlButton("Change Directory");
    public DirectoryDisplayer directoryDisplayer = new DirectoryDisplayer("");
    public FileTable fileTable = new FileTable();
    public FolderSummary folderSummary = new FolderSummary();

    /**
     * Initialize a file panel with a directory displayer, a change directory button, a file table, and a folder summary. 
      * Set the layout, define fixed width of the file panel and apply CSS styles. 
      * The change directory button and file table interactions will be defined in the subclasses (UserFilePanel and ServerFilePanel).
     */
    protected FilePanel() {
        // Layout
        setTop(new HBox(UIMetrics.FOLDER_PANEL_INNER_PADDING, directoryDisplayer, changeDirButton));
        setCenter(fileTable);
        setBottom(folderSummary);
        // Style and size
        setFixedSize();
        setStyle();
    }

    /**
     * Set action for change directory button, to be implemented in subclasses.
     */
    public abstract void setCDButtonAction();
    /**
     * Set action for double-clicking a folder in the file table, to be implemented in subclasses.
     */
    public abstract void setDoubleClickFolderAction();
    
    /**
     * Get the metadata of the currently selected file in the file table.
     * @return the metadata of the selected file, or null if no file is selected
     */
    public FileMetadata getSelectedFile() {
        return fileTable.getSelectionModel().getSelectedItem();
    }

    /**
     * Get the absolute path of the currently selected file in the file table.
     * @return the absolute path of the selected file, or null if no file is selected
     */
    public String getSelectedFileAbsolutePath() {
        FileMetadata selectedFile = getSelectedFile();
        if (selectedFile == null) 
            return null;
        return Path.of(directoryDisplayer.getText()).resolve(selectedFile.getName()).toString();
    }

    /**
     * Enable the file panel for user interactions, by enabling the change directory button.
     */
    public void enablePane() {
        Platform.runLater(() -> {
            changeDirButton.setDisable(false);
        });
    }
    /**
     * Disable the file panel for user interactions, by disabling the change directory button 
     *          and clear the pane.
     */
    public void disablePane() {
        Platform.runLater(() -> {
            changeDirButton.setDisable(true);
        });
        clear();
    }
    /**
     * Clear the file panel by clearing the directory displayer, file table, and folder summary.
     */
    public void clear() {
        Platform.runLater(() -> {
            directoryDisplayer.setText("");
            fileTable.clear();
            folderSummary.clear();
        });
    }

     /**
     * Reload the file panel with the provided file list and update the directory displayer and folder summary accordingly. 
     * Remove unknown file types from the file list before updating the UI.
     * @param currentDir The current directory path to be displayed in the directory displayer.
     * @param files The list of FileMetadata objects representing the files to be displayed in the file table.
     */

    public void reload(String currentDir, List<FileMetadata> files) {
        new Thread(() -> {
            // Remove unknown file types & Calculate summary
            files.removeIf(file -> file.getType() == FileMetadata.FileType.UNKNOWN);
            int[] folderCount = {0};
            int[] fileCount = {0};
            long[] totalSize = {0};
            for (FileMetadata file : files) {
                if (file.getType() == FileMetadata.FileType.DIRECTORY) {
                    folderCount[0]++;
                } else {
                    fileCount[0]++;
                    totalSize[0] += file.getSizeInByte();
                }
            }
            // Update UI
            Platform.runLater(() -> {
                directoryDisplayer.setText(currentDir);
                fileTable.setFiles(files);
                folderSummary.updateSummary(folderCount[0], fileCount[0], totalSize[0]);
            });
        }).start();
    }

    /**
     * Set fixed width for the file panel, height determine by parent's container.
     */
    @Override
    public void setFixedSize() {
        setPrefWidth(UIMetrics.FOLDER_PANEL_WIDTH);
        setMinWidth(UIMetrics.FOLDER_PANEL_WIDTH);
        setMaxWidth(UIMetrics.FOLDER_PANEL_WIDTH);
    }

    /**
     * Apply CSS style class to the file panel. No border and no background color.
     */
    @Override
    public void setStyle() {
        getStyleClass().add("file-panel");
    }
}
