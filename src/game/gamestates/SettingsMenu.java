package game.gamestates;

import engine.GameState;
import engine.MenuButton;
import engine.subsystems.Key;
import game.Game;
import game.map.Background;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Menu where difficulty level and controllers can be changed.
 */
public class SettingsMenu extends GameState {
    private Background background;
    private MenuButton easy, normal, hard, back, jump, left, right, quit;

    @Override
    public void create() {
        background = new Background("/backgrounds/desert.png", 1);

        easy = new MenuButton(Game.renderer.getScreenWidth() / 2 - 450, 150, 300, 131, Color.black, Color.white,
                "EASY", new Font("Arial", Font.BOLD, 40));

        normal = new MenuButton(Game.renderer.getScreenWidth() / 2 - 450, 300, 300, 131, Color.black, Color.white,
                "NORMAL", new Font("Arial", Font.BOLD, 40));

        hard = new MenuButton(Game.renderer.getScreenWidth() / 2 - 450, 450, 300, 131, Color.black, Color.white,
                "HARD", new Font("Arial", Font.BOLD, 40));

        back = new MenuButton(Game.renderer.getScreenWidth() / 2 - 150, 650,
                300, 131, Color.black, Color.white, "BACK", new Font("Arial", Font.BOLD, 40));

        jump = new MenuButton(Game.renderer.getScreenWidth() / 2 + 400, 150,
                120, 60, Color.black, Color.white, "CHANGE", new Font("Arial", Font.BOLD, 20));

        left = new MenuButton(Game.renderer.getScreenWidth() / 2 + 400, 250,
                120, 60, Color.black, Color.white, "CHANGE", new Font("Arial", Font.BOLD, 20));

        right = new MenuButton(Game.renderer.getScreenWidth() / 2 + 400, 350,
                120, 60, Color.black, Color.white, "CHANGE", new Font("Arial", Font.BOLD, 20));

        quit = new MenuButton(Game.renderer.getScreenWidth() / 2 + 400, 450,
                120, 60, Color.black, Color.white, "CHANGE", new Font("Arial", Font.BOLD, 20));
    }

    @Override
    public void update(double delta) {
        if (Game.inputManager.isMouseClicked("MouseLeft")) {
            checkMouseClick(Game.inputManager.getLastClick());
        }
    }

    /**
     * Called when the left mouse button is clicked to se if a button was clicked.
     *
     * @param e MouseEvent
     */
    private void checkMouseClick(MouseEvent e) {
        changeDifficulty(e);
        changeControllers(e);

        if (back.isClicked(e)) {
            Game.loader.setGameState("main");
        }
    }

    /**
     * Check if difficulty level has been changed.
     *
     * @param e MouseEvent
     */
    private void changeDifficulty(MouseEvent e) {
        if (easy.isClicked(e)) {
            Game.difficulty = "EASY";
        }
        if (normal.isClicked(e)) {
            Game.difficulty = "NORMAL";
        }
        if (hard.isClicked(e)) {
            Game.difficulty = "HARD";
        }
    }

    /**
     * Check if controllers has been changed.
     *
     * @param e MouseEvent
     */
    private void changeControllers(MouseEvent e) {
        if (jump.isClicked(e)) {
            Object input = JOptionPane.showInternalInputDialog(Game.renderer.getContentPane(), "Choose key", null,
                    JOptionPane.INFORMATION_MESSAGE, null, Game.inputManager.getKeys(), KeyEvent.VK_SPACE);

            if (input != null) {
                Game.setJumpKey((Key) input);
                Game.inputManager.addKey((Key) input);
            }
        }
        if (left.isClicked(e)) {
            Object input = JOptionPane.showInternalInputDialog(Game.renderer.getContentPane(), "Choose key", null,
                    JOptionPane.INFORMATION_MESSAGE, null, Game.inputManager.getKeys(), KeyEvent.VK_SPACE);

            if (input != null) {
                Game.setLeftKey((Key) input);
                Game.inputManager.addKey((Key) input);
            }
        }
        if (right.isClicked(e)) {
            Object input = JOptionPane.showInternalInputDialog(Game.renderer.getContentPane(), "Choose key", null,
                    JOptionPane.INFORMATION_MESSAGE, null, Game.inputManager.getKeys(), KeyEvent.VK_SPACE);

            if (input != null) {
                Game.setRightKey((Key) input);
                Game.inputManager.addKey((Key) input);
            }
        }
        if (quit.isClicked(e)) {
            Object input = JOptionPane.showInternalInputDialog(Game.renderer.getContentPane(), "Choose key", null,
                    JOptionPane.INFORMATION_MESSAGE, null, Game.inputManager.getKeys(), KeyEvent.VK_SPACE);

            if (input != null) {
                Game.setQuitKey((Key) input);
                Game.inputManager.addKey((Key) input);
            }
        }
    }

    @Override
    public void draw() {
        background.draw();

        Game.renderer.getGraphics2D().setColor(Color.black);
        Game.renderer.getGraphics2D().setFont(new Font("Arial", Font.BOLD, 40));
        Game.renderer.getGraphics2D().drawString("DIFFICULTY: " + Game.difficulty, Game.renderer.getScreenWidth() / 2 - 450, 100);
        Game.renderer.getGraphics2D().drawString("CONTROLLERS ", Game.renderer.getScreenWidth() / 2, 100);

        Game.renderer.getGraphics2D().setStroke(new BasicStroke(3));
        easy.draw();
        normal.draw();
        hard.draw();
        back.draw();

        Game.renderer.getGraphics2D().drawString("JUMP: " + Game.getJumpKey().getName(),
                Game.renderer.getScreenWidth() / 2, 200);
        jump.draw();

        Game.renderer.getGraphics2D().setFont(new Font("Arial", Font.BOLD, 40));
        Game.renderer.getGraphics2D().drawString("LEFT: " + Game.getLeftKey().getName(),
                Game.renderer.getScreenWidth() / 2, 300);
        left.draw();

        Game.renderer.getGraphics2D().setFont(new Font("Arial", Font.BOLD, 40));
        Game.renderer.getGraphics2D().drawString("RIGHT: " + Game.getRightKey().getName(),
                Game.renderer.getScreenWidth() / 2, 400);
        right.draw();

        Game.renderer.getGraphics2D().setFont(new Font("Arial", Font.BOLD, 40));
        Game.renderer.getGraphics2D().drawString("QUIT: " + Game.getQuitKey().getName(),
                Game.renderer.getScreenWidth() / 2, 500);
        quit.draw();
    }
}
