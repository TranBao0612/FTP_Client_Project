package vgu.SoSe2026_Compnet2.util;

import javafx.scene.control.Alert;
import javafx.application.Platform;

/**
 * Utility class for creating and showing error alerts.
 */
public final class ErrorAlert {
    /**
     * Create and show an alert with the given message.
     * @param message the message to display in the alert
     * @return the created alert
     */
    public static void show(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.setResizable(true);
            alert.showAndWait();
        });
    }
    
}
