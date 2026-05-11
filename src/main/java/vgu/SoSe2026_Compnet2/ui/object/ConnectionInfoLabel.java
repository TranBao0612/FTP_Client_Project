package vgu.SoSe2026_Compnet2.ui.object;

import vgu.SoSe2026_Compnet2.ui.UIComponent;
import javafx.scene.control.Label;
import javafx.application.Platform;

public class ConnectionInfoLabel extends Label implements UIComponent {
    public ConnectionInfoLabel() {
        disconnected();
    }

    public void connected(String host, boolean isAnonymous, String username) {
        String displayUsername = isAnonymous ? "Anonymous Login" : "Username: " + username;
        Platform.runLater(() -> {
            setText("Connected to: " + host + " | " + displayUsername);
        });
    }
    

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

    @Override
    public void setStyle() {
        getStyleClass().add("connection-info-label");
    }
    
}
