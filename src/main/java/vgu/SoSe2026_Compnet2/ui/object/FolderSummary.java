package vgu.SoSe2026_Compnet2.ui.object;

import vgu.SoSe2026_Compnet2.constants.UIMetrics;
import vgu.SoSe2026_Compnet2.ui.UIComponent;
import javafx.scene.control.Label;

/**
 * A label that displays a summary of the contents of a folder, 
 *      including the number of folders, number of files, and total size in KB.
 */
public class FolderSummary extends Label implements UIComponent {
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
     * Create a label with default summary information showing 0 folders, 0 files, and 0 KB.
     */
    public FolderSummary() {
        this(0, 0, 0);
    }


    /**
     * Update the displayed summary information in the label.
     * @param numberOfFolders number of folders in the current directory
     * @param numberOfFiles number of files in the current directory
     * @param totalSizeInBytes total size of all files in the current directory, in bytes
     */
    public void updateSummary(int numberOfFolders, int numberOfFiles, long totalSizeInBytes) {
        setText(String.format("%d Folders %d Files %d K", numberOfFolders, numberOfFiles, totalSizeInBytes / 1024));
    }

    /**
     * Clear the summary information by resetting the label text to show 0 folders, 0 files, and 0 KB.
     */
    public void clear() {
        updateSummary(0, 0, 0);
    }


    /**
     * Set fixed width for the folder summary to ensure consistent layout in the UI. <br> 
     * The height is determined by the default label behavior, so it is not set here.
     */
    @Override
    public void setFixedSize() {
        setPrefWidth(UIMetrics.FOLDER_PANEL_WIDTH);
        setMinWidth(UIMetrics.FOLDER_PANEL_WIDTH);
        setMaxWidth(UIMetrics.FOLDER_PANEL_WIDTH);
    }

    
    /**
     * Apply CSS style class to style the text and the label background.
     */
    @Override
    public void setStyle() {
        getStyleClass().add("folder-summary");
    }
    
}
