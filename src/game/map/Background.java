package game.map;

import engine.Vector2;
import game.Game;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * Background image that can be used in levels and menus.
 * Can move in the same direction as the level at a selected speed.
 */
public class Background {
    private final BufferedImage image;
    private Vector2 position;
    private final double speedFactor;   // Speed that background moves compared to rest of level. 1 is same speed, 0.1 is 10 % speed etc.

    public Background(String filePath, double speedFactor) {
        this.speedFactor = speedFactor;
        position = new Vector2();
        image = Game.loader.loadImage(filePath);
    }

    /**
     * Moves the background along with rest of level at the speed determined by speedFactor.
     * Called in the levels update method.
     *
     * @param mapPosition Vector2 with position of the TileMap
     */
    public void update(Vector2 mapPosition) {
        position.x = (mapPosition.x * speedFactor) % Game.renderer.getScreenWidth();
        position.y = (mapPosition.y * speedFactor) % Game.renderer.getScreenHeight();
    }

    /**
     * Draws the background. Called in the levels draw method.
     */
    public void draw() {
        Game.renderer.getGraphics2D().drawImage(image, (int) position.x, (int) position.y, null);

        if (position.x < 0) {   // Screen is moving left
            Game.renderer.getGraphics2D().drawImage(image, (int) (position.x + image.getWidth()), (int) position.y, null);
        } else if (position.x > 0) {    // Screen moving right
            Game.renderer.getGraphics2D().drawImage(image, (int) (position.x - image.getWidth()), (int) position.y, null);
        }
    }
}
