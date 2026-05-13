package vgu.SoSe2026_Compnet2.controller;

import vgu.SoSe2026_Compnet2.data.ConnectionData;
import vgu.SoSe2026_Compnet2.ui.panel.LogConsole;
import vgu.SoSe2026_Compnet2.util.*;
import vgu.SoSe2026_Compnet2.constants.LoginData;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Runnable class to handle the connection logic when the connect button is clicked.
 */
public class ConnectionHandler implements Runnable {
    Controller controller;

    /**
     * Handle the connection logic when the connect button is clicked. <br>
     * The connection process includes: connect to the server and handle login procedure.
     *      Refresh automatically if the connection and login are successful, or log error message if any step fails. <br> 
     * @param controller instances stores all UI components
     */
    public ConnectionHandler(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        // Disable the connect button to prevent multiple connection attempts while connecting or disconnecting.
        controller.inExecutingStateUI();
        // Must enable again after finish, impleneted in both connect() and disconnect() methods to ensure the button is enabled again
        if (!controller.connectButtonIsConnect()) {
            connect();
        } else {
            disconnect();
        }
    }


    /**
     * Connect to the server using the provided connection information. 
     *      Enable UI interactions and log the connection status in the log console if the connection is successful. 
     *      Otherwise, log the error message in the log console.
     */
    private void connect() {
        ConnectionData connectionData = RequestConnectionInfo.request();
        // User may cancel the connection request, in that case connectionData will be null, just return and do nothing.
        if (connectionData == null)
            return;
        new Thread(() -> {
            try {
                controller.connection = Connection.initializeFTPControlConnection(connectionData.getServerURL());
                String welcomeMessage = controller.receiveFromServer();
                if (ValidateFTPResponse.startWith(welcomeMessage, "220")) {
                    if (loginSuccessful(connectionData)) {
                        updateUIOnSuccess(connectionData);
                        // Automatically refresh the server file panel after successful connection and login.
                        new RefreshHandler(controller).run();
                    }
                } else {
                    // Connection is closed by the server, log the error message and close the connection.
                    ErrorAlert.show("Connection Error", "Error: Connection rejected by server. Please try again.");
                    controller.closeConnection(true, "Connection closed by server.");
                }
            } catch (UnknownHostException e) {
                ErrorAlert.show("Connection Error", "Error: Unknown host. Please check the server URL and try again.");
                controller.closeConnection(true, "Unknown host: " + e.getMessage());
            } catch (IOException e) {
                ErrorAlert.show("Connection Error", "Error: Failed to connect to server. Please check your network connection and try again.");
                controller.closeConnection(true, "Failed to connect to server: " + e.getMessage());
            }
        }).start();
    }


    /**
     * Disconnect from the server, disable UI interactions, and log the disconnection status in the log console.
     * This method run on JavaxFX application thread.
     */
    private void disconnect() {
        new Thread(() -> {
            if (Command.quit(controller)) {
                controller.closeConnection(false, "Quiting success, disconnected gracefully from server.");
            } else {
                controller.closeConnection(true, "Failed to disconnect from server properly.");
            }
        }).start();
    }

    /**
     * Update the UI after a successful connection is established.
     * @param data The connection data used to establish the connection, including server URL, username, and whether it's an anonymous login.
     */
    private void updateUIOnSuccess(ConnectionData data) {
        controller.readyStateUI();
        controller.connectionInfoLabel.connected(data.getServerURL(), data.isAnonymous(), data.getUsername());
        controller.logConsole.log("Successfully connected to " + data.getServerURL(), LogConsole.TYPE_INFO);
    }

    private boolean loginSuccessful(ConnectionData data) {
        if (data.isAnonymous()) {
            return loginAnonymously(data);
        } else {
            return loginProcedure(data, true); // Login with provided username and password if not anonymous login.
        }
    }

    /**
     * Attempt to login anonymously using the default anonymous username.
     * If failed, try alternative anonymous usernames defined in LoginData.AlTERNATIVE_ANONYMOUS_USERNAME.
     * @param data The connection data containing the server URL and anonymous login information.
     * @return true if any anonymous login attempt is successful, false otherwise.
     */
    private boolean loginAnonymously(ConnectionData data) {
        boolean success = loginProcedure(data, false);
        if (success)
            return true;
        for (String alternativeUsername : LoginData.AlTERNATIVE_ANONYMOUS_USERNAME) {
            // If anonymous login with default username fails. Try alternative anonymous username.
            ConnectionData alternative = new ConnectionData(data.getServerURL(), alternativeUsername, data.getPassword(), true);
            success = loginProcedure(alternative, false);
            if (success)
                break;
        }
        if (!success) {
            ErrorAlert.show("Login Error", "Anonymous login failed. " +
                    "This server probably does not allow anonymous login, or has specific requirements for anonymous login that are not met by the default or alternative usernames.");
            controller.closeConnection(true, "All anonymous login attempts failed. " +
                    "This server probably does not allow anonymous login, or has specific requirements for anonymous login that are not met by the default or alternative usernames."
            );
        }
        // If all anonymous login attempts fail, return false.
        return success;
    }

    /**
     * Perform the login procedure by sending the username and password to the server and checking the responses.
     * @param data The connection data containing the username and password for login.
     * @return true if login is successful, false otherwise.
     */
    private boolean loginProcedure(ConnectionData data, boolean autoCloseOnFailure) {
        // Send username
        String response;
        controller.sendToServer("USER " + data.getUsername());
        response = controller.receiveFromServer();
        // Some server may not require pasword 
        if (ValidateFTPResponse.startWith(response, "230"))
            return true;
        // But if the server requires password and username is rejected, close connection and log error message.
        if (ValidateFTPResponse.startWith(response, "530") || !ValidateFTPResponse.startWith(response, "331")) {
            if (autoCloseOnFailure) {
                ErrorAlert.show("Login Error", "Login failed: Username \"" + data.getUsername() + "\" rejected.");
                controller.closeConnection(true, "Login failed: Username rejected - " + data.getUsername());
            } else {
                controller.logConsole.log("Login failed: Username rejected - " + data.getUsername(), LogConsole.TYPE_ERROR);
            }
            return false;
        }
        // Send password
        controller.sendToServer("PASS " + data.getPassword());
        response = controller.receiveFromServer();
        if (!ValidateFTPResponse.startWith(response, "230")) {
            if (autoCloseOnFailure) {
                ErrorAlert.show("Login Error", "Login failed: Password rejected.");
                controller.closeConnection(true, "Login failed: Password rejected.");
            } else {
                controller.logConsole.log("Login failed: Password rejected.", LogConsole.TYPE_ERROR);
            }
            return false;
        }
        // Login successful
        return true;
    }
}
