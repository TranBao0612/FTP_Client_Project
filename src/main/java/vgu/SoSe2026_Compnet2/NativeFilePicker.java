package vgu.SoSe2026_Compnet2;

import java.awt.FileDialog;
import java.awt.Frame;

public class NativeFilePicker {
    public static void main(String[] args) {
        Frame frame = new Frame();

        FileDialog dialog = new FileDialog(frame, "Select File", FileDialog.LOAD);
        dialog.setVisible(true);

        String file = dialog.getFile();
        String directory = dialog.getDirectory();

        dialog.dispose();
        frame.dispose();

        if (file != null) {
            System.out.println("Selected: " + directory + file);
        } else {
            System.out.println("No file selected");
        }
    }
}
