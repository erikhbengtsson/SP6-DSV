package game.gamestates;

import game.Game;
import game.gameobjects.Player;
import game.map.Background;
import game.map.TileMap;

import java.util.ArrayList;

/**
 * Level 1.
 */
public class Level1 extends LevelState {

    public Level1() {
        super();
    }

    @Override
    public void create() {
        coinSound = Game.loader.loadSound("/sound/coin.wav");
        ballSound = Game.loader.loadSound("/sound/bomb.wav");
        jumpSound = Game.loader.loadSound("/sound/jump.wav");
        background = new Background("/backgrounds/trees.png", 0.1);
        coins = new ArrayList<>();
        balls = new ArrayList<>();

        if (Game.difficulty.equals("EASY")) {
            tileMap = new TileMap("/levels/level1easy.txt", "/tiles/grass/", 128, coins, balls);
        } else if (Game.difficulty.equals("NORMAL")) {
            tileMap = new TileMap("/levels/level1normal.txt", "/tiles/grass/", 128, coins, balls);
        } else if (Game.difficulty.equals("HARD")) {
            tileMap = new TileMap("/levels/level1hard.txt", "/tiles/grass/", 128, coins, balls);
        }

        player = new Player(tileMap, 200, 400);
    }
}
