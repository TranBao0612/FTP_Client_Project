package vgu.SoSe2026_Compnet2.ui.panel;

import vgu.SoSe2026_Compnet2.constants.Directory;
import vgu.SoSe2026_Compnet2.data.FileMetadata;
import vgu.SoSe2026_Compnet2.util.DirectoryPicker;
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseButton;

import java.util.List;
import java.util.ArrayList;
import java.io.File;

/**
 * Class representing the user file panel in the UI, which extends the abstract FilePanel.
 * The user file panel allows users to navigate their local file system, change directories, and view file metadata.
 */
public class UserFilePanel extends FilePanel {
    /**
     * Create a user file panel with the default directory set to the user's home directory. 
     * Load the file panel with the files in the default directory.
     */
    public UserFilePanel() {
        super();
        directoryDisplayer.setText(Directory.DEFAULT_USER_FILE_PANEL_DIR);
        reload(directoryDisplayer.getText());
        // Add action
        setCDButtonAction();
        setDoubleClickFolderAction();
    }

    /**
     * Set action for change directory button: open a directory picker dialog, 
     *      if a new directory is selected, reload the file panel with the new directory.
     */
    @Override
    public void setCDButtonAction() {
        changeDirButton.addAction(() -> {
            String newDir = DirectoryPicker.pick(changeDirButton);
            if (newDir == null) 
                return;
            reload(newDir);
        });
    }

    /**
     * If double-clicked item is a folder, reload the file panel with the folder as the current directory. 
     * If clicked with secondary mouse button, clear the selection in the file table.
     */
    @Override
    public void setDoubleClickFolderAction() {
        fileTable.setRowFactory(tv -> {
            TableRow<FileMetadata> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                // Clear selection when clicking on secondary mouse button
                if (event.getButton() == MouseButton.SECONDARY) {
                    fileTable.getSelectionModel().clearSelection();
                }
                // Double-click and primary mouse button to open folder
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    if (row.getItem() != null && row.getItem().getType() == FileMetadata.FileType.DIRECTORY) {
                        reload(Directory.generateFilePath(directoryDisplayer.getText(), row.getItem().getName()));
                    }
                }
            });
            return row;
        });
    }

    /**
     * Overloaded method to reload the file panel with a new folder and its corresponding file list.
     * @param folder the folder to reload the file panel with
     */
    public void reload(String folder) {
        reload(folder, getFileList(folder));
    }

    /**
     * Refresh the file panel
     */
    public void reload() {
        reload(directoryDisplayer.getText(), getFileList(directoryDisplayer.getText()));
    }

    /**
     * Retrieve metadata of files in the specified folder.
     * @param folder the folder to retrieve file metadata from
     * @return a list of FileMetadata objects representing the files in the specified folder
     */
    private List<FileMetadata> getFileList(String folder) {
        File[] filePaths = new File(folder).listFiles();
        List<FileMetadata> fileList = new ArrayList<>();
        for (File file : filePaths) {
            fileList.add(FileMetadata.derivedFromJavaFileObject(file));
        }
        return fileList;
    }
}
