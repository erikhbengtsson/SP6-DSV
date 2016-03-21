package game.gamestates;

import engine.GameState;
import engine.SoundClip;
import engine.Vector2;
import engine.subsystems.PhysicsManager;
import game.Game;
import game.gameobjects.Ball;
import game.gameobjects.Coin;
import game.gameobjects.Player;
import game.map.Background;
import game.map.TileMap;

import java.awt.*;
import java.util.ArrayList;

/**
 * Abstract class with variables and methods needed by all levels.
 */
public abstract class LevelState extends GameState {
    protected Player player;
    protected ArrayList<Coin> coins;
    protected ArrayList<Ball> balls;
    protected TileMap tileMap;
    protected SoundClip coinSound;
    protected SoundClip ballSound;
    protected SoundClip jumpSound;
    protected Background background;

    public LevelState() {
        super();
    }

    @Override
    public abstract void create();

    @Override
    public void update(double delta) {
        handleInput();
        player.update(delta);
        updateCoins(delta);
        updateBalls(delta);
        playerCoinCollision();
        playerBallCollision();
        ballCollision();
        levelComplete();
        setMapPosition();
        background.update(tileMap.getPosition());
    }

    /**
     * Handle keyboard input.
     */
    private void handleInput() {
        if (Game.inputManager.isKeyPressed(Game.getJumpKey().getName())) {
            player.getMovement().setJumping(true);
            if (!player.getMovement().isFalling()) {
                jumpSound.play();
            }
        } else {
            player.getMovement().setJumping(false);
        }
        if (Game.inputManager.isKeyPressed(Game.getLeftKey().getName())) {
            player.getMovement().setMovingLeft(true);
        } else {
            player.getMovement().setMovingLeft(false);
        }
        if (Game.inputManager.isKeyPressed(Game.getRightKey().getName())) {
            player.getMovement().setMovingRight(true);
        } else {
            player.getMovement().setMovingRight(false);
        }
        if (Game.inputManager.isKeyPressed(Game.getQuitKey().getName())) {
            Game.loader.setGameState("main");
            Game.lives = 9;
            Game.score = 0;
        }
    }

    /**
     * Update coins.
     *
     * @param delta double
     */
    private void updateCoins(double delta) {
        for (int i = 0; i < coins.size(); i++) {
            coins.get(i).update(delta);
        }
    }

    /**
     * Update balls.
     *
     * @param delta double
     */
    private void updateBalls(double delta) {
        for (int i = 0; i < balls.size(); i++) {
            balls.get(i).update(delta);
        }
    }

    /**
     * Handle collisions between player and coins.
     */
    private void playerCoinCollision() {
        for (int i = 0; i < coins.size(); i++) {
            if (PhysicsManager.circleRectangleCollision(player, coins.get(i))) {
                coinSound.play();
                coins.remove(i);
                Game.score += 100;
                i--;
            }
        }
    }

    /**
     * Handle collisions between two balls. When colliding the balls get the velocity of the other ball.
     */
    private void ballCollision() {
        for (int i = 0; i < balls.size() - 1; i++) {
            Ball b1 = balls.get(i);
            for (int j = i + 1; j < balls.size(); j++) {
                Ball b2 = balls.get(j);
                if (!b1.isExploding() && !b2.isExploding() && PhysicsManager.circleCircleCollision(b1, b2)) {
                    Vector2 v = b1.getVelocity();
                    b1.setVelocity(b2.getVelocity());
                    b2.setVelocity(v);
                }
            }
        }
    }

    /**
     * Handle collisions between player and ball. The player loses a life and the ball explodes.
     */
    private void playerBallCollision() {
        for (Ball ball : balls) {
            if ((!ball.isExploding()) && PhysicsManager.circleRectangleCollision(player, ball)) {
                ballSound.play();
                ball.setExploding(true);    // This starts exploding animation and removes ball
                player.setHitByBall(true);  // This starts dizzy animation
                Game.lives--;
                Game.score -= 100;
            }
        }
    }

    /**
     * Check if level is completed by collecting all coins or losing all lives.
     */
    private void levelComplete() {
        if (coins.size() == 0) {
            if (Game.loader.getCurrentGameState() instanceof Level1) {
                Game.loader.setGameState("level2");
            } else if (Game.loader.getCurrentGameState() instanceof Level2) {
                Game.loader.setGameState("level3");
            } else if (Game.loader.getCurrentGameState() instanceof Level3) {
                HighScore.checkHighScore();
                Game.loader.setGameState("highScore");
                Game.lives = 9; // Reset lives
            }
        } else if (Game.lives == 0) {
            Game.loader.setGameState("main");
            Game.score = 0; // Reset score
            Game.lives = 9; // Reset lives
        }
    }

    /**
     * Change the position of the TileMap so it follows player.
     */
    private void setMapPosition() {
        tileMap.setPosition(
                (Game.renderer.getScreenWidth() / 2 - player.getX() - (player.getWidth() / 2)),
                (Game.renderer.getScreenHeight() / 2 - player.getY() - 150)
        );
    }

    @Override
    public void draw() {
        background.draw();
        tileMap.draw();
        drawCoins();
        drawBalls();
        player.draw();
        drawStats();
    }

    /**
     * Draw coins that are on the screen.
     */
    private void drawCoins() {
        for (Coin coin : coins) {
            if (coin.isOnScreen()) {
                coin.draw();
            }
        }
    }

    /**
     * Draw balls that are on the screen.
     */
    private void drawBalls() {
        for (Ball ball : balls) {
            if (ball.isOnScreen()) {
                ball.draw();
            }
        }
    }

    /**
     * Draw score and number of lives left.
     */
    private void drawStats() {
        Game.renderer.getGraphics2D().setColor(Color.black);
        Game.renderer.getGraphics2D().setFont(new Font("Arial", Font.BOLD, 35));
        Game.renderer.getGraphics2D().drawString("LIVES: " + Game.lives, 50, 100);
        Game.renderer.getGraphics2D().drawString("SCORE: " + Game.score, 50, 150);
    }

    /**
     * Remove a ball from the game when it collides with player.
     *
     * @param ball Ball
     */
    public void removeBall(Ball ball) {
        balls.remove(ball);
    }
}
