package vgu.SoSe2026_Compnet2.ui.object;

import vgu.SoSe2026_Compnet2.ui.UIComponent;
import javafx.scene.control.Label;
import javafx.application.Platform;

/**
 * A custom JavaFX Label that displays the current connection status to the user.
 */
public class ConnectionInfoLabel extends Label implements UIComponent {

    /**
     * Initializes the ConnectionInfoLabel with a default "disconnected" message: "Not connected to any server."
     */
    public ConnectionInfoLabel() {
        disconnected();
        setStyle();
    }

    /**
     * Updates the label to display the current connection status, 
     *      including the host name & username or "Anonymous Login".
     * @param host The host name of the connected server.
     * @param isAnonymous Whether the connection is an anonymous login or not.
     * @param username The username for the connected server.
     */
    public void connected(String host, boolean isAnonymous, String username) {
        String displayUsername = isAnonymous ? "Anonymous Login" : "Username: " + username;
        Platform.runLater(() -> {
            setText("Connected to: " + host + " | " + displayUsername);
        });
    }
    
    /**
     * Updates the label to display a "disconnected" message: "Not connected to any server."
     */
    public void disconnected() {
        Platform.runLater(() -> {
            setText("Not connected to any server.");
        });
    }

    /**
     * ConnectionInfoLabel should be able to resize horizontally with the window, so we don't set fixed width for it.
     */
    @Override
    public void setFixedSize() {}

    /**
     * Add CSS styling.
     */
    @Override
    public void setStyle() {
        getStyleClass().add("connection-info-label");
    }
    
}
