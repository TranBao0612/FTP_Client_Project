package vgu.SoSe2026_Compnet2.controller;

import vgu.SoSe2026_Compnet2.ui.panel.*;
import vgu.SoSe2026_Compnet2.util.Connection;
import vgu.SoSe2026_Compnet2.ui.object.ConnectionInfoLabel;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Controller class to handle the logic of the application. 
 * Define functions of the control buttons
 */
public class Controller implements AutoCloseable {
    Connection connection;
    ConnectionInfoLabel connectionInfoLabel;
    ControlButtonPanel controlPanel;
    UserFilePanel userFilePanel;
    ServerFilePanel serverFilePanel;
    LogConsole logConsole;

    public Controller(Connection connection, 
                                    ConnectionInfoLabel connectionInfoLabel,
                                    ControlButtonPanel controlPanel, 
                                    UserFilePanel userFilePanel, 
                                    ServerFilePanel serverFilePanel, 
                                    LogConsole logConsole
    ) {
        this.connection = connection;
        this.connectionInfoLabel = connectionInfoLabel;
        this.controlPanel = controlPanel;
        this.userFilePanel = userFilePanel;
        this.serverFilePanel = serverFilePanel;
        this.logConsole = logConsole;
        // Add action listeners to control buttons
        controlPanel.connection.addAction(new ConnectionHandler(this));
        controlPanel.refresh.addAction(new RefreshHandler(this));
        controlPanel.download.addAction(new DownloadHandler(this));
        // Add action listeners for server file panel
        serverFilePanel.addCDButtonAction(ChangeDirectoryHandler.toParent(this));
        ChangeDirectoryHandler.setCdToChild(this);
        // Start with disconnected state
        disconnectedStateUI();
    }



    // ----------------------------- Control Button Actions --------------------------------

        // 3. CREATE FOLDER BUTTON
    public void createFolder() {
        // Pop up dialog to get the name of the new folder
        // Send a command to the server to create the new folder in current directory
        // If the folder is created successfully, refresh the server file panel and log the successful creation in the log console.
        // If there is an error (e.g., folder already exists, permission denied), log the error message in the log console.
    }
        // 4. DELETE BUTTON
    public void delete() {
        // If no item is selected, do nothing.
        // Determine whether the selected item is a file or a folder.
        //      If it's a file, send DELE command to the server to delete the file.
        //      If it's a folder, send RMD command to the server to delete the folder.
        // Receive the response from the server and log the result in the log console:
        //      If successfully, refresh and log the successful deletion.
        //      If there is an error (e.g., file not found, permission denied), log the error.
    }
        // 6. UPLOAD 
    public void upload() {
        // If no item is selected, do nothing.
        // Choose a file from user file panel to upload, declined if is a folder.
        // Send a command to the server to upload the selected file to current directory.
        // Receive the response from the server and log the result in the log console:
        //      If successfully, refresh server file panel and log the successful upload.
        //      If there is an error (e.g., file not found, permission denied), log the error.
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
            // Update connection info label
            disconnectedStateUI();
            // Log messages
            logConsole.log(message, isError ? LogConsole.TYPE_ERROR : LogConsole.TYPE_INFO);
            if (isError) 
                logConsole.log("Error: Connection closed. Please reconnect.", LogConsole.TYPE_ERROR);
            else
                logConsole.log("Connection closed successfully.", LogConsole.TYPE_INFO);
        } else {
            // If in this case, connection has never been established sucessfully, 
            // just log the error message without updating UI to avoid confusion.
            logConsole.log(message, LogConsole.TYPE_ERROR);
        }
        // Set connection to null so that isConnected() will return false
        connection = null;
    }

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
        controlPanel.disableAll(false);
        serverFilePanel.disable();
    }

    /**
     * Update the UI to reflect the ready state by 
     *      enabling all actions and interactions.
     */
    public void readyStateUI() {
        controlPanel.enableAll();
        serverFilePanel.enable();
    }

    /**
     * Turn off timeout for control connection to allow for long-running commands, e.g., file upload and download, without prematurely closing the connection due to timeout.
     */
    public void turnOffTimeout() {
        try {
            connection.turnOffTimeout();
        } catch (Exception e) {
            closeConnection(true, "Error turning off timeout: " + e.getMessage());
        }
    }

    /**
     * Turn on timeout for the connection to prevent hanging when the server does not respond.
     */
    public void turnOnTimeout() {
        try {
            connection.turnOnTimeout();
        } catch (Exception e) {
            closeConnection(true, "Error turning on timeout: " + e.getMessage());
        }
    }

    /**
     * Get the current connection status of the controller.
     * @return true if the controller is currently connected to a server, false otherwise.
     */
    public boolean connectButtonIsConnect() {
        return connection != null;
    }

    @Override
    public void close() {
        if (connection != null)
            connection.close();
    }
}
