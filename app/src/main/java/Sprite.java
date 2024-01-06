import com.raylib.Raylib;

/**
 * Represents a sprite with texture and offset in a 2D environment.
 *
 * This class encapsulates information about a sprite, including its texture
 * and offset. It is designed for use in a 2D environment, such as a game or
 * graphical application.
 *
 * @author Your Name
 * @version 1.0
 */
public class Sprite {

    /**
     * The texture associated with the sprite.
     */
    public Raylib.Texture texture;

    /**
     * The offset of the sprite.
     */
    public double offset;

    /**
     * Constructs a Sprite with the specified texture and offset.
     *
     * @param texture The texture to be associated with the sprite.
     * @param offset The offset of the sprite in a 2D space.
     */
    public Sprite(Raylib.Texture texture, double offset) {
        this.texture = texture;
        this.offset = offset;
    }
}
