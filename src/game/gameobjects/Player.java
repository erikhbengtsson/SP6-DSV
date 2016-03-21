package game.gameobjects;

import engine.*;
import game.Game;
import game.map.TileMap;

import java.awt.image.BufferedImage;

/**
 * Human controlled character that can walk and jump.
 */
public class Player extends GameObject {

    // Player actions
    private enum Action {
        IDLE,
        RUNNING,
        JUMPING,
        FALLING,
        DIZZY
    }

    private Action currentAction;
    private boolean hitByBall;
    private boolean playDizzyAnimation;
    private PlatformerMovement movement;

    public Player(TileMap tileMap, double x, double y) {
        super(tileMap, x, y);
    }

    @Override
    protected void create() {
        width = 102;
        height = 136;
        movement = new PlatformerMovement(0.7, 0.2, 9, -13.0, 8.5, 0.5);

        // Animations
        BufferedImage[] idleSprites = new BufferedImage[2];
        BufferedImage[] runSprites = new BufferedImage[4];
        BufferedImage[] fallSprites = new BufferedImage[1];
        BufferedImage[] jumpSprites = new BufferedImage[1];
        BufferedImage[] dizzySprites = new BufferedImage[2];

        for (int i = 0; i < idleSprites.length; i++) {
            idleSprites[i] = Game.loader.loadImage("/player/idle/" + i + ".png");
        }

        for (int i = 0; i < runSprites.length; i++) {
            runSprites[i] = Game.loader.loadImage("/player/run/" + i + ".png");
        }

        for (int i = 0; i < dizzySprites.length; i++) {
            dizzySprites[i] = Game.loader.loadImage("/player/dizzy/" + i + ".png");
        }

        fallSprites[0] = Game.loader.loadImage("/player/jump fall/0.png");
        jumpSprites[0] = Game.loader.loadImage("/player/jump up/0.png");

        sprite.addAnimation("idle", new Animation(idleSprites, 300));
        sprite.addAnimation("run", new Animation(runSprites, 100));
        sprite.addAnimation("jump", new Animation(jumpSprites, 1000));
        sprite.addAnimation("fall", new Animation(fallSprites, 1000));
        sprite.addAnimation("dizzy", new Animation(dizzySprites, 600));
        sprite.setAnimation("idle");
        currentAction = Action.IDLE;
    }

    @Override
    public void update(double delta) {
        movement.update(delta, velocity);

        if (hitByBall) {
            playDizzyAnimation = true;
        }

        updateAnimation();
        tileCollision();
    }

    /**
     * Change between the different animations depending on whats happening in the game.
     */
    private void updateAnimation() {
        if (playDizzyAnimation) {
            if (currentAction != Action.DIZZY) {
                currentAction = Action.DIZZY;
                sprite.setAnimation("dizzy");
                hitByBall = false;
            }
            if (sprite.getAnimation().isPlayedOnce()) {
                playDizzyAnimation = false;
                hitByBall = false;
            }
        } else if (velocity.y < 0) {
            if (currentAction != Action.JUMPING) {
                currentAction = Action.JUMPING;
                sprite.setAnimation("jump");
            }
        } else if (velocity.y > 0) {
            if (currentAction != Action.FALLING) {
                currentAction = Action.FALLING;
                sprite.setAnimation("fall");
            }
        } else if (movement.isMovingLeft() || movement.isMovingRight()) {
            if (currentAction != Action.RUNNING) {
                currentAction = Action.RUNNING;
                sprite.setAnimation("run");
            }
        } else {
            if (currentAction != Action.IDLE) {
                currentAction = Action.IDLE;
                sprite.setAnimation("idle");
            }
        }

        sprite.update();
    }

    /**
     * Handle collisions with the tile map. The method checks future positions to prevent getting stuck in the tiles.
     */
    private void tileCollision() {
        int currentColumn = tileMap.getColumnTile((int) position.x);
        int currentRow = tileMap.getRowTile((int) position.y);
        Vector2 destination = new Vector2(position.x + velocity.x, position.y + velocity.y);
        Vector2 temp = new Vector2(position.x, position.y);

        // Vertical collisions
        checkSurroundingTiles(position.x, destination.y);

        if (velocity.y < 0) {   // Moving up
            if (topLeft || topRight) {  // Collision
                velocity.y = 0;
                temp.y = currentRow * tileMap.getTileSize();    // Move back to free tile
            } else {
                temp.y += velocity.y;
            }
        } else if (velocity.y > 0) {    // Moving down
            if (bottomLeft || bottomRight) {    // Collision
                velocity.y = 0;
                movement.setFalling(false);
                temp.y = (currentRow + 2) * tileMap.getTileSize() - height;   // Move back to free tile
            } else {
                temp.y += velocity.y;
            }
        }

        // Horizontal collisions
        checkSurroundingTiles(destination.x, position.y);

        if (velocity.x < 0) {   // Moving left
            if (topLeft || bottomLeft) {  // Collision
                velocity.x = 0;
                temp.x = currentColumn * tileMap.getTileSize();    // Move back to free tile
            } else {
                temp.x += velocity.x;
            }
        } else if (velocity.x > 0) {    // Moving right
            if (topRight || bottomRight) {    // Collision
                velocity.x = 0;
                temp.x = (currentColumn + 1) * tileMap.getTileSize() - width;   // Move back to free tile
            } else {
                temp.x += velocity.x;
            }
        }

        // Check if player is standing on ground
        if (!movement.isFalling()) {
            checkSurroundingTiles(position.x, position.y + 1); // y + 1 checks 1 pixel below player
            if (!bottomLeft && !bottomRight) {  // Not standing on ground
                movement.setFalling(true);
            }
        }

        // Set x and y
        position.set(temp.x, temp.y);
    }

    @Override
    public void draw() {

        // Map position needs to be added so player is in the right global position
        double mapX = tileMap.getX();
        double mapY = tileMap.getY();

        if (movement.isFacingRight()) {
            Game.renderer.getGraphics2D().drawImage(
                    sprite.getAnimation().getImage(),
                    (int) (mapX + position.x),
                    (int) (mapY + position.y),
                    width,
                    height,
                    null
            );
        } else {
            Game.renderer.getGraphics2D().drawImage(
                    sprite.getAnimation().getImage(),
                    (int) (mapX + position.x + width),
                    (int) (mapY + position.y),
                    -width,
                    height,
                    null
            );
        }
    }

    public PlatformerMovement getMovement() {
        return movement;
    }

    public void setHitByBall(boolean hitByBall) {
        this.hitByBall = hitByBall;
    }
}
