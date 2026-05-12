package vgu.SoSe2026_Compnet2.ui.panel;

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
    }

    /**
     * Enable to add action to the button to change to the parent folder.
     * @param cdParentFolder the action to change to the parent folder
     */
    public void addCDButtonAction(Runnable cdParentFolder) {
        changeDirButton.addAction(cdParentFolder);
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
