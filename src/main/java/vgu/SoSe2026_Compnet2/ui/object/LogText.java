package vgu.SoSe2026_Compnet2.ui.object;

import vgu.SoSe2026_Compnet2.ui.UIComponent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * A scroll pane that serves as a log console for displaying messages, errors, and other information to the user.
 */
public class LogText extends TextFlow implements UIComponent {
    /**
     * Create a ScrollPane that serves as a log console 
     *      for displaying messages, errors, and other information to the user.
     */
    public LogText() {
        setStyle();
    }


    /**
     * Add a text node to the log text flow.
     * @param text The text to be added.
     */
    public void addLog(Text text) {
        getChildren().add(text);
    }


    /**
     * Mandatory method from UIObject interface to be overridden, 
     *      but since the size of the log text should fit the log console, 
     *      it is left empty here.
     */
    @Override
    public void setFixedSize() {}


    /**
     * Apply CSS styles to style the text and the text flow background.
     */
    @Override
    public void setStyle() {
        getStyleClass().add("log-text");
    }
}
