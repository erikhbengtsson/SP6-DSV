package game.gameobjects;

import engine.Animation;
import engine.GameObject;
import game.Game;
import game.map.TileMap;

import java.awt.image.BufferedImage;

/**
 * Item that's collected by player to complete levels.
 */
public class Coin extends GameObject {

    public Coin(TileMap tileMap, double x, double y) {
        super(tileMap, x, y);
    }

    @Override
    protected void create() {
        width = 70;
        height = 70;

        BufferedImage[] coinSprites = new BufferedImage[10];

        for (int i = 0; i < coinSprites.length; i++) {
            coinSprites[i] = Game.loader.loadImage("/coin/" + i + ".png");
        }

        sprite.addAnimation("coin", new Animation(coinSprites, 200));
        sprite.setAnimation("coin");
    }

    @Override
    public void update(double delta) {
        sprite.update();
    }

    @Override
    public void draw() {
        double mapX = tileMap.getX();
        double mapY = tileMap.getY();

        try {
            Game.renderer.getGraphics2D().drawImage(
                    sprite.getAnimation().getImage(),
                    (int) (mapX + position.x),
                    (int) (mapY + position.y),
                    width,
                    height,
                    null
            );
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
