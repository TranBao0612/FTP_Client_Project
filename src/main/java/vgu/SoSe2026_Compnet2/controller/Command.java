package vgu.SoSe2026_Compnet2.controller;

import vgu.SoSe2026_Compnet2.data.FileMetadata;
import vgu.SoSe2026_Compnet2.util.Connection;
import vgu.SoSe2026_Compnet2.util.ValidateFTPResponse;
import vgu.SoSe2026_Compnet2.constants.ConnectionConstant;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.ArrayList;

/**
 * Command class to handle the FTP commands sent to the server and produce results.
 */
public final class Command {
    /**
     * Send PWD command to the server to get the current working directory of the server.
     * @param controller instances stores all UI components
     * @return String represent the current working directory of the server, or null if an error occurs.
     */
    public static String pwd(Controller controller) {
        controller.sendToServer("PWD");
        String response = controller.receiveFromServer();
        if (ValidateFTPResponse.startWith(response, "257")) {
            // Extract the directory path from the response message. The path is usually enclosed in double quotes.
            int start_index = response.indexOf('"');
            int stop_index = response.indexOf('"', start_index + 1);
            if (start_index != -1 && stop_index != -1) {
                return response.substring(start_index + 1, stop_index);
            }
        }
        return null;
    } 

    /**
     * Send LIST command to the server to retrieve the list of files in the current directory.
     * @param controller instances stores all UI components
     * @param dataConnection The data connection to receive the file list from.
     * @return A list of FileMetadata objects representing the files in the current directory, or null if an error occurs.
     */
    public static List<FileMetadata> list(Controller controller, Connection dataConnection) {
        controller.sendToServer("LIST");
        // Server confirms that it is ready to transfer by "150" response
        if (!ValidateFTPResponse.startWith(controller.receiveFromServer(), "150"))
            return null;
        // Use a separate thread to receive data, since data connection may be closed 
        // after controller receives the final response from the server, which will block the program.
        List<String> data = new ArrayList<>();
        AtomicBoolean isDone = new AtomicBoolean(false);
        Thread dataThread = new Thread(() -> {
            controller.receiveFromDataConnection(dataConnection, isDone, data);
        });
        dataThread.start();
        // Stop data thread after receiving the final response from the server
        String finalResponse = controller.receiveFromServer();
        isDone.set(true);
        // Process the data received if the server confirms that the transfer is successful
        if (ValidateFTPResponse.startWith(finalResponse, "226")) {
            return FileMetadata.derivedFromUnixDescription(data);
        }
        // If the server responds with an error code, return null
        return null;
    }

    /**
     * Send PASV command to the server to enter passive mode and establish a data connection, with retry logic.
     * @param controller instances stores all UI components
     * @return Connection instance representing the data connection, or null if an error occurs.
     */
    public static Connection pasv(Controller controller) {
        for (int i = 0; i < ConnectionConstant.PASSIVE_MODE_RETRY_LIMIT; i++) {
            controller.sendToServer("PASV");
            String response = controller.receiveFromServer();
            if(ValidateFTPResponse.startWith(response, "227")) {
                try {
                    return Connection.initializeFTPDataConnection(response);
                } catch (Exception ignore) {}
            }
        }
        return null;
    }

    /**
     * Send TYPE I command to the server to set the transfer mode to binary, with retry logic.
     * @param controller instances stores all UI components
     * @return true if the server successfully changes to binary mode, false otherwise
     */
    public static boolean binaryMode(Controller controller) {
        for (int i = 0; i < ConnectionConstant.BINARY_MODE_RETRY_LIMIT; i++) {
            controller.sendToServer("TYPE I");
            String response = controller.receiveFromServer();
            if(ValidateFTPResponse.startWith(response, "200"))
                return true;
        }
        return false;
    }

    /**
     * Send CDUP command to the server to change the current directory to its parent directory.
     * @param controller instances stores all UI components
     * @return true if the directory change is successful, false otherwise
     */
    public static boolean cdup(Controller controller) {
        controller.sendToServer("CDUP");
        String response = controller.receiveFromServer();
        if(ValidateFTPResponse.startWith(response, "200") || ValidateFTPResponse.startWith(response, "250"))
            return true;
        else
            return false;
    }

    /**
     * Send CWD command to the server to change the current directory to a specified subdirectory.
     * @param controller instances stores all UI components
     * @param subFolderName the name of the subdirectory to change into
     * @return true if the directory change is successful, false otherwise
     */
    public static boolean cwd(Controller controller, String subFolderName) {
        controller.sendToServer("CWD " + subFolderName);
        String response = controller.receiveFromServer();
        if(ValidateFTPResponse.startWith(response, "250"))
            return true;
        else
            return false;
    }

    public static boolean quit(Controller controller) {
        controller.sendToServer("QUIT");
        String response = controller.receiveFromServer();
        if(ValidateFTPResponse.startWith(response, "221"))
            return true;
        else
            return false;
    }
}