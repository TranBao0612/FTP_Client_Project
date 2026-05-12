package vgu.SoSe2026_Compnet2.controller;

import vgu.SoSe2026_Compnet2.data.FileMetadata;
import vgu.SoSe2026_Compnet2.util.Connection;
import vgu.SoSe2026_Compnet2.util.ValidateFTPResponse;
import vgu.SoSe2026_Compnet2.constants.ConnectionConstant;
import vgu.SoSe2026_Compnet2.constants.Directory;
import vgu.SoSe2026_Compnet2.ui.panel.LogConsole;

import java.util.List;
import java.util.ArrayList;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;

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
        // Receive file list from data connection
        List<String> data = new ArrayList<>();
        String message;
        while (true) {
            try {
                message = dataConnection.in();
                if (message == null)
                    break;
                data.add(message);
            } catch (Exception e) {
                break;
            }
        }
        // Process the data received if the server confirms the transfer is successful
        String finalResponse = controller.receiveFromServer();
        if (ValidateFTPResponse.startWith(finalResponse, "226")) {
            return FileMetadata.derivedFromUnixDescription(data);
        }
        // If the server responds with an error code, return null
        return null;
    }

    /**
     * Send RETR command to the server to retrieve a file from the current directory of server.
     * @param controller instances stores all UI components
     * @param dataConnection The data connection to receive the file data from.
     * @param serverFilename The name of the file to retrieve from the server.
     * @param userFilePath The path where the downloaded file will be saved.
     * @return true if the file retrieval is successful, false otherwise.
     */
    public static boolean retr(Controller controller, Connection dataConnection, String serverFilename, String userFilePath) {
        controller.sendToServer("RETR " + serverFilename);
        // Server confirms that it is ready to transfer by "150" response
        if (!ValidateFTPResponse.startWith(controller.receiveFromServer(), "150"))
            return false;
        // Receive file data through data connection
        try {
            InputStream dataInputStream = dataConnection.getInStream();
            FileOutputStream fileOutputStream = new FileOutputStream(userFilePath);
            byte[] buffer = new byte[ConnectionConstant.DATA_TRANSFER_CHUNK_SIZE];
            int bytesRead;
            while ((bytesRead = dataInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            fileOutputStream.close();
        } catch (Exception e) {
            return false;
        }
        // Wait for the server to confirm the transfer is successful
        String finalResponse = controller.receiveFromServer();
        if (ValidateFTPResponse.startWith(finalResponse, "226")) {
            return true;
        }
        // If the server responds with an error code, return false
        return false;
    }


    /**
     * Send STOR command to the server to store a file in the current directory of user.
     * @param controller instances stores all UI components
     * @param dataConnection The data connection to send the file data through.
     * @param serverFilename The name of the file to store to the server.
     * @param userFilePath The path of the file on the user's local system.
     * @return true if the file storage is successful, false otherwise.
     */
    public static boolean stor(Controller controller, Connection dataConnection, String serverFilename, String userFilePath) {
        controller.sendToServer("STOR " + serverFilename);
        // Server confirms that it is ready to transfer by "150" response
        if (!ValidateFTPResponse.startWith(controller.receiveFromServer(), "150"))
            return false;
        // Send file data through data connection
        try {
            OutputStream dataOutputStream = dataConnection.getOutStream();
            FileInputStream fileInputStream = new FileInputStream(userFilePath);
            byte[] buffer = new byte[ConnectionConstant.DATA_TRANSFER_CHUNK_SIZE];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                dataOutputStream.write(buffer, 0, bytesRead);
            }
            fileInputStream.close();
            dataConnection.close();
        } catch (Exception e) {
            return false;
        }
        // Wait for the server to confirm the transfer is successful
        String finalResponse = controller.receiveFromServer();
        if (ValidateFTPResponse.startWith(finalResponse, "226")) {
            return true;
        }
        // If the server responds with an error code, return false
        return false;
    }


    /**
     * Send QUIT command to the server to terminate the FTP session.
     * @param controller instances stores all UI components
     * @return true if the server confirms the termination with a "221" response, false otherwise.
     */
    public static boolean quit(Controller controller) {
        controller.sendToServer("QUIT");
        String response = controller.receiveFromServer();
        if(ValidateFTPResponse.startWith(response, "221"))
            return true;
        else
            return false;
    }
}