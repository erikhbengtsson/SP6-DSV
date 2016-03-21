package engine.subsystems;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class creates a JFrame and draws images on it.
 * The Graphics2D object in this class should be used for all drawing.
 */
public class Renderer extends JFrame {
    private BufferedImage image;
    private Graphics2D graphics2D;
    private int width, height;
    private boolean fullscreen;

    /**
     * Constructor initializing JFrame with a fixed width and height.
     */
    public Renderer(int width, int height) {
        this.width = width;
        this.height = height;
        fullscreen = false;
        setPreferredSize(new Dimension(width, height));
        setVisible(true);
        pack();
        init();
    }

    /**
     * Constructor initializing JFrame in fullscreen.
     */
    public Renderer() {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = environment.getDefaultScreenDevice();
        setUndecorated(true);
        device.setFullScreenWindow(this);
        this.width = device.getFullScreenWindow().getWidth();
        this.height = device.getFullScreenWindow().getHeight();
        fullscreen = true;
        init();
    }

    /**
     * Variables and attributes used by both fullscreen and windowed constructors.
     */
    private void init() {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        graphics2D = (Graphics2D) image.getGraphics();
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Renders the screen with BufferedImage image.
     */
    public void render() {
        Graphics g = getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
    }

    // Getter methods

    public Graphics2D getGraphics2D() {
        return graphics2D;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public int getScreenWidth() {
        return width;
    }

    public int getScreenHeight() {
        return height;
    }
}
