package game.gamestates;

import game.Game;
import game.gameobjects.Player;
import game.map.Background;
import game.map.TileMap;

import java.util.ArrayList;

/**
 * Level 2.
 */
public class Level2 extends LevelState {

    public Level2() {
        super();
    }

    @Override
    public void create() {
        coinSound = Game.loader.loadSound("/sound/coin.wav");
        ballSound = Game.loader.loadSound("/sound/bomb.wav");
        jumpSound = Game.loader.loadSound("/sound/jump.wav");
        background = new Background("/backgrounds/desert.png", 0.1);
        coins = new ArrayList<>();
        balls = new ArrayList<>();

        if (Game.difficulty.equals("EASY")) {
            tileMap = new TileMap("/levels/level2easy.txt", "/tiles/desert/", 128, coins, balls);
        } else if (Game.difficulty.equals("NORMAL")) {
            tileMap = new TileMap("/levels/level2normal.txt", "/tiles/desert/", 128, coins, balls);
        } else if (Game.difficulty.equals("HARD")) {
            tileMap = new TileMap("/levels/level2hard.txt", "/tiles/desert/", 128, coins, balls);
        }

        player = new Player(tileMap, 200, 400);
    }
}
