package engine.subsystems;

import engine.SoundClip;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Singleton class used for storing and loading sounds. The instance of this class is located in the Loader class.
 */
public class SoundManager {
    private static SoundManager soundManager = new SoundManager();
    private HashMap<String, SoundClip> sounds;

    private SoundManager() {
        sounds = new HashMap<>();
    }

    /**
     * Returns the instance of the class.
     *
     * @return SoundManager
     */
    public static SoundManager getInstance() {
        return soundManager;
    }

    /**
     * Add a sound to HashMap sounds. The files path is used as key.
     *
     * @param path String
     */
    public void addSound(String path) {
        sounds.put(path, new SoundClip(path));
    }

    /**
     * Check is HashMap sounds contains a particular sound.
     *
     * @param path String
     * @return boolean
     */
    public boolean containsSound(String path) {
        return sounds.containsKey(path);
    }

    /**
     * Get a sound from HashMap sounds.
     *
     * @param path String
     * @return SoundClip
     */
    public SoundClip getSound(String path) {
        return sounds.get(path);
    }
}
