package vgu.SoSe2026_Compnet2.controller;

public final class Command {
    /**
     * Send PWD command to the server to get the current working directory of the server.
     * @param controller instances stores all UI components
     * @return String represent the current working directory of the server, or null if an error occurs.
     */
    public static String pwd(Controller controller) {
        controller.sendToServer("PWD");
        return controller.receiveFromServer();

    } 
}
