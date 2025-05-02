package engine;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * The Sound class is responsible for handling audio playback in the application.
 * It supports loading audio files, playing them, and optionally looping the playback.
 */
public class Sound {
    protected Clip sound;
    private boolean loop;

    /**
     * Constructs a Sound object and initializes it with the specified audio file.
     *
     * @param filePath The file path of the audio file to be loaded.
     */
    public Sound(String filePath) {
        setSound(filePath);
    }

    /**
     * Sets the audio file to be used by this Sound object.
     *
     * @param filePath The file path of the audio file to be loaded.
     */
    public void setSound(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            sound = AudioSystem.getClip();
            sound.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets whether the audio should loop continuously when played.
     *
     * @param loop A boolean value indicating whether the audio should loop.
     */
    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    /**
     * Plays the audio. If looping is enabled, the audio will play continuously.
     * Otherwise, it will play once from the beginning.
     */
    public void playSound() {
        if (sound != null) {
            if (loop) {
                sound.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                sound.setFramePosition(0); // Reset to the beginning
                sound.start();
            }
        }
    }
}