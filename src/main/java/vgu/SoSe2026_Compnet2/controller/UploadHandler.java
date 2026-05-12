package vgu.SoSe2026_Compnet2.controller;

import vgu.SoSe2026_Compnet2.util.ValidateSelectedFile;
import vgu.SoSe2026_Compnet2.data.FileMetadata;

public class UploadHandler implements Runnable {
    Controller controller;

    public UploadHandler(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        FileMetadata selectedFile = ValidateSelectedFile.validate(controller.userFilePanel);
        if (selectedFile == null) {
            return;
        }
    }
    
}
