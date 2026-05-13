package vgu.SoSe2026_Compnet2.constants;

import javafx.scene.Scene;

/**
 * Defines the UI metrics for the application, for ease of maintenance and consistency.
 */
public final class UIMetrics {
    /**
     * Prefered height for each text line in UI.
     */
    public static final int HEIGHT_PER_LINE = 30;

    // ---------- Main Window ----------
    public static final int MAIN_WINDOW_HEIGHT = 700;
    public static final int MAIN_WINDOW_WIDTH = 900;

    // ---------- Control Button ----------
    public static final int CONTROL_BUTTON_WIDTH = 120;
    public static final int CONTROL_BUTTON_HEIGHT = HEIGHT_PER_LINE;

    // ---------- Directory Displayer ----------
    public static final int DIRECTORY_DISPLAYER_WIDTH = 250;
    public static final int DIRECTORY_DISPLAYER_HEIGHT = CONTROL_BUTTON_HEIGHT;

    // ---------- Folder Panel ----------
    /**
     * The inner padding between the directory displayer and the change directory button in the folder panel.
     */
    public static final int FOLDER_PANEL_INNER_PADDING = 10;
    public static final int FOLDER_PANEL_WIDTH = CONTROL_BUTTON_WIDTH + DIRECTORY_DISPLAYER_WIDTH + FOLDER_PANEL_INNER_PADDING;

    // ---------- Log Console ----------
    public static final int LOG_CONSOLE_HEIGHT = HEIGHT_PER_LINE * 7;


    /**
     * Applies the stylesheet css/style.css to the given scene.
     * @param scene The scene to which the stylesheet should be applied.
     */
    public static final void applyStylesheet(Scene scene) {
        scene.getStylesheets().add(Directory.STYLE_CSS_PATH);
    }
}
