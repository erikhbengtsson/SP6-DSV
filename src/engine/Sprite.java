package engine;

import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * This class is the visual representation of a GameObject. It can be a single image or any number of animations.
 * Every GameObject has an instance of the Sprite class.
 */
public class Sprite {
    private BufferedImage image;
    private final HashMap<String, Animation> animations;
    private String currentAnimation;

    public Sprite() {
        animations = new HashMap<>();
    }

    /**
     * Update the animation current animation.
     */
    public void update() {
        animations.get(currentAnimation).update();
    }

    /**
     * If the GameObject only has one image this method is used to select it.
     *
     * @param path String
     */
    public void setImage(String path) {
        image = GameEngine.loader.loadImage(path);
    }

    /**
     * Add an animation.
     *
     * @param name String
     * @param animation Animation
     */
    public void addAnimation(String name, Animation animation) {
        animations.put(name, animation);
    }

    /**
     * Set the current animation.
     *
     * @param name String
     */
    public void setAnimation(String name) {
        currentAnimation = name;
        animations.get(currentAnimation).setPlayedOnce(false);
        animations.get(currentAnimation).setCurrentFrame(0);
    }

    // Getter methods

    public Animation getAnimation() {
        return animations.get(currentAnimation);
    }

    public BufferedImage getImage() {
        return image;
    }
}
