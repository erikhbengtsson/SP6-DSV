package game.gamestates;

import game.Game;
import game.gameobjects.Player;
import game.map.Background;
import game.map.TileMap;

import java.util.ArrayList;

/**
 * Level 3.
 */
public class Level3 extends LevelState {

    public Level3() {
        super();
    }

    @Override
    public void create() {
        coinSound = Game.loader.loadSound("/sound/coin.wav");
        ballSound = Game.loader.loadSound("/sound/bomb.wav");
        jumpSound = Game.loader.loadSound("/sound/jump.wav");
        background = new Background("/backgrounds/winter.png", 0.1);
        coins = new ArrayList<>();
        balls = new ArrayList<>();

        if (Game.difficulty.equals("EASY")) {
            tileMap = new TileMap("/levels/level3easy.txt", "/tiles/winter/", 128, coins, balls);
        } else if (Game.difficulty.equals("NORMAL")) {
            tileMap = new TileMap("/levels/level3normal.txt", "/tiles/winter/", 128, coins, balls);
        } else if (Game.difficulty.equals("HARD")) {
            tileMap = new TileMap("/levels/level3hard.txt", "/tiles/winter/", 128, coins, balls);
        }

        player = new Player(tileMap, 200, 400);
        player.getMovement().setDeceleration(0.05); // Slower deceleration because of ice/snow
    }
}
