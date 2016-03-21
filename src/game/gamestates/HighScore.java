package game.gamestates;

import engine.GameState;
import engine.MenuButton;
import game.Game;
import game.map.Background;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;

/**
 * Menu showing high scores.
 */
public class HighScore extends GameState {
    private Background background;
    private MenuButton back;

    @Override
    public void create() {
        background = new Background("/backgrounds/trees.png", 1);
        back = new MenuButton(Game.renderer.getScreenWidth() / 2 - 150, Game.renderer.getScreenHeight() / 2 + 235,
                300, 131, Color.black, Color.white, "BACK", new Font("Arial", Font.BOLD, 40));
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
        if (back.isClicked(e)) {
            Game.loader.setGameState("main");
            Game.score = 0;
        }
    }

    @Override
    public void draw() {
        background.draw();

        // Title
        Game.renderer.getGraphics2D().setColor(Color.black);
        Game.renderer.getGraphics2D().setFont(new Font("Arial", Font.BOLD, 70));
        Game.renderer.getGraphics2D().drawString("HIGH SCORE", Game.renderer.getScreenWidth() / 2 - 250, 100);

        // Easy high score
        Game.renderer.getGraphics2D().setFont(new Font("Arial", Font.BOLD, 40));
        Game.renderer.getGraphics2D().drawString("EASY", Game.renderer.getScreenWidth() / 2 - 250, 200);
        Game.renderer.getGraphics2D().drawString(Game.highScoreNameEasy + ", " + Game.highScoreEasy + " points",
                Game.renderer.getScreenWidth() / 2 - 250, 250);

        // Normal high score
        Game.renderer.getGraphics2D().drawString("NORMAL", Game.renderer.getScreenWidth() / 2 - 250, 350);
        Game.renderer.getGraphics2D().drawString(Game.highScoreNameNormal + ", " + Game.highScoreNormal + " points",
                Game.renderer.getScreenWidth() / 2 - 250, 400);

        // Hard high score
        Game.renderer.getGraphics2D().drawString("HARD", Game.renderer.getScreenWidth() / 2 - 250, 500);
        Game.renderer.getGraphics2D().drawString(Game.highScoreNameHard + ", " + Game.highScoreHard + " points",
                Game.renderer.getScreenWidth() / 2 - 250, 550);

        // Back button
        Game.renderer.getGraphics2D().setStroke(new BasicStroke(3));
        back.draw();
    }

    /**
     * Check if a new high score has been set.
     */
    public static void checkHighScore() {
        loadHighScore();
        newHighScore();
    }

    /**
     * Loads high score from file.
     */
    private static void loadHighScore() {
        if (Game.highScoreEasy == -1) {
            String highScoreInfo = getHighScore();  // String with both name and score for all difficulties
            if (highScoreInfo != null) {
                String[] splitHighScoreInfo = highScoreInfo.split(":"); // Delimiter between name an score is :
                Game.highScoreNameEasy = splitHighScoreInfo[0];
                Game.highScoreEasy = Integer.parseInt(splitHighScoreInfo[1]);
                Game.highScoreNameNormal = splitHighScoreInfo[2];
                Game.highScoreNormal = Integer.parseInt(splitHighScoreInfo[3]);
                Game.highScoreNameHard = splitHighScoreInfo[4];
                Game.highScoreHard = Integer.parseInt(splitHighScoreInfo[5]);
            }
        }
    }

    /**
     * Read high score from file.
     *
     * @return String containing player name and high score for all three difficulty levels
     */
    private static String getHighScore() {
        try {
            FileReader fileReader = new FileReader("highScore.dat");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            return bufferedReader.readLine();

        } catch (FileNotFoundException e) {
            return "Not completed:0:Not completed:0:Not completed:0"; // There is no file and no high score
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Not completed:0:Not completed:0:Not completed:0";
    }

    /**
     * Checks if a new high score has been set.
     */
    private static void newHighScore() {
        if (Game.difficulty.equals("EASY") && Game.score > Game.highScoreEasy) {
            Game.highScoreEasy = Game.score;
            Game.highScoreNameEasy = JOptionPane.showInternalInputDialog(
                    Game.renderer.getContentPane(),
                    "Enter your name:",
                    "High Score",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else if (Game.difficulty.equals("NORMAL") && Game.score > Game.highScoreNormal) {
            Game.highScoreNormal = Game.score;
            Game.highScoreNameNormal = JOptionPane.showInternalInputDialog(
                    Game.renderer.getContentPane(),
                    "Enter your name:",
                    "High Score",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else if (Game.difficulty.equals("HARD") && Game.score > Game.highScoreHard) {
            Game.highScoreHard = Game.score;
            Game.highScoreNameHard = JOptionPane.showInternalInputDialog(
                    Game.renderer.getContentPane(),
                    "Enter your name:",
                    "High Score",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
        setHighScore(Game.highScoreNameEasy + ":" + Game.highScoreEasy + ":" + Game.highScoreNameNormal + ":"
                + Game.highScoreNormal + ":" + Game.highScoreNameHard + ":" + Game.highScoreHard);
    }

    /**
     * Write high score to file.
     *
     * @param highScoreInfo String containing player name and high score
     */
    private static void setHighScore(String highScoreInfo) {
        File highScoreFile = new File("highScore.dat");
        try {
            if (!highScoreFile.exists()) {
                highScoreFile.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(highScoreFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(highScoreInfo);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
