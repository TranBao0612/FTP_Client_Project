package vgu.SoSe2026_Compnet2.controller;

import vgu.SoSe2026_Compnet2.ui.panel.*;
import vgu.SoSe2026_Compnet2.util.Connection;
import vgu.SoSe2026_Compnet2.ui.object.ConnectionInfoLabel;
import vgu.SoSe2026_Compnet2.util.ErrorAlert;

/**
 * Controller class to handle the logic of the application. 
 * Define functions of the control buttons
 */
public class Controller implements AutoCloseable {
    Connection connection = null;
    ConnectionInfoLabel connectionInfoLabel;
    ControlButtonPanel controlPanel;
    UserFilePanel userFilePanel;
    ServerFilePanel serverFilePanel;
    LogConsole logConsole;

    /**
     * Initialize the controller with given UI components to handle the interactions between them.
     * @param connectionInfoLabel the label to display the connection information
     * @param controlPanel the panel containing control buttons
     * @param userFilePanel the panel displaying the user's local files
     * @param serverFilePanel the panel displaying the server files
     * @param logConsole the console to log messages and errors
     */
    public Controller(ConnectionInfoLabel connectionInfoLabel,
                                    ControlButtonPanel controlPanel, 
                                    UserFilePanel userFilePanel, 
                                    ServerFilePanel serverFilePanel, 
                                    LogConsole logConsole
    ) {
        this.connectionInfoLabel = connectionInfoLabel;
        this.controlPanel = controlPanel;
        this.userFilePanel = userFilePanel;
        this.serverFilePanel = serverFilePanel;
        this.logConsole = logConsole;
        // Add action listeners to control buttons
        controlPanel.connection.addAction(new ConnectionHandler(this));
        controlPanel.refresh.addAction(new RefreshHandler(this));
        controlPanel.createFolder.addAction(new CreateFolderHandler(this));
        controlPanel.delete.addAction(new DeleteHandler(this));
        controlPanel.download.addAction(new DownloadHandler(this));
        controlPanel.upload.addAction(new UploadHandler(this));
        // Add action listeners for server file panel
        serverFilePanel.addCDButtonAction(ChangeDirectoryHandler.toParent(this));
        ChangeDirectoryHandler.setCdToChild(this);
        // Start with disconnected state
        disconnectedStateUI();
    }





    // ----------------------------- Failed Connection Handling --------------------------------

    /**
     * Send a message to the server and log the command in the log console.
     *      Close connection if the connection is closed and log the error message.
     * @param message The message to be sent to the server.
     */
    public void sendToServer(String message) {
        if (connection.isConnected()) {
            connection.out(message);
            logConsole.log("[CLIENT] " + message, LogConsole.TYPE_COMMAND);
        } else {
            ErrorAlert.show("Connection Error", "Error: Connection closed. Please reconnect.");
            closeConnection(true, "Cannot send message.");
        }
    }

    /**
     * Receive a message from the server and log the response in the log console.
     *     Close connection if the connection is closed and log the error message.
     * @return The message received from the server, or null if an error occurs.
     */
    public String receiveFromServer() {
        try {
            String message;
            // Handle case when server sends multiple response messages for a single command, e.g., welcome message "220-"
            do {
                message = connection.in();
                logConsole.log("[SERVER] " + message, LogConsole.TYPE_RESPONSE);
            } while (!message.matches("^\\d{3} .*"));
            return message; 
        } catch (Exception e) {
            ErrorAlert.show("Connection Error", "Error: Connection closed. Please reconnect.");
            closeConnection(true, "Error receiving message: " + e.getMessage());
            return null;
        }
    }

    /**
     * Closing the connection, 
     *      update UI to reflect the disconnected state,
     *      and logging the error message.
     * @param isError Whether the event is an error.
     * @param message The error message to log.
     */
    public void closeConnection(boolean isError, String message) {
        // Skip this part if already disconnected (case when establishing connection failed)
        if (connectButtonIsConnect()) {
            // Close connection
            connection.close();
            connection = null;
            // Log messages
            logConsole.log(message, isError ? LogConsole.TYPE_ERROR : LogConsole.TYPE_INFO);
            if (isError) {
                logConsole.log("Error: Control connection closed. Please reconnect.", LogConsole.TYPE_ERROR);
            } else {
                logConsole.log("Connection closed successfully.", LogConsole.TYPE_INFO);
            }
        } else {
            // If in this case, connection has never been established sucessfully, 
            // just log the error message without updating UI to avoid confusion.
            logConsole.log(message, LogConsole.TYPE_ERROR);
        }
        // Update UI to reflect the disconnected state
        disconnectedStateUI();
    }

    /**
     * Get the current connection status of the controller.
     * @return true if the controller is currently connected to a server, false otherwise.
     */
    public boolean connectButtonIsConnect() {
        return connection != null;
    }


    // ----------------------------- UI State Handling --------------------------------

    /**
     * Update the UI to reflect the disconnected state by 
     *      updating the connection info label, disabling control buttons, and clearing server file panel.
      * This method is called when the connection is closed, either due to an error or a normal disconnection.
     */
    public void disconnectedStateUI() {
        connectionInfoLabel.disconnected();
        controlPanel.disableAll(true);
        serverFilePanel.disableAndClear();
    }

    /**
     * Update the UI to reflect the executing state by 
     *      disabling all actions required communication with the server.
     */
    public void inExecutingStateUI() {
        if (connectButtonIsConnect()) {
            controlPanel.disableAll(false);
            serverFilePanel.disable();
        }
    }

    /**
     * Update the UI to reflect the ready state by 
     *      enabling all actions and interactions.
     */
    public void readyStateUI() {
        if (connectButtonIsConnect()) {
            controlPanel.enableAll();
            serverFilePanel.enable();
        }
    }



    /**
     * Clean up resources when the controller is closed.
     */
    @Override
    public void close() {
        if (connection != null)
            connection.close();
    }
}
