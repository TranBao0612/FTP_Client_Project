package vgu.SoSe2026_Compnet2.ui.object;

import vgu.SoSe2026_Compnet2.constants.UIMetrics;
import vgu.SoSe2026_Compnet2.ui.UIComponent;
import javafx.scene.control.TextField;

/**
 * A textbox that displays the current directory path. <br>
 * It is non-editable but is copyable, allowing users to easily copy the directory path if needed. 
 */
public class DirectoryDisplayer extends TextField implements UIComponent {
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
     * Update the displayed directory path in the TextField.
     * @param newDirectory
     */
    public void updateDirectory(String newDirectory) {
        setText(newDirectory);
    }

    /**
     * Set the fixed width and height of the DirectoryDisplayer to ensure consistent layout in the UI.
     */
    @Override
    public void setFixedSize() {
        setPrefWidth(UIMetrics.DIRECTORY_DISPLAYER_WIDTH);
        setMaxWidth(UIMetrics.DIRECTORY_DISPLAYER_WIDTH);
        setMinWidth(UIMetrics.DIRECTORY_DISPLAYER_WIDTH);

        setPrefHeight(UIMetrics.DIRECTORY_DISPLAYER_HEIGHT);
        setMaxHeight(UIMetrics.DIRECTORY_DISPLAYER_HEIGHT);
        setMinHeight(UIMetrics.DIRECTORY_DISPLAYER_HEIGHT);
    }

    /**
     * Apply the CSS style class to style the text and the textfield.
     */
    @Override
    public void setStyle() {
        getStyleClass().add("directory-displayer");
    }
}
