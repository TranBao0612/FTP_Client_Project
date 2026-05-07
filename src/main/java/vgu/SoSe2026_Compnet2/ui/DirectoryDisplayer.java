package vgu.SoSe2026_Compnet2.ui;

import vgu.SoSe2026_Compnet2.constants.UIComponent;
import javafx.scene.control.TextField;

/**
 * A textbox that displays the current directory path. <br>
 * It is non-editable but is copyable, allowing users to easily copy the directory path if needed. 
 */
public class DirectoryDisplayer extends TextField implements UIObject {
    /**
     * Create a TextField to display the current directory path.
     * @param directory The directory path to be displayed in the TextField.
     */
    public DirectoryDisplayer(String directory) {
        super(directory);
        setEditable(false);
        setFixedSize();
        setStyle();
    }

    /**
     * Set the fixed width and height of the DirectoryDisplayer to ensure consistent layout in the UI.
     */
    @Override
    public void setFixedSize() {
        setPrefWidth(UIComponent.DIRECTORY_DISPLAYER_WIDTH);
        setMaxWidth(UIComponent.DIRECTORY_DISPLAYER_WIDTH);
        setMinWidth(UIComponent.DIRECTORY_DISPLAYER_WIDTH);

        setPrefHeight(UIComponent.DIRECTORY_DISPLAYER_HEIGHT);
        setMaxHeight(UIComponent.DIRECTORY_DISPLAYER_HEIGHT);
        setMinHeight(UIComponent.DIRECTORY_DISPLAYER_HEIGHT);
    }

    @Override
    public void setStyle() {
        getStyleClass().add("directory-displayer");
    }
}
