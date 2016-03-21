package engine.subsystems;

/**
 * Class representing a mouse button.
 */
public class MouseButton {
    private String name;
    private int buttonCode;
    private boolean pressed;
    private boolean clicked;

    public MouseButton(String name, int buttonCode) {
        this.name = name;
        this.buttonCode = buttonCode;
    }

    /**
     * Change between pressed and released.
     *
     * @param toggle boolean
     */
    public void togglePressed(boolean toggle) {
        if (pressed != toggle) {
            pressed = toggle;
        }
    }

    /**
     * Changed between clicked and not clicked.
     *
     * @param toggle boolean
     */
    public void toggleClicked(boolean toggle) {
        if (clicked != toggle) {
            clicked = toggle;
        }
    }

    // Getter methods

    public String getName() {
        return name;
    }

    public int getButtonCode() {
        return buttonCode;
    }

    public boolean isPressed() {
        return pressed;
    }

    public boolean isClicked() {
        return clicked;
    }
}
