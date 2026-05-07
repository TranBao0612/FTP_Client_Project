package vgu.SoSe2026_Compnet2.service;

import vgu.SoSe2026_Compnet2.constants.LoginData;
import vgu.SoSe2026_Compnet2.data.ConnectionData;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class RequestConnectionInfo extends Dialog<ConnectionData> {
    private CheckBox isAnonymousLogin = new CheckBox("Anonymous Login");
    private TextField serverURLField = new TextField();
    private TextField usernameField = new TextField();
    private TextField passwordField = new TextField();

    /**
     * Static method to display the connection info dialog and retrieve the user's input as a ConnectionData object. <br>
     * @return A ConnectionData object containing the user's input OR null if the user cancels.
     */
    public static ConnectionData request() {
        RequestConnectionInfo dialog = new RequestConnectionInfo();
        return dialog.showAndWait().orElse(null);
    }

    /**
     * Initializes the dialog for requesting FTP connection information from the user,
     *      including server URL, username, and password. <br>
     * If the user leaves any field blank, the prompt text will be used. <br>
     * If the user checks the "Anonymous Login" checkbox, 
     *      the username and password fields will be immutable. <br>
     */
    private RequestConnectionInfo() {
        getDialogPane().setContent(new VBox(10, isAnonymousLogin, serverURLField, usernameField, passwordField));
        // Set up the dialog layout
        setTitle("FTP Service - Connection Initialization");
        serverURLField.setPromptText(LoginData.DLPTEST_HOST);
        usernameField.setPromptText(LoginData.DLPTEST_USERNAME);
        passwordField.setPromptText(LoginData.DLPTEST_PASSWORD);
        // Set up anonymous login behavior
        setAnonymousLoginBehavior();
        // Add buttons
        ButtonType connectButton = new ButtonType("Connect", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(connectButton, ButtonType.CANCEL);
        // Set results converter to return ConnectionData when the "Connect" button is pressed
        setResultConverter(dialogButton -> {
            if (dialogButton == connectButton) {
                return getResults();
            }
            return null;
        });
    }

    /**
     * Retrieves the connection data entered by the user, using prompt text as defaults for any fields left blank.
     * @return A ConnectionData object containing the server URL, username, and password for the FTP connection.
     */
    private ConnectionData getResults() {
        String serverURL = serverURLField.getText().trim().isBlank() ? 
                        serverURLField.getPromptText() : serverURLField.getText().trim();
        String username = usernameField.getText().trim().isBlank() ? 
                        usernameField.getPromptText() : usernameField.getText().trim();
        String password = passwordField.getText().trim().isBlank() ? 
                        passwordField.getPromptText() : passwordField.getText().trim();
        return new ConnectionData(serverURL, username, password);
    }

    /**
     * Disable and clear the username and password fields when the "Anonymous Login" checkbox is selected, 
     *      and re-enable them when it is deselected. <br>
     */
    private void setAnonymousLoginBehavior() {
        isAnonymousLogin.setOnAction(e -> {
            if (isAnonymousLogin.isSelected()) {
                usernameField.setDisable(true);
                passwordField.setDisable(true);
                usernameField.setPromptText(LoginData.ANONYMOUS_USERNAME);
                passwordField.setPromptText(LoginData.ANONYMOUS_PASSWORD);
                usernameField.clear();
                passwordField.clear();
            } else {
                usernameField.setDisable(false);
                passwordField.setDisable(false);
                usernameField.setPromptText(LoginData.DLPTEST_USERNAME);
                passwordField.setPromptText(LoginData.DLPTEST_PASSWORD);
            }
        });
    }
}
