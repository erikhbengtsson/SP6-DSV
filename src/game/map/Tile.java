package game.map;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * Tiles are blocks making up the TileMap. They have an image and can be blocked so GameObjects can't go through them.
 */
public class Tile {
    private final BufferedImage image;
    private final boolean blocked;

    public Tile(BufferedImage image, boolean blocked) {
        this.image = image;
        this.blocked = blocked;
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean isBlocked() {
        return blocked;
    }
}
