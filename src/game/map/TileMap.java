package game.map;

import engine.Vector2;
import game.Game;
import game.gameobjects.Ball;
import game.gameobjects.Coin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A map built of Tiles. The map is read from a txt-file with ints representing different Tiles. The txt-file also
 * includes initial positions for Balls and Coins represented with a 'b' or 'c'.
 */
public class TileMap {

    // Position
    private Vector2 position;

    // Fixates position inside the map
    private final Vector2 maxPosition;
    private final Vector2 minPosition;

    // Tiles
    private final int tileSize;
    private int[][] map;    // 2d array read from txt-file
    private int numCols;    // Number of tiles on the x-axis
    private int numRows;    // Number of tiles on the y-axis
    private ArrayList<Tile> tiles;

    // Calculates which tiles needs to be drawn
    private int rowOffset;
    private int colOffset;

    // Used to make "camera" follow player with a delay
    private final double tween;

    public TileMap(String textFile, String tileSet, int tileSize, ArrayList<Coin> coins, ArrayList<Ball> balls) {
        this.tileSize = tileSize;
        loadTextFile(textFile, coins, balls);
        loadTiles(tileSet);
        position = new Vector2();
        tween = 0.06;
        maxPosition = new Vector2();
        minPosition = new Vector2(
                Game.renderer.getScreenWidth() - numCols * tileSize,
                Game.renderer.getScreenHeight() - numRows * tileSize
        );
    }

    /**
     * Load information from x txt-file. Tiles are loaded into a 2D array, Balls and Coins into ArrayLists.
     *
     * @param path String
     * @param coins ArrayList<Coin>
     * @param balls ArrayList<Ball>
     */
    private void loadTextFile(String path, ArrayList<Coin> coins, ArrayList<Ball> balls) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            numCols = Integer.parseInt(reader.readLine());
            numRows = Integer.parseInt(reader.readLine());
            map = new int[numRows][numCols];

            for (int row = 0; row < numRows; row++) {
                String line = reader.readLine();
                String[] lineData = line.split(","); // Delimiter between ints/tiles is ,

                for (int col = 0; col < numCols; col++) {
                    switch (lineData[col]) {
                        case "c":     // Add Coins marked with "c" in txt-file. +29 to place coins in center of tiles
                            coins.add(new Coin(this, col * tileSize + 29, row * tileSize + 29));
                            map[row][col] = 0;
                            break;
                        case "b":  // Add Balls marked with "b" in txt-file
                            balls.add(new Ball(this, col * tileSize, row * tileSize));
                            map[row][col] = 0;
                            break;
                        default:     // Add ints representing the Tiles
                            map[row][col] = Integer.parseInt(lineData[col]);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the tiles used in the map into an ArrayList.
     *
     * @param path String with the location of the tile images
     */
    private void loadTiles(String path) {
        tiles = new ArrayList<>();

        for (int i = 0; i < 17; i++) {

            // Separate between tiles that are blocked and not
            if (i == 0) {
                tiles.add(new Tile(Game.loader.loadImage(path + i + ".png"), false));
            } else {
                tiles.add(new Tile(Game.loader.loadImage(path + i + ".png"), true));
            }
        }
    }

    /**
     * Draw the TileMap by looping through 2D array of ints read from the txt-file. Then draw the image of the Tile
     * the int represents. Offset is used to only draw Tiles that are on the screen.
     */
    public void draw() {

        // Number of tiles on screen, + 2 is needed to draw correctly in borders while moving
        int numRowsToDraw = Game.renderer.getScreenHeight() / tileSize + 2;
        int numColsToDraw = Game.renderer.getScreenWidth() / tileSize + 2;

        for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
            if (row >= numRows)
                break;

            for (int col = colOffset; col < colOffset + numColsToDraw; col++) {
                if (col >= numCols)
                    break;

                int tile = map[row][col];   // int from the txt-file

                Game.renderer.getGraphics2D().drawImage(
                        tiles.get(tile).getImage(),
                        (int) position.x + col * tileSize,
                        (int) position.y + row * tileSize,
                        null
                );
            }
        }
    }

    /**
     * Set the position of the tile map based on the players position.
     *
     * @param x double
     * @param y double
     */
    public void setPosition(double x, double y) {
        position.add((x - position.x) * tween, (y - position.y) * tween);   // This follows the player with a delay

        fixBounds();

        // Update offset used in the draw method
        colOffset = (int) -position.x / tileSize;
        rowOffset = (int) -position.y / tileSize;
    }

    /**
     * Keep the position within the TileMap so empty space around it isn't shown.
     */
    private void fixBounds() {
        if (position.x < minPosition.x)
            position.x = minPosition.x;
        if (position.y < minPosition.y)
            position.y = minPosition.y;
        if (position.x > maxPosition.x)
            position.x = maxPosition.x;
        if (position.y > maxPosition.y)
            position.y = maxPosition.y;
    }

    public double getX() {
        return position.x;
    }

    public double getY() {
        return position.y;
    }

    public int getColumnTile(int x) {
        return x / tileSize;
    }

    public int getRowTile(int y) {
        return y / tileSize;
    }

    public boolean isBlocked(int row, int col) {
        int tile = map[row][col];
        return tiles.get(tile).isBlocked();
    }

    public int getTileSize() {
        return tileSize;
    }

    public Vector2 getPosition() {
        return position;
    }
}
