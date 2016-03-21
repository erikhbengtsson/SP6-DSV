package game.gameobjects;

import engine.Animation;
import engine.GameObject;
import engine.Vector2;
import game.Game;
import game.gamestates.LevelState;
import game.map.TileMap;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Balls bounces around and explodes when they collide with the player.
 */
public class Ball extends GameObject {

    private enum Action {
        BOUNCING,
        EXPLODING
    }

    private Action currentAction;
    private double maxFallingSpeed;
    private Vector2 energyLoss;
    private boolean exploding;

    public Ball(TileMap tileMap, double x, double y) {
        super(tileMap, x, y);
    }

    @Override
    protected void create() {
        width = 70;
        height = 70;
        maxFallingSpeed = 15.0;
        energyLoss = new Vector2(1, 1); // Make values less then 1 if ball should stop bouncing eventually

        // Animations
        BufferedImage[] ballSprites = new BufferedImage[1];
        ballSprites[0] = Game.loader.loadImage("/ball/ball.png");
        BufferedImage[] explotionSprites = Game.loader.loadSpriteSheet("/ball/explosion.png", 3, 8, 21);
        sprite.addAnimation("ball", new Animation(ballSprites, 1000));
        sprite.addAnimation("explosion", new Animation(explotionSprites, 100));
        sprite.setAnimation("ball");
        currentAction = Action.BOUNCING;

        setRandomDirection();
    }

    /**
     * Set a random horizontal direction of the Ball.
     */
    private void setRandomDirection() {
        Random random = new Random();
        int xDirection = random.nextInt(10);
        boolean negative = random.nextBoolean();

        // If bool negative is true the Ball is moving to the left
        if (negative) {
            xDirection = -xDirection;
        }

        setDirection(xDirection, 0);    // y-velocity is zero and only effected by gravity
    }

    @Override
    public void update(double delta) {
        if (exploding) {
            explosionAnimation();
        } else {
            velocity.y += Game.physicsManager.getGravity() * delta; // Ball is moved down by gravity

            if (velocity.y > maxFallingSpeed) {
                velocity.y = maxFallingSpeed;
            }

            tileCollision();
        }

        sprite.update();
    }

    /**
     * Explosion animation is used when player and ball collides. The ball is then removed from the game.
     */
    private void explosionAnimation() {
        if (currentAction != Action.EXPLODING) {
            currentAction = Action.EXPLODING;
            sprite.setAnimation("explosion");
            velocity = new Vector2();
            setPosition(position.x - 35, position.y - 35);
            width = 140;
            height = 140;
        }

        // When the full animation is played once the Ball is removed
        if (sprite.getAnimation().isPlayedOnce()) {
            if (Game.loader.getCurrentGameState() instanceof LevelState) {
                ((LevelState) Game.loader.getCurrentGameState()).removeBall(this);
            }
        }
    }

    /**
     * Handle collisions with the tile map.
     */
    private void tileCollision() {
        double destinationX = position.x + velocity.x;
        double destinationY = position.y + velocity.y;
        double tempX = position.x;
        double tempY = position.y;

        // Vertical collisions
        checkSurroundingTiles(position.x, destinationY);

        if (velocity.y < 0) {   // Moving up
            if (topLeft || topRight) {  // Collision
                velocity.y = Math.abs(velocity.y);
            } else {
                tempY += velocity.y;
            }
        } else if (velocity.y > 0) {    // Moving down
            if (bottomLeft || bottomRight) {    // Collision
                velocity.y = -velocity.y * energyLoss.y;
                velocity.x *= energyLoss.x;
                if (Math.abs(velocity.x) < 0.8) {
                    velocity.x = 0;
                }
            } else {
                tempY += velocity.y;
            }
        }

        // Horizontal collisions
        checkSurroundingTiles(destinationX, position.y);

        if (velocity.x < 0) {   // Moving left
            if (topLeft || bottomLeft) {  // Collision
                velocity.x = Math.abs(velocity.x) * energyLoss.x;
            } else {
                tempX += velocity.x;
            }
        } else if (velocity.x > 0) {    // Moving right
            if (topRight || bottomRight) {    // Collision
                velocity.x = -velocity.x * energyLoss.x;
            } else {
                tempX += velocity.x;
            }
        }

        // Set x and y
        position.set(tempX, tempY);
    }

    @Override
    public void draw() {
        double mapX = tileMap.getX();
        double mapY = tileMap.getY();


        Game.renderer.getGraphics2D().drawImage(
                sprite.getAnimation().getImage(),
                (int) (mapX + position.x),
                (int) (mapY + position.y),
                width,
                height,
                null
        );
    }

    public boolean isExploding() {
        return exploding;
    }

    public void setExploding(boolean exploding) {
        this.exploding = exploding;
    }
}
