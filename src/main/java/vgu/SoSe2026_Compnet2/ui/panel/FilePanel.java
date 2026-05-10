package vgu.SoSe2026_Compnet2.ui.panel;

import vgu.SoSe2026_Compnet2.data.FileMetadata;
import vgu.SoSe2026_Compnet2.ui.object.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import java.util.List;

public abstract class FilePanel extends BorderPane {
    protected ControlButton changeDirButton;
    protected DirectoryDisplayer directoryDisplayer;
    protected FileTable fileTable;
    protected FolderSummary folderSummary;
    protected String selectedFile;

    protected FilePanel() {
        setTop(new HBox(5, directoryDisplayer, changeDirButton));
        setCenter(fileTable);
        setBottom(folderSummary);
    }

    public abstract void enableButton();
    public abstract void disableButton();
    public abstract void clear();
    public abstract void setCDButtonAction();
    public abstract void setInteractFileItemAction();

    public void reload(String currentDir, List<FileMetadata> files) {
        // Remove unknown file types & Calculate summary
        int folderCount = 0;
        int fileCount = 0;
        long totalSize = 0;
        for (FileMetadata file : files) {
            if (file.type == FileMetadata.FileType.DIRECTORY) {
                folderCount++;
            } else if (file.type == FileMetadata.FileType.FILE) {
                fileCount++;
                totalSize += file.sizeInByte;
            } else {
                files.remove(file);
            }
        }
        // Update UI
        directoryDisplayer.setText(currentDir);
        fileTable.setFiles(files);
        folderSummary.updateSummary(folderCount, fileCount, totalSize);
    }
}
