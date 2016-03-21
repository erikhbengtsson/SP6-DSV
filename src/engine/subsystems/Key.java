package engine.subsystems;

/**
 * Class representing a keyboard key.
 */
public class Key {
    private String name;
    private int keyCode;
    private boolean pressed;

    public Key(String name, int keyCode) {
        this.name = name;
        this.keyCode = keyCode;
    }

    /**
     * Toggle between pressed and released.
     *
     * @param toggle boolean
     */
    public void toggle(boolean toggle) {
        if (pressed != toggle) {
            pressed = toggle;
        }
    }

    // Getter methods

    public String getName() {
        return name;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public boolean isPressed() {
        return pressed;
    }

    // Setter methods

    public void setName(String name) {
        this.name = name;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    @Override
    public String toString() {
        return name;
    }
}
