package vgu.SoSe2026_Compnet2.ui;

/**
 * Interface for UI components in the application.
 * To enforce a consistent design and behavior across all UI components, 
 *      any class implementing this interface must provide method to set a fixed size and apply styling to the component.
 */
public interface UIComponent {
    public void setFixedSize();
    public void setStyle();
}
