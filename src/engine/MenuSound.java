package engine;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * The MenuSound class extends the Sound class to support playing an intro part
 * of a menu theme using one audio file and then looping a specified part using another audio file.
 */
public class MenuSound extends Sound {
    private Clip loopSound;

    /**
     * Constructs a MenuSound object and initializes it with the specified intro and loop audio files.
     *
     * @param introFilePath The file path of the intro audio file to be loaded.
     * @param loopFilePath  The file path of the looping audio file to be loaded.
     */
    public MenuSound(String introFilePath, String loopFilePath) {
        super(introFilePath);
        setLoopSound(loopFilePath);
    }

    /**
     * Sets the audio file to be used for the looping part of the menu theme.
     *
     * @param loopFilePath The file path of the looping audio file to be loaded.
     */
    public void setLoopSound(String loopFilePath) {
        try {
            File soundFile = new File(loopFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            loopSound = AudioSystem.getClip();
            loopSound.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays the intro audio followed by looping the specified looping audio.
     */
    @Override
    public void playSound() {
        if (sound != null && loopSound != null) {
            sound.setFramePosition(0); // Start the intro from the beginning
            sound.start();

            // Add a listener to transition to the looping audio after the intro finishes
            sound.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    sound.close(); // Close the intro clip
                    loopSound.loop(Clip.LOOP_CONTINUOUSLY); // Start looping the loop audio
                }
            });
        }
    }

    /**
     * Stops the currently playing audio and closes the clips.
     */
    public void stopSound() {
        if (sound != null) {
            sound.stop();
            sound.close();
        }
        if (loopSound != null) {
            loopSound.stop();
            loopSound.close();
        }
    }
}