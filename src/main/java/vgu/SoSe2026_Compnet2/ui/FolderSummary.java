package vgu.SoSe2026_Compnet2.ui;

import vgu.SoSe2026_Compnet2.constants.UIComponent;
import javafx.scene.control.Label;

/**
 * A label that displays a summary of the contents of a folder, 
 *      including the number of folders, number of files, and total size in KB.
 */
public class FolderSummary extends Label implements UIObject {
    /**
     * Create a label that displays a summary of the contents of a folder: 
     *          number of folders, number of files, and total size in KB.
     * @param numberOfFolders number of folders in the current directory
     * @param numberOfFiles number of files in the current directory
     * @param totalSizeInBytes total size of all files in the current directory, in bytes
     */
    public FolderSummary(int numberOfFolders, int numberOfFiles, long totalSizeInBytes) {
        super(String.format("%d Folders %d Files %d K", numberOfFolders, numberOfFiles, totalSizeInBytes / 1024));
        setStyle();
    }

    /**
     * Set fixed width for the folder summary to ensure consistent layout in the UI. <br> 
     * The height is determined by the default label behavior, so it is not set here.
     */
    @Override
    public void setFixedSize() {
        setPrefWidth(UIComponent.FOLDER_PANEL_WIDTH);
        setMinWidth(UIComponent.FOLDER_PANEL_WIDTH);
        setMaxWidth(UIComponent.FOLDER_PANEL_WIDTH);
    }

    @Override
    public void setStyle() {
        getStyleClass().add("folder-summary");
    }
    
}
