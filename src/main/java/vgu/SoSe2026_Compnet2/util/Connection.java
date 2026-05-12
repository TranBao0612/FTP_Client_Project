package vgu.SoSe2026_Compnet2.util;

import vgu.SoSe2026_Compnet2.constants.ConnectionConstant;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.net.InetSocketAddress;

/**
 * This class represents a connection to an server.
 * Provides static methods to initialize connections to FTP Server and 
 *      instance methods to send and receive messages, as well as close the connection.
 */
public class Connection implements AutoCloseable {
    private String host;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Initialize a connection to the specified host and port with an socket initialization timeout, 
     *      resources cleaned up automatically if construction fails.
     * @param host the hostname or IP address of the server
     * @param port the port number to connect to (example: 21 for FTP control connection, 20 for FTP data connection)
     * @param initializeTimeout the socket initialization timeout (ms); no timeout will be set if value <= 0
    * @throws IOException if an I/O error occurs when creating the socket or getting the input/output streams
     */
    public Connection(String host, int port, int initializeTimeout) throws IOException {
        this.host = host;
        if (initializeTimeout <= 0) {
            this.socket = new Socket(host, port);
        } else {
            this.socket = new Socket();
            socket.connect(new InetSocketAddress(host, port), initializeTimeout);
        }
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    /**
     * Initialize a connection to the specified host and port with no socket initialization timeout.
     * @param host the hostname or IP address of the server
     * @param port the port number to connect to
     * @throws IOException if an I/O error occurs when creating the socket or getting the input/output streams
     */
    public Connection(String host, int port) throws IOException {
        this(host, port, -1);
    }



    /**
     * Initializes a control connection to the specified FTP host.
     * @param host the hostname or IP address of the FTP server
     * @return Connection instance representing the control connection
     * @throws UnknownHostException if the host is unknown
     * @throws IOException if an I/O error occurs when creating the socket or getting the input/output streams
     */
    public static Connection initializeFTPControlConnection(String host) throws UnknownHostException, IOException {
        return new Connection(host, ConnectionConstant.FTP_CONTROL_PORT);
    }

    /**
     * Initializes a data connection to the specified FTP host with host and port information from the passive mode response.
     * @param enterPassiveModeResponse the response from the FTP server indicating the data connection details (format: "227 Entering Passive Mode (192,168,1,2,7,138)")
     * @return Connection instance representing the data connection
     * @throws IOException if an I/O error occurs when creating the socket or getting the input/output streams
     */
    public static Connection initializeFTPDataConnection(String enterPassiveModeResponse) throws IOException {
        String[] connectionInfo = extractConnectionInfoFromFTPPassiveModeResponse(enterPassiveModeResponse);
        String host = String.join(".", connectionInfo[0], connectionInfo[1], connectionInfo[2], connectionInfo[3]);
        int port = Integer.parseInt(connectionInfo[4]) * 256 + Integer.parseInt(connectionInfo[5]);
        return new Connection(host, port, ConnectionConstant.DATA_CONN_INIT_TIMEOUT_MILLISEC);
    }

    /**
     * Extracts the host and port information from the FTP server's passive mode response.
     * @param response the passive mode response from the FTP server (format: "227 Entering Passive Mode (192,168,1,2,7,138)")
     * @return an array containing 6 elements: the first 4 are the host octets, and the last 2 are the port numbers
     */
    public static String[] extractConnectionInfoFromFTPPassiveModeResponse(String response) {
        // Example response: "227 Entering Passive Mode (192,168,1,2,7,138)"
        int start = response.indexOf('(');
        int end = response.indexOf(')');
        return response.substring(start + 1, end).split(",");
    }

    /**
     * Gets the host of this connection.
     * @return the hostname or IP address of the server this connection is connected to
     */
    public String getHost() {
        return host;
    }

    /**
     * Checks if this connection is currently active and connected to the server.
     * @return true if the connection is active and connected, false otherwise
     */
    public boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    /**
     * Sends a message to the server through this connection.
     * @param message the message to send
     */
    public void out(String message) {
        out.println(message);
    }

    /**
     * Turns off the socket timeout, allowing the connection to wait indefinitely for a response from the server.
     * @throws IOException if an I/O error occurs when setting the socket timeout
     */
    public void turnOffTimeout() throws IOException {
        socket.setSoTimeout(0); // 0 means infinite timeout
    }

    /**
     * Turns on the socket timeout, setting it to a predefined value (e.g., 5000 milliseconds) to prevent indefinite blocking when waiting for a response from the server.
     * @throws IOException if an I/O error occurs when setting the socket timeout
     */
    public void turnOnTimeout() throws IOException {
        socket.setSoTimeout(ConnectionConstant.TIMEOUT_MILLISEC);
    }

    /**
     * Receives a message from the server through this connection.
     * @return the message received
     * @throws IOException if an I/O error occurs when reading from the input stream
     */
    public String in() throws IOException {
        return in.readLine();
    }

    /**
     * Clean resources, including the socket and associated input/output streams.
     */
    @Override
    public void close() {
        try {
            if (socket != null)
                socket.close();
        } catch (IOException ignore) {}
        try {
            if (in != null)
                in.close();
        } catch (IOException ignore) {}
        if (out != null)
            out.close();
    }
}
