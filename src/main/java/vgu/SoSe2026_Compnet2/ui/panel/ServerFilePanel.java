package vgu.SoSe2026_Compnet2.ui.panel;

import javafx.scene.control.TableRow;
import javafx.scene.input.MouseButton;
import vgu.SoSe2026_Compnet2.data.FileMetadata;

/**
 * Class representing the server file panel in the UI, which extends the abstract FilePanel.
 * The server file panel allows users to navigate the server's file system by 
 *      changing to the parent folder or double-clicking on folders in the file table.
 */
public class ServerFilePanel extends FilePanel {
    /**
     * Initialize a server file panel and disable interactions by default. 
     * The change directory button is set to "To Parent Folder".
     */
    public ServerFilePanel() {
        super();
        changeDirButton.setText("To Parent Folder");
        disablePane();
    }

    /**
     * Enable to add action to the button to change to the parent folder.
     * @param cdParentFolder the action to change to the parent folder
     */
    public void addCDButtonAction(Runnable cdParentFolder) {
        changeDirButton.addAction(cdParentFolder);
    }

    /**
     * Enable to add action to clear selection and double-clicking a folder in the file table to change into that folder.
     * @param doubleClickFolder the action to change into the double-clicked folder
     */
    public void addDoubleClickFolderAction(Runnable doubleClickFolder) {
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
                        doubleClickFolder.run();
                    }
                }
            });
            return row;
        });
    }

    /**
     * Override the abstract methods from FilePanel with empty implementations, 
     *      as the actual actions will be added through the addCDButtonAction and addDoubleClickFolderAction methods
     *      by the Controller.
     */
    @Override
    public void setCDButtonAction() {}

    /**
     * Override the abstract method from FilePanel with an empty implementation, 
     *      as the actual action will be added through the addDoubleClickFolderAction method
     *      by the Controller.
     */
    @Override
    public void setDoubleClickFolderAction() {}

}
