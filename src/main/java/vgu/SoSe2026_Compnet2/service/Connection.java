package vgu.SoSe2026_Compnet2.service;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.UnknownHostException;

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
     * Initialize a connection to the specified host and port, resources cleaned up automatically if construction fails.
     * @param host the hostname or IP address of the server
     * @param port the port number to connect to (example: 21 for FTP control connection, 20 for FTP data connection)
     * @throws IOException if an I/O error occurs when creating the socket or getting the input/output streams
     */
    public Connection(String host, int port) throws IOException {
        this.host = host;
        this.socket = new Socket(host, port);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Initializes a control connection to the specified FTP host.
     * @param host the hostname or IP address of the FTP server
     * @return Connection instance representing the control connection
     * @throws UnknownHostException if the host is unknown
     * @throws IOException if an I/O error occurs when creating the socket or getting the input/output streams
     */
    public static Connection initializeFTPControlConnection(String host) throws UnknownHostException, IOException {
        return new Connection(host, 21);
    }

    /**
     * Initializes a data connection to the specified FTP host.
     * @param host the hostname or IP address of the FTP server
     * @return Connection instance representing the data connection
     * @throws IOException if an I/O error occurs when creating the socket or getting the input/output streams
     */
    public static Connection initializeFTPDataConnection(String host) throws IOException {
        return new Connection(host, 20);
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
