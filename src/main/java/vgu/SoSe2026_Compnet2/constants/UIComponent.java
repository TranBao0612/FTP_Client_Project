package vgu.SoSe2026_Compnet2.constants;

import javafx.scene.Scene;

public final class UIComponent {
    /**
     * Prefered height for each text line in UI.
     */
    public static final int HEIGHT_PER_LINE = 30;

    // ---------- Main Window ----------
    public static final int MAIN_WINDOW_HEIGHT = 600;
    public static final int MAIN_WINDOW_WIDTH = 900;

    // ---------- Control Button ----------
    public static final int CONTROL_BUTTON_WIDTH = 90;
    public static final int CONTROL_BUTTON_HEIGHT = HEIGHT_PER_LINE;

    // ---------- Directory Displayer ----------
    public static final int DIRECTORY_DISPLAYER_WIDTH = 250;
    public static final int DIRECTORY_DISPLAYER_HEIGHT = CONTROL_BUTTON_HEIGHT;

    // ---------- Folder Table ----------
    public static final int FOLDER_TABLE_WIDTH = CONTROL_BUTTON_WIDTH + DIRECTORY_DISPLAYER_WIDTH + 10; // 20 for padding

    // ---------- Folder Panel ----------
    public static final int FOLDER_PANEL_WIDTH = FOLDER_TABLE_WIDTH;

    // ---------- Log Console ----------
    public static final int LOG_CONSOLE_HEIGHT = HEIGHT_PER_LINE * 5;


    /**
     * Applies the stylesheet css/style.css to the given scene.
     * @param scene The scene to which the stylesheet should be applied.
     */
    public static final void applyStylesheet(Scene scene) {
        scene.getStylesheets().add(Directory.STYLE_CSS_PATH);
    }
}
