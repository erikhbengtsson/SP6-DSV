package engine;

/**
 * Abstract class that represents a state of the game for example a menu or a level.
 */
public abstract class GameState {

    public GameState() {}

    /**
     * Initialize variables. Called once when object is created.
     */
    public abstract void create();

    /**
     * Update the game state.
     *
     * @param delta double used to adapt to the frame speed.
     */
    public abstract void update(double delta);

    /**
     * Draw the content of the game state.
     */
    public abstract void draw();
}
