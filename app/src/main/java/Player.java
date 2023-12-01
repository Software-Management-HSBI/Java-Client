import static com.raylib.Raylib.ImageResize;
import static com.raylib.Raylib.LoadImageFromTexture;
import static com.raylib.Raylib.LoadTexture;
import static com.raylib.Raylib.LoadTextureFromImage;

import com.raylib.Raylib.Image;
import com.raylib.Raylib.Texture;

public class Player {
    public Texture texture;
    public Texture straight;
    public Texture right;
    public Texture left;
    public int x, y;

    /**
     * Create a new player on the given x and y coordinates with set textures
     *
     * @param x position of the player
     * @param y position of the player
     */
    public Player(int x, int y) {
        this.texture = LoadTexture(Constants.SPRITETEXTUREPATH + "player_straight.png");
        this.straight = scale(LoadTexture(Constants.SPRITETEXTUREPATH + "player_straight.png"));
        this.right = scale(LoadTexture(Constants.SPRITETEXTUREPATH + "player_right.png"));
        this.left = scale(LoadTexture(Constants.SPRITETEXTUREPATH + "player_left.png"));
        this.x = x;
        this.y = y;
    }

    /** Set the texture of the player to the straight texture */
    public void driveStraight() {
        this.texture = straight;
    }

    /** Set the texture of the player to the right texture */
    public void driveRight() {
        this.texture = right;
    }

    /** Set the texture of the player to the left texture */
    public void driveLeft() {
        this.texture = left;
    }

    /**
     * Scale the given texture by a factor of 3
     *
     * @param texture to scale
     * @return the scaled texture
     */
    public Texture scale(Texture texture) {
        Image image = LoadImageFromTexture(texture);
        ImageResize(image, this.texture.width() * 3, this.texture.height() * 3);
        return LoadTextureFromImage(image);
    }
}
