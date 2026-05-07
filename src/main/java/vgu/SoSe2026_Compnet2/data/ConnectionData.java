package vgu.SoSe2026_Compnet2.data;

/**
 * This class encapsulates the connection data required to connect to a server: server URL, username, and password. <br>
 * Data shouldn't be change after creation, so no setters are provided.
 */
public class ConnectionData {
    private String serverURL;
    private String username;
    private String password;

    /**
     * Initialize the connection data, data are immutable after creation.
     * @param serverURL the URL of the server to connect to
     * @param username the username for authentication
     * @param password the password for authentication
     */
    public ConnectionData(String serverURL, String username, String password) {
        this.serverURL = serverURL;
        this.username = username;
        this.password = password;
    }

    // Getters
    public String getServerURL() {
        return serverURL;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
