import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

/**
 * The Sound class provides functionality to play sound files in a separate thread. It supports
 * playing WAV files using the javax.sound.sampled library.
 */
public class Sound {

    /** The path to the directory containing the sound files. */
    public String path;

    /** The name of the sound file (excluding extension) to be played. */
    public String soundName;

    /** The currently playing sound's name. */
    private volatile String currentlyPlaying;

    /** The Clip object responsible for playing the sound. */
    private Clip clip;

    /** The volume control for the sound. */
    public FloatControl volumeControl;

    /**
     * Constructs a new Sound object with the specified path and sound name.
     *
     * @param path The path to the directory containing the sound files.
     * @param soundName The name of the sound file (excluding extension) to be played.
     */
    public Sound(String path, String soundName) {
        this.path = path;
        this.soundName = soundName;
    }

    /**
     * Gets the name of the currently playing sound.
     *
     * @return The name of the currently playing sound.
     */
    public String getCurrentlyPlaying() {
        return currentlyPlaying;
    }

    /** Plays the specified sound in a separate thread. */
    public void playSound() {
        // Anonymous thread for playing sound
        Thread soundThread =
                new Thread(
                        () -> {
                            try {
                                // Check if a sound is already playing
                                if (currentlyPlaying != null) {
                                    return;
                                }

                                currentlyPlaying = soundName;

                                // Load the sound file
                                File soundFile = new File(path + soundName + ".wav");
                                AudioInputStream audioInputStream =
                                        AudioSystem.getAudioInputStream(soundFile);
                                clip = AudioSystem.getClip();
                                clip.open(audioInputStream);
                                // Check if MASTER_GAIN control is supported
                                if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                                    volumeControl =
                                            (FloatControl)
                                                    clip.getControl(FloatControl.Type.MASTER_GAIN);
                                }

                                // Add a listener to respond to the end of the clip
                                clip.addLineListener(
                                        event -> {
                                            if (event.getType() == LineEvent.Type.STOP) {
                                                clip.close();
                                                currentlyPlaying = null;
                                            }
                                        });

                                // Start playing the sound
                                clip.start();

                                // Wait for the sound to finish before continuing
                                clip.drain();

                            } catch (UnsupportedAudioFileException
                                    | IOException
                                    | LineUnavailableException e) {
                                e.printStackTrace();
                            }
                        });

        // Start the sound thread
        soundThread.start();
    }

    /**
     * Gets the index of a sound with the specified name in the Game's list of sounds.
     *
     * @param name The name of the sound.
     * @return The index of the sound, or -1 if not found.
     */
    public static int getSoundIndex(String name) {
        int index = 0;
        for (Sound s : Game.sounds) {
            if (s.soundName.equals(name)) {
                return index;
            }
            index++;
        }
        return -1;
    }
}
