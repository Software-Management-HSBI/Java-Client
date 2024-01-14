import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SoundTest {


    private Sound testSound;

    @BeforeEach
    public void setUp() {
        testSound = new Sound(Constants.SOUNDSPATH, "racer");
        Game.sounds = new ArrayList<>();
    }

    @Test
    public void testGetCurrentlyPlaying() {
        assertNull(testSound.getCurrentlyPlaying());
    }



    @Test
    public void testGetSoundIndex() {
        // Create some test sounds
        Sound sound1 = new Sound(Constants.SOUNDSPATH, "sound1");


        // Add the test sounds to Game.sounds
        Game.sounds.add(sound1);


        // Test finding the index of a sound
        assertEquals(0, Sound.getSoundIndex("sound1"));


        // Test when the sound is not found
        assertEquals(-1, Sound.getSoundIndex("nonexistentSound"));
    }

    @Test
    public void testSoundConstruction() {
        assertEquals(Constants.SOUNDSPATH, testSound.path);
        assertEquals("racer", testSound.soundName);
    }




}
