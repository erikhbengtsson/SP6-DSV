package engine;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Class representing a single sound like a sound effect or music sequence.
 */
public class SoundClip {
    private Clip clip;

    public SoundClip(String path) {
        try {
            InputStream audioSource = getClass().getResourceAsStream(path);
            InputStream bufferedIn = new BufferedInputStream(audioSource);
            AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Play the sound once.
     */
    public void play() {
        if (clip == null) {
            return;
        }
        stop();
        clip.setFramePosition(0);
        clip.start();
    }

    /**
     * Loop the sound repeatedly.
     */
    public void loop() {
        if (clip == null) {
            return;
        }
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stop the sound stream. Sound can be played again later in the game.
     */
    public void stop() {
        if (clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * Closes the sound stream. Sound cannot be played again.
     */
    public void close() {
        stop();
        clip.close();
    }
}
