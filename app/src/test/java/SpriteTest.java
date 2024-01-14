import com.raylib.Raylib;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SpriteTest {

    private  Raylib.Texture testTexture;
    private static double testOffset;
    private static Sprite testSprite;

    public SpriteTest(){
        testTexture = new Raylib.Texture();
        testOffset = 10.5;
        testSprite = new Sprite(testTexture, testOffset);
    }


    @Test
    public void testGetTexture() {
        assertEquals(testTexture, testSprite.texture);
    }

    @Test
    public void testGetOffset() {
        assertEquals(testOffset, testSprite.offset, 0.0);
    }


    @Test
    public void testNotEquals() {
        Raylib.Texture differentTexture = new Raylib.Texture();
        Sprite differentSprite = new Sprite(differentTexture, testOffset);
        assertNotEquals(testSprite, differentSprite);
    }
}
