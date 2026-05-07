package vgu.SoSe2026_Compnet2.ui;

import vgu.SoSe2026_Compnet2.constants.UIComponent;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A scroll pane that serves as a log console for displaying messages, errors, and other information to the user.
 */
public class LogConsole extends ScrollPane implements UIObject {
    public static final Color TYPE_COMMAND = Color.GREEN;
    public static final Color TYPE_RESPONSE = Color.BLACK;
    public static final Color TYPE_ERROR = Color.RED;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private TextFlow textFlow;


    /**
     * Create a ScrollPane that serves as a log console 
     *      for displaying messages, errors, and other information to the user.
     */
    public LogConsole() {
        textFlow = new TextFlow();
        setContent(textFlow);
        setFixedSize();
        setStyle();
        allowVerticalScrollOnly();
    }


    /**
     * Update the log console with a new message includes a timestamp and color based on the type of message.
     * @param message The message to be displayed in the log console.
     * @param type The color type of the message
     */
    public void addLog(String message, Color type) {
        LocalDateTime now = LocalDateTime.now();
        Text text = new Text(now.format(TIME_FORMATTER) + " " + message + "\n");
        text.setFill(type);
        textFlow.getChildren().add(text);
    }



    /**
     * Set fixed height for the log console to ensure consistent layout in the UI. <br>
     * The height will stretch to fit the width of the parent container, so it is not set here.
     */
    @Override
    public void setFixedSize() {
        setPrefHeight(UIComponent.LOG_CONSOLE_HEIGHT);
        setMinHeight(UIComponent.LOG_CONSOLE_HEIGHT);
        setMaxHeight(UIComponent.LOG_CONSOLE_HEIGHT);
    }

    /**
     * Apply CSS styles to the scrollpane and the text flow
     *      to determine border and text alignment
     */
    @Override
    public void setStyle() {
        this.getStyleClass().add("log-console");
        textFlow.getStyleClass().add("log-console-text");
    }

    /**
     * Configure the scroll pane to allow only vertical scrolling and
     *      fit the width of the content to the width of the scroll pane.
     */
    private void allowVerticalScrollOnly() {
        setFitToWidth(true);
        // textFlow.prefWidthProperty().bind(this.widthProperty());
        setHbarPolicy(ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
    }
    
}
