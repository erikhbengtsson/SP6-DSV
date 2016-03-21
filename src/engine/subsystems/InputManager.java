package engine.subsystems;

import engine.Vector2;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Singleton class for handling keyboard and mouse input. The instance of the class is located in GameEngine.
 * All KeyEvents and MouseEvents in these methods are passed from methods in GameEngine.
 */
public class InputManager {
    private static InputManager inputManager = new InputManager();
    private ArrayList<Key> gameKeys;    // Keys used in the game
    private Key[] keys;                 // Keys that can be chosen from when changing keys in settings menu
    private ArrayList<MouseButton> mouseButtons;
    private MouseEvent lastClick;   // Last mouse click made. Can be used to check is buttons are clicked.
    private Vector2 mousePosition;

    private InputManager() {
        gameKeys = new ArrayList<>();
        mouseButtons = new ArrayList<>();
        mousePosition = new Vector2();

        // Just a few keys added as an example.
        keys = new Key[]{new Key("Space", KeyEvent.VK_SPACE), new Key("Escape", KeyEvent.VK_ESCAPE),
                new Key("W", KeyEvent.VK_W), new Key("A", KeyEvent.VK_A), new Key("S", KeyEvent.VK_S),
                new Key("D", KeyEvent.VK_D), new Key("Up arrow", KeyEvent.VK_UP),
                new Key("Left arrow", KeyEvent.VK_LEFT), new Key("Right arrow", KeyEvent.VK_RIGHT)};
    }

    /**
     * Returns the instance of this class.
     *
     * @return InputManager
     */
    public static InputManager getInstance() {
        return inputManager;
    }

    /**
     * Add key that should be used in the game.
     *
     * @param key Key
     */
    public void addKey(Key key) {
        gameKeys.add(key);
    }

    /**
     * Add mouse button that should be used in the game.
     *
     * @param buttonName String
     * @param buttonCode int
     */
    public void addMouseButton(String buttonName, int buttonCode) {
        mouseButtons.add(new MouseButton(buttonName, buttonCode));
    }

    /**
     * Returns true if the key given as parameter is pressed.
     *
     * @param keyName String with name of the key being checked.
     * @return boolean isKeyPressed. True if key is being pressed, otherwise false.
     */
    public boolean isKeyPressed(String keyName) {
        for (Key gameKey : gameKeys) {
            if (keyName.equals(gameKey.getName())) {
                return gameKey.isPressed();
            }
        }
        return false;
    }

    /**
     * Check if a mouse button is pressed.
     *
     * @param buttonName String
     * @return true if pressed
     */
    public boolean isMousePressed(String buttonName) {
        for (MouseButton mouseButton : mouseButtons) {
            if (buttonName.equals(mouseButton.getName())) {
                return mouseButton.isPressed();
            }
        }
        return false;
    }

    /**
     * Check if a mouse button is clicked (pressed and released).
     *
     * @param buttonName String
     * @return true if clicked
     */
    public boolean isMouseClicked(String buttonName) {
        for (MouseButton mouseButton : mouseButtons) {
            if (buttonName.equals(mouseButton.getName())) {
                if (mouseButton.isClicked()) {
                    mouseButton.toggleClicked(false);   // Set clicked to false to "reset" for next click
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Set all keys to released.
     */
    public void resetInput() {
        for (Key gameKey : gameKeys) {
            gameKey.toggle(false);
        }
    }

    /**
     * Set pressed to true if the key being pressed is used in the game.
     *
     * @param e KeyEvent for the key being pressed.
     */
    public void keyPressed(KeyEvent e) {
        for (Key gameKey : gameKeys) {
            if (e.getKeyCode() == gameKey.getKeyCode()) {
                gameKey.toggle(true);
            }
        }
    }

    /**
     * Set pressed to false if the key being pressed is used in the game.
     *
     * @param e KeyEvent for the key being released.
     */
    public void keyReleased(KeyEvent e) {
        for (Key gameKey : gameKeys) {
            if (e.getKeyCode() == gameKey.getKeyCode()) {
                gameKey.toggle(false);
            }
        }
    }

    /**
     * Set pressed to true if the mouse button is used in the game.
     *
     * @param e MouseEvent
     */
    public void mousePressed(MouseEvent e) {
        for (MouseButton mouseButton : mouseButtons) {
            if (e.getButton() == mouseButton.getButtonCode()) {
                mouseButton.togglePressed(true);
            }
        }
    }

    /**
     * Set pressed to false if the mouse button is used in the game.
     *
     * @param e MouseEvent
     */
    public void mouseReleased(MouseEvent e) {
        for (MouseButton mouseButton : mouseButtons) {
            if (e.getButton() == mouseButton.getButtonCode()) {
                mouseButton.togglePressed(false);
            }
        }
    }

    /**
     * Set clicked to true if the mouse button is used in the game.
     * Also saves the last mouse click which can be used to check if buttons where clicked.
     *
     * @param e MouseEvent
     */
    public void mouseClicked(MouseEvent e) {
        lastClick = e;  // Save position of last click
        for (MouseButton mouseButton : mouseButtons) {
            if (e.getButton() == mouseButton.getButtonCode()) {
                mouseButton.toggleClicked(true);
            }
        }
    }

    /**
     * Keep track of the mouse position when mouse is moved.
     *
     * @param e MouseEvent
     */
    public void mouseMoved(MouseEvent e) {
        mousePosition.set(e.getX(), e.getY());
    }

    // Getter methods

    public MouseEvent getLastClick() {
        return lastClick;
    }

    public Vector2 getMousePosition() {
        return mousePosition;
    }

    public Key[] getKeys() {
        return keys;
    }

    public Key getKey(String name) {
        for (Key k : gameKeys) {
            if (k.getName().equals(name)) {
                return k;
            }
        }
        return null;
    }

    // Setter methods

    public void setMousePosition(Vector2 mousePosition) {
        this.mousePosition = mousePosition;
    }
}
