package vgu.SoSe2026_Compnet2.ui.object;

import vgu.SoSe2026_Compnet2.constants.UIMetrics;
import vgu.SoSe2026_Compnet2.ui.UIComponent;
import javafx.scene.control.Button;
import javafx.concurrent.Task;
import javafx.application.Platform;

/**
 * A custom button class for control buttons in the FTP client UI. <br>
 * This class extends the standard JavaFX Button and 
 *      applies consistent styling and 
 *      fixed width for all control buttons in the application.
 */
public class ControlButton extends Button implements UIComponent {
    /**
     * Creates a styled button with the specified text and action.
     * @param text the text to display on the button
     */
    public ControlButton(String text) {
        super(text);
        setFixedSize();
        setStyle();
    }


    /**
     * Adds an action to the button that will be executed when the button is clicked.
     * Button will be disabled while the action is being executed to prevent multiple clicks.
     * @param action the Runnable action to execute on button click
     */
    public void addAction(Runnable action) {
        setOnAction(e -> {
            action.run();
        });
    }


    /**
     * Sets the button to have a fixed width and height to ensure consistent layout in the UI.
     */
    @Override
    public void setFixedSize() {
        setPrefSize(UIMetrics.CONTROL_BUTTON_WIDTH, UIMetrics.CONTROL_BUTTON_HEIGHT);
        setMinSize(UIMetrics.CONTROL_BUTTON_WIDTH, UIMetrics.CONTROL_BUTTON_HEIGHT);
        setMaxSize(UIMetrics.CONTROL_BUTTON_WIDTH, UIMetrics.CONTROL_BUTTON_HEIGHT);
    }

    
    /*
     * Apply CSS style class to style the text and button states.
     */
    @Override
    public void setStyle() {
        getStyleClass().add("control-button");
    }
    
}
