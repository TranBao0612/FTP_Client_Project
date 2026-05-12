package vgu.SoSe2026_Compnet2;

import vgu.SoSe2026_Compnet2.constants.LoginData;
import vgu.SoSe2026_Compnet2.data.FileMetadata;
import vgu.SoSe2026_Compnet2.util.Connection;

public class Test {
    private static volatile boolean dataConnectionActive = false;
    public static void main(String[] args) {
        try {
            Connection controlConnection = Connection.initializeFTPControlConnection("ftp.gnu.org");

            // Start a thread to read responses from the server
            new Thread(() -> {
                try {
                    while (true) {
                        String response = controlConnection.in();
                        if (response == null) {
                            System.out.println("Connection closed by server.");
                            break;
                        }
                        System.out.println("Server: " + response);
                        if (response.startsWith("227")) { // Entering Passive Mode
                            receiveData(response);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            // Send login commands
            controlConnection.out("USER " + LoginData.ANONYMOUS_USERNAME);
            controlConnection.out("PASS " + LoginData.DLPTEST_PASSWORD);

            // Print working directory
            controlConnection.out("PWD");

            // List files in the current directory
            controlConnection.out("PASV");
            while (!dataConnectionActive) {
                Thread.sleep(100); // Wait for the data connection to be established
            }
            controlConnection.out("LIST");





        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void receiveData(String passiveResponse) {
        new Thread(() -> {
            try {
                Connection dataConnection = Connection.initializeFTPDataConnection(passiveResponse);
                dataConnectionActive = true;
                String dataLine;
                while (dataConnection.isConnected() && (dataLine = dataConnection.in()) != null) {
                    System.out.println(FileMetadata.derivedFromUnixDescription(dataLine).toString());
                }
                dataConnection.close();
                dataConnectionActive = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
