package game.gamestates;

import engine.GameState;
import engine.MenuButton;
import game.Game;
import game.map.Background;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * First menu shown when starting the game.
 */
public class MainMenu extends GameState {
    private Background background;
    private MenuButton play, settings, quit;

    @Override
    public void create() {
        background = new Background("/backgrounds/winter.png", 1);

        play = new MenuButton(Game.renderer.getScreenWidth() / 2 - 150, Game.renderer.getScreenHeight() / 2 - 265,
                300, 131, Color.black, Color.white, "PLAY", new Font("Arial", Font.BOLD, 40));

        settings = new MenuButton(Game.renderer.getScreenWidth() / 2 - 150, Game.renderer.getScreenHeight() / 2 - 65,
                300, 131, Color.black, Color.white, "SETTINGS", new Font("Arial", Font.BOLD, 40));

        quit = new MenuButton(Game.renderer.getScreenWidth() / 2 - 150, Game.renderer.getScreenHeight() / 2 + 135,
                300, 131, Color.black, Color.white, "QUIT", new Font("Arial", Font.BOLD, 40));
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
        if (play.isClicked(e)) {
            Game.loader.setGameState("level1");
        }
        if (settings.isClicked(e)) {
            Game.loader.setGameState("settings");
        }
        if (quit.isClicked(e)) {
            System.exit(0);
        }
    }

    @Override
    public void draw() {
        background.draw();

        // Images
        Game.renderer.getGraphics2D().drawImage(
                Game.loader.loadImage("/player/idle/0.png"),
                50,
                Game.renderer.getScreenHeight() - 410,
                304,
                410,
                null
        );
        Game.renderer.getGraphics2D().drawImage(
                Game.loader.loadImage("/ball/ball.png"),
                Game.renderer.getScreenWidth() - 290,
                Game.renderer.getScreenHeight() / 3,
                190,
                190,
                null
        );
        Game.renderer.getGraphics2D().drawImage(
                Game.loader.loadImage("/coin/0.png"),
                130,
                Game.renderer.getScreenHeight() / 8,
                180,
                180,
                null
        );

        // Buttons
        Game.renderer.getGraphics2D().setStroke(new BasicStroke(3));    // Increases thickness of button borders
        play.draw();
        settings.draw();
        quit.draw();
    }
}
