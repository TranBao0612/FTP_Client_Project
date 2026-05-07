package vgu.SoSe2026_Compnet2.ui;

import vgu.SoSe2026_Compnet2.constants.UIComponent;
import javafx.scene.control.Button;

/**
 * A custom button class for control buttons in the FTP client UI. <br>
 * This class extends the standard JavaFX Button and 
 *      applies consistent styling and 
 *      fixed width for all control buttons in the application.
 */
public class ControlButton extends Button implements UIObject {
    /**
     * Creates a styled button with the specified text and action.
     * @param text the text to display on the button
     * @param action the action to perform when the button is clicked
     */
    public ControlButton(String text, Runnable action) {
        super(text);
        setFixedSize();
        setStyle();
        setOnAction(e -> action.run());
    }

    /**
     * Sets the button to have a fixed width and height to ensure consistent layout in the UI.
     */
    @Override
    public void setFixedSize() {
        setPrefWidth(UIComponent.CONTROL_BUTTON_WIDTH);
        setMaxWidth(UIComponent.CONTROL_BUTTON_WIDTH);
        setMinWidth(UIComponent.CONTROL_BUTTON_WIDTH);

        setPrefHeight(UIComponent.CONTROL_BUTTON_HEIGHT);
        setMaxHeight(UIComponent.CONTROL_BUTTON_HEIGHT);
        setMinHeight(UIComponent.CONTROL_BUTTON_HEIGHT);
    }

    @Override
    public void setStyle() {
        getStyleClass().add("control-button");
    }
    
}
