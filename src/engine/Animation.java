package engine;

import java.awt.image.BufferedImage;

/**
 * This class handles a sequence of images representing the state of a game object (idle, run, jump etc).
 * The animations i stored in the Sprite class.
 */
public class Animation {
    private BufferedImage[] frames;
    private int currentFrame;
    private long startTime;
    private long delay;
    private boolean playedOnce;

    public Animation(BufferedImage[] frames, long delay) {
        this.frames = frames;
        this.delay = delay;
        playedOnce = false;
    }

    /**
     * Update the animation by changing frame after the selected delay.
     */
    public void update() {
        long elapsedTime = (System.nanoTime() - startTime) / 1000000;   // Milliseconds since last change of frame

        if (elapsedTime > delay) {
            currentFrame++;
            startTime = System.nanoTime();
        }

        if (currentFrame == frames.length) {
            playedOnce = true;  // The full animation has been played once
            currentFrame = 0;   // Start over when last frame is reached
        }
    }

    /**
     * Set the delay in milliseconds between each frame.
     *
     * @param delay long
     */
    public void setDelay(long delay) {
        this.delay = delay;
    }

    /**
     * Get the image on the current frame so it can be drawn.
     *
     * @return BufferedImage
     */
    public BufferedImage getImage() {
        return frames[currentFrame];
    }

    /**
     * Check if all of the animation has been played once.
     *
     * @return boolean, true if all the frames has been played
     */
    public boolean isPlayedOnce() {
        return playedOnce;
    }

    /**
     * Set if the animation has been played once or not.
     *
     * @param playedOnce boolean
     */
    public void setPlayedOnce(boolean playedOnce) {
        this.playedOnce = playedOnce;
    }

    /**
     * Set the current frame of the animation. Can be used for resetting an animation to it's first frame.
     *
     * @param currentFrame int
     */
    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }
}
