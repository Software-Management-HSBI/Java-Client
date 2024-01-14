import com.raylib.Raylib;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class UtilTest {

    @Test
    public void testGetCurrentTimestamp() {
        long timestamp = Util.getCurrentTimestamp();
        assertTrue(timestamp > 0);
    }

    @Test
    public void testLimit() {
        assertEquals(5.0, Util.limit(5.0, 2.0, 8.0), 0.001);
        assertEquals(2.0, Util.limit(1.0, 2.0, 8.0), 0.001);
        assertEquals(8.0, Util.limit(10.0, 2.0, 8.0), 0.001);
    }

    @Test
    public void testRandomInt() {
        int result = Util.randomInt(1, 10);
        assertTrue(result >= 1 && result <= 10);
        result = Util.randomInt(5, 5);
        assertEquals(5, result);
    }

    @Test
    public void testInterpolate() {
        assertEquals(2.0, Util.interpolate(1.0, 3.0, 0.5), 0.001);
        assertEquals(1.0, Util.interpolate(1.0, 3.0, 0.0), 0.001);
        assertEquals(3.0, Util.interpolate(1.0, 3.0, 1.0), 0.001);
    }

    @Test
    public void testRandomChoice() {
        int[] options = {1, 2, 3, 4, 5};
        int result = Util.randomChoice(options);
        assertTrue(result >= 1 && result <= 5);
        int[] singleOption = {1};
        assertEquals(1, Util.randomChoice(singleOption));
    }

    @Test
    public void testPercentRemaining() {
        assertEquals(0.2, Util.percentRemaining(20, 100), 0.001);
        assertEquals(0.0, Util.percentRemaining(0, 100), 0.001);
        assertEquals(0.5, Util.percentRemaining(50, 100), 0.001);
    }

    @Test
    public void testAccelerate() {
        assertEquals(11.0, Util.accelerate(10.0, 2.0, 0.5), 0.001);
        assertEquals(10.0, Util.accelerate(10.0, 2.0, 0.0), 0.001);
        assertEquals(14.0, Util.accelerate(10.0, 4.0, 1.0), 0.001);
    }

    @Test
    public void testExponentialFog() {
        double result = Util.exponentialFog(5.0, 0.1);
        assertTrue(result > 0);
    }

    @Test
    public void testIncrease() {
        assertEquals(3.0, Util.increase(8.0, 5.0, 10.0), 0.001);
        assertEquals(9.0, Util.increase(8.0, 1.0, 10.0), 0.001);
        assertEquals(0.0, Util.increase(8.0, 2.0, 10.0), 0.001);
    }





    @Test
    public void testEaseIn() {
        assertEquals(3.0, Util.easeIn(2.0, 6.0, 0.5), 0.1);
        assertEquals(2.0, Util.easeIn(2.0, 6.0, 0.0), 0.1);
        assertEquals(6.0, Util.easeIn(2.0, 6.0, 1.0), 0.1);
    }

    @Test
    public void testEaseOut() {
        assertEquals(5.0, Util.easeOut(2.0, 6.0, 0.5), 0.1);
        assertEquals(2.0, Util.easeOut(2.0, 6.0, 0.0), 0.1);
        assertNotEquals(2.0, Util.easeOut(2.0, 6.0, 1.0), 0.1);
    }

    @Test
    public void testEaseInOut() {
        assertEquals(4.0, Util.easeInOut(2.0, 6.0, 0.5), 0.001);
        assertEquals(2.0, Util.easeInOut(2.0, 6.0, 0.0), 0.001);
        assertEquals(6.0, Util.easeInOut(2.0, 6.0, 1.0), 0.001);
    }



    @Test
    public void testPointCreation() {
        Util util = new Util();
        Util.Point point = util.new Point(5.0);
        assertNotNull(point);
        assertNotNull(point.world);
        assertNotNull(point.camera);
        assertNotNull(point.screen);
    }

    // Doesn't work when running the ./gradlew test
    @Test
    public void testGetRandomTexture() {
        // Create a mock array of textures
        Raylib.Texture[] mockTextures = { mock(Raylib.Texture.class), mock(Raylib.Texture.class), mock(Raylib.Texture.class) };

        // Get a random texture from the array
        Raylib.Texture randomTexture = Util.getRandomTexture(mockTextures);

        // Verify that the returned texture is not null
        assertNotNull(randomTexture);

        // Verify that the returned texture is one of the textures in the array
        boolean isInArray = false;
        for (Raylib.Texture texture : mockTextures) {
            if (texture == randomTexture) {
                isInArray = true;
                break;
            }
        }
        assertTrue(isInArray, "The returned texture is not one of the textures in the array.");
    }

}
