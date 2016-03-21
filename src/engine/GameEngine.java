package engine;

import engine.subsystems.InputManager;
import engine.subsystems.Loader;
import engine.subsystems.PhysicsManager;
import engine.subsystems.Renderer;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;

/**
 * Main class of the game engine. This is an abstract class that should be extended by the main class of the game.
 * Contains the game loop and all subsystems except the SoundManager which is a part of the Loader.
 * Key and mouse events are sent from this class to the InputManager.
 */
public abstract class GameEngine implements Runnable, KeyListener, MouseListener, MouseMotionListener {
    protected final int FPS = 60;
    protected Thread thread;
    protected volatile boolean running;

    // Subsystems
    public static Renderer renderer;
    public static PhysicsManager physicsManager;
    public static Loader loader;
    public static InputManager inputManager;

    /**
     * Create a game with fixed width and height of the window.
     *
     * @param screenWidth int
     * @param screenHeight int
     */
    public GameEngine(int screenWidth, int screenHeight) {
        renderer = new Renderer(screenWidth, screenHeight);
        init();
    }

    /**
     * Create a game in fullscreen window.
     */
    public GameEngine() {
        renderer = new Renderer();
        init();
    }

    /**
     * Add listeners to renderer and initialize subsystems that are the same for fullscreen and windowed.
     */
    private void init() {
        renderer.addKeyListener(this);
        renderer.addMouseListener(this);
        renderer.addMouseMotionListener(this);
        physicsManager = PhysicsManager.getInstance();
        loader = Loader.getInstance();
        inputManager = InputManager.getInstance();
    }

    /**
     * Initialize variables. Called once when the game loop starts.
     */
    protected abstract void create();

    /**
     * Update the game.
     */
    protected abstract void update(double delta);

    /**
     * Draw the game.
     */
    protected abstract void draw();

    /**
     * Start the game loop.
     */
    public synchronized void start() {
        if (thread == null || !running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * Stop the game loop.
     */
    public synchronized void stop() {
        running = false;
    }

    /**
     * Game loop.
     */
    @Override
    public void run() {
        long prevStartTime = System.nanoTime();
        long targetTime = 1000000000 / FPS; // Target time in nanoseconds

        create();

        while (running) {
            long startTime = System.nanoTime(); // Start time of frame
            long deltaTime = startTime - prevStartTime;  // Time to run frame
            prevStartTime = startTime;  // Set new previous start time for next frame
            double delta = deltaTime / (double) targetTime;    // Factor used for updating game objects

            update(delta);
            draw();
            renderer.render();

            long frameTime = System.nanoTime() - startTime; // Time that frame has taken so far

            // TODO 3-4 ggr mindre cpu anv�ndning men mer lagg. Thread.sleep är inte tillräckligt exakt?
            /*if (frameTime < targetTime) {
                try{
                    Thread.sleep((targetTime - frameTime) / 1000000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }*/

            while (frameTime < targetTime) {    // Loop until frame has reached targetTime.
                frameTime = System.nanoTime() - startTime;  // Update the time frame has taken so far
            }
        }
    }
}
