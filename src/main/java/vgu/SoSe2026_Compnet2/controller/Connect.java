package vgu.SoSe2026_Compnet2.controller;

import vgu.SoSe2026_Compnet2.data.ConnectionData;
import vgu.SoSe2026_Compnet2.service.*;

public class Connect implements Runnable {
    @Override
    public void run() {
        ConnectionData connectionData = RequestConnectionInfo.request();
        // User may cancel the connection request, in that case connectionData will be null, just return and do nothing.
        if (connectionData == null)
            return;
        System.out.println("Connecting to server: " + connectionData.getServerURL() 
                            + " with username: " + connectionData.getUsername()
                            + " anonymous: " + connectionData.isAnonymous());
    }
    
}
