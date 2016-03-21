package engine;

import game.Game;
import game.map.TileMap;
import javafx.scene.shape.Circle;

import java.awt.*;

/**
 * Abstract class representing a game object like a human controlled character or an enemy.
 */
public abstract class GameObject {
    protected final Sprite sprite;
    protected Vector2 position;
    protected Vector2 velocity;
    protected int width;
    protected int height;
    protected final TileMap tileMap;

    // Used for checking if tiles surrounding GameObject is blocked
    protected boolean topLeft, topRight, bottomLeft, bottomRight;

    public GameObject(TileMap tileMap, double x, double y) {
        sprite = new Sprite();
        this.tileMap = tileMap;
        position = new Vector2(x, y);
        velocity = new Vector2();
        create();
    }

    /**
     * Initialize variables. Called once when object is created.
     */
    protected abstract void create();

    /**
     * Update the game object.
     *
     * @param delta double used to adapt to the frame speed.
     */
    public abstract void update(double delta);

    /**
     * Draw the game object.
     */
    public abstract void draw();

    /**
     * Returns a Rectangle representing the bounds of the game object. Used for handling collisions.
     *
     * @return Rectangle representing the game object
     */
    public Rectangle getRectangle() {
        return new Rectangle((int) position.x, (int) position.y, width, height);
    }

    /**
     * Returns a Circle representing the bounds of the game object. Used for handling collisions.
     * Circle radius is the game objects width divided by two.
     *
     * @return Circle representing the game object
     */
    public Circle getCircle() {
        return new Circle(position.x + width / 2, position.y + height / 2, width / 2);
    }

    /**
     * Returns the a Vector2 with the x- and y-position of the GameObjects center
     *
     * @return Vector2 with center point of GameObject
     */
    public Vector2 getCenter() {
        return new Vector2(position.x + width / 2, position.y + height / 2);
    }

    /**
     * Checks which four (or less) tiles the GameObject is on.
     * Only works if game object is small enough to fit on four tiles.
     * Also checks if the tiles are blocked or free to move through.
     *
     * @param x double, horizontal position
     * @param y double, vertical position
     */
    protected void checkSurroundingTiles(double x, double y) {

        // Get row and column of the tiles the GameObject can collide with
        int leftTile = tileMap.getColumnTile((int) x);
        int rightTile = tileMap.getColumnTile((int) (x + width) - 1);
        int topTile = tileMap.getRowTile((int) y);
        int bottomTile = tileMap.getRowTile((int) (y + height) - 1);

        // Check if the tiles are blocked
        topLeft = tileMap.isBlocked(topTile, leftTile);
        topRight = tileMap.isBlocked(topTile, rightTile);
        bottomLeft = tileMap.isBlocked(bottomTile, leftTile);
        bottomRight = tileMap.isBlocked(bottomTile, rightTile);
    }

    /**
     * Checks if the game object is on screen.
     * This is used to prevent drawing objects not on screen.
     *
     * @return true if game object is on screen
     */
    public boolean isOnScreen() {
        return position.x + tileMap.getX() + width > 0 ||                                   // Left limit of screen
                position.x + tileMap.getX() - width < Game.renderer.getScreenWidth() ||     // Right limit of screen
                position.y + tileMap.getY() + height > 0 ||                                 // Upper limit of screen
                position.y + tileMap.getY() - height < Game.renderer.getScreenHeight();     // Lower limit of screen
    }

    // Getter methods

    public double getX() {
        return position.x;
    }

    public double getY() {
        return position.y;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // Setter methods

    public void setPosition(double x, double y) {
        position.x = x;
        position.y = y;
    }

    public void setPosition(Vector2 other) {
        position = other;
    }

    public void setDirection(double x, double y) {
        velocity.x = x;
        velocity.y = y;
    }

    public void setVelocity(Vector2 other) {
        velocity = other;
    }
}
