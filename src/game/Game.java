package game;

import engine.GameEngine;
import engine.SoundClip;
import engine.subsystems.Key;
import game.gamestates.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Main class of the game. Updates and draws the current GameState and sends keyboard and mouse input to InputManager.
 */
public class Game extends GameEngine {
    public static int score = 0;
    public static int lives = 9;
    public static int highScoreEasy = -1;
    public static int highScoreNormal = -1;
    public static int highScoreHard = -1;
    public static String highScoreNameEasy = "";
    public static String highScoreNameNormal = "";
    public static String highScoreNameHard = "";
    public static String difficulty = "EASY";   // Default difficulty

    // Keyboard keys used in the game
    private static Key jumpKey, leftKey, rightKey, quitKey;

    // Constructor for windowed mode
    public Game(int screenWidth, int screenHeight) {
        super(screenWidth, screenHeight);
    }

    // Constructor for fullscreen mode
    public Game() {
        super();
    }

    @Override
    protected void create() {

        // Add GameStates and set MainMenu to initial state
        loader.addGameState("main", new MainMenu());
        loader.addGameState("settings", new SettingsMenu());
        loader.addGameState("level1", new Level1());
        loader.addGameState("level2", new Level2());
        loader.addGameState("level3", new Level3());
        loader.addGameState("highScore", new HighScore());
        loader.setGameState("main");

        physicsManager.setGravity(0.2);
        SoundClip music = Game.loader.loadSound("/sound/music.wav");    // Background music used in all GameStates
        music.loop();
        addKeys();
    }

    /**
     * Add default keys and mouse buttons used in the game.
     */
    private void addKeys() {
        Game.inputManager.addMouseButton("MouseLeft", MouseEvent.BUTTON1);

        jumpKey = new Key("Up arrow", KeyEvent.VK_UP);
        Game.inputManager.addKey(jumpKey);

        leftKey = new Key("Left arrow", KeyEvent.VK_LEFT);
        Game.inputManager.addKey(leftKey);

        rightKey = new Key("Right arrow", KeyEvent.VK_RIGHT);
        Game.inputManager.addKey(rightKey);

        quitKey = new Key("Escape", KeyEvent.VK_ESCAPE);
        Game.inputManager.addKey(quitKey);
    }

    @Override
    protected void update(double delta) {
        Game.loader.getCurrentGameState().update(delta);
    }

    @Override
    protected void draw() {
        renderer.getGraphics2D().setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        );
        Game.loader.getCurrentGameState().draw();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        inputManager.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        inputManager.keyReleased(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        inputManager.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        inputManager.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        inputManager.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (Game.inputManager != null) {
            inputManager.mouseMoved(e);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Not used
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Not used
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Not used
    }

    // Getters and setters used for changing keyboard controllers

    public static Key getJumpKey() {
        return jumpKey;
    }

    public static void setJumpKey(Key jumpKey) {
        Game.jumpKey = jumpKey;
    }

    public static Key getLeftKey() {
        return leftKey;
    }

    public static void setLeftKey(Key leftKey) {
        Game.leftKey = leftKey;
    }

    public static Key getRightKey() {
        return rightKey;
    }

    public static void setRightKey(Key rightKey) {
        Game.rightKey = rightKey;
    }

    public static Key getQuitKey() {
        return quitKey;
    }

    public static void setQuitKey(Key quitKey) {
        Game.quitKey = quitKey;
    }
}
