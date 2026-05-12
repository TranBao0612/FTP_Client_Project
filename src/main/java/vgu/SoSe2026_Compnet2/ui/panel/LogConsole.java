package vgu.SoSe2026_Compnet2.ui.panel;

import vgu.SoSe2026_Compnet2.ui.UIComponent;
import vgu.SoSe2026_Compnet2.ui.object.LogText;
import vgu.SoSe2026_Compnet2.util.DateFormatter;
import vgu.SoSe2026_Compnet2.constants.UIMetrics;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.application.Platform;

/**
 * Custom log pane that displays log messages with timestamps and color coding based on message type.
 */
public class LogConsole extends ScrollPane implements UIComponent {
    public static final Color TYPE_COMMAND = Color.GREEN;
    public static final Color TYPE_RESPONSE = Color.BLACK;
    public static final Color TYPE_ERROR = Color.RED;
    public static final Color TYPE_INFO = Color.BLUE;

    /**
     * LogText is a custom TextFlow that handles the display of log messages in the console.
     */
    LogText logText = new LogText();


    /**
     * Initialize log console with content, set fixed height, apply styles, and allow only vertical scrolling.
     */
    public LogConsole() {
        setContent(logText);
        setFixedSize();
        setStyle();
        allowVerticalScrollOnly();
    }

    /**
     * Update the log console with a new message includes a timestamp and color based on the type of message.
     * @param text The text to be added.
     * @param type The color type of the message
     */
    public void log(String message, Color type) {
        Text text = new Text(DateFormatter.now() + " " + message + "\n");
        text.setFill(type);
        Platform.runLater(() -> logText.addLog(text));
    }


    /**
     * Set fixed height for the log console to ensure consistent layout in the UI. <br>
     * The height will stretch to fit the width of the parent container, so it is not set here.
     */
    @Override
    public void setFixedSize() {
        setPrefHeight(UIMetrics.LOG_CONSOLE_HEIGHT);
        setMinHeight(UIMetrics.LOG_CONSOLE_HEIGHT);
        setMaxHeight(UIMetrics.LOG_CONSOLE_HEIGHT);
    }


    /**
     * Apply CSS styles to the scrollpane and the text flow
     *      to determine border and text alignment
     */
    @Override
    public void setStyle() {
        this.getStyleClass().add("log-console");
    }

    
    /**
     * Configure the scroll pane to allow only vertical scrolling and
     *      fit the width of the content to the width of the scroll pane.
     */
    private void allowVerticalScrollOnly() {
        setFitToWidth(true);
        setHbarPolicy(ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
    }
}
