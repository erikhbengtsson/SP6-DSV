package engine.subsystems;

import engine.GameState;
import engine.SoundClip;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/**
 * Singleton class for loading images and sounds. Also used for storing and changing GameStates.
 */
public class Loader {
    private static Loader loader = new Loader();
    private HashMap<String, BufferedImage> images;
    private SoundManager soundManager;
    private HashMap<String, GameState> gameStates;
    private GameState currentGameState;

    private Loader() {
        images = new HashMap<>();
        soundManager = SoundManager.getInstance();
        gameStates = new HashMap<>();
    }

    /**
     * Returns the instance of this class.
     *
     * @return Loader
     */
    public static Loader getInstance() {
        return loader;
    }

    /**
     * Add GameState to Loader.
     */
    public void addGameState(String name, GameState gameState) {
        gameStates.put(name, gameState);
    }

    /**
     * Loads a GameState and calls it's create method.
     *
     * @param name String
     */
    public void setGameState(String name) {
        currentGameState = gameStates.get(name);
        currentGameState.create();
    }

    /**
     * Get the current GameState. This is used to call the current GameStates update and draw methods in GameEngine.
     *
     * @return GameState
     */
    public GameState getCurrentGameState() {
        return currentGameState;
    }

    /**
     * Loads a BufferedImage from file. If file is already loaded, get it from HashMap images.
     *
     * @param path String with the filepath
     * @return BufferedImage or null if the filepath is wrong
     */
    public BufferedImage loadImage(String path) {
        try {
            if (images.containsKey(path)) {
                return images.get(path);
            } else {
                images.put(path, ImageIO.read(getClass().getResourceAsStream(path)));
                return images.get(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Splits a sprite sheet into single images and loads it in an array.
     * The array can be used for animations or to draw a tilemap.
     *
     * @param path String
     * @param rows int
     * @param columns int
     * @param numImages int
     * @return BufferImage[]
     */
    public BufferedImage[] loadSpriteSheet(String path, int rows, int columns, int numImages) {

        // Load sprite sheet
        BufferedImage spriteSheet = loadImage(path);
        int width = spriteSheet.getWidth() / columns;   // Width of the subimages
        int height = spriteSheet.getHeight() / rows;    // Height of the subimages

        // Put single images from sprite sheet in array
        BufferedImage subimage;
        BufferedImage[] result = new BufferedImage[numImages];
        int index = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                subimage = spriteSheet.getSubimage(col * width, row * height, width, height);
                result[index] = subimage;
                index++;
                if (index == numImages) {
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * Splits a sprite sheet into single images and loads it in a 2D array.
     * 2D array can be easier then array when drawing tile maps.
     *
     * @param path String
     * @param rows int
     * @param columns int
     * @return BufferedImage[][]
     */
    public BufferedImage[][] loadSpriteSheetInMultiArray(String path, int rows, int columns) {

        // Load sprite sheet
        BufferedImage spriteSheet = loadImage(path);
        int width = spriteSheet.getWidth() / columns;
        int height = spriteSheet.getHeight() / rows;

        // Put single images from sprite sheet in array
        BufferedImage subimage;
        BufferedImage[][] result = new BufferedImage[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                subimage = spriteSheet.getSubimage(col * width, row * height, width, height);
                result[row][col] = subimage;
            }
        }
        return result;
    }

    /**
     * Loads a SoundClip from file. If file is already loaded, get it from HashMap sounds.
     *
     * @param path String with the filepath
     * @return SoundClip
     */
    public SoundClip loadSound(String path) {
        if (soundManager.containsSound(path)) {
            return soundManager.getSound(path);
        } else {
            soundManager.addSound(path);
            return soundManager.getSound(path);
        }
    }
}
