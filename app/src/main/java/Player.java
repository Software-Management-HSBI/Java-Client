import static com.raylib.Raylib.ImageResize;
import static com.raylib.Raylib.LoadImageFromTexture;
import static com.raylib.Raylib.LoadTexture;
import static com.raylib.Raylib.LoadTextureFromImage;

import com.raylib.Raylib.Image;
import com.raylib.Raylib.Texture;

/** The player class which holds the player textures and position */
public class Player {
    public Texture texture;
    public Texture straight;
    public Texture right;
    public Texture left;

    public Texture upStraight;
    public Texture upRight;
    public Texture upLeft;

    public int x, y;

    /**
     * Create a new player on the given x and y coordinates with set textures
     *
     * @param x position of the player
     * @param y position of the player
     */
    public Player(int x, int y) {
        this.texture = LoadTexture(Constants.SPRITETEXTUREPATH + "player_straight.png");
        this.straight = Util.scale(LoadTexture(Constants.SPRITETEXTUREPATH + "player_straight.png"), 3);
        this.right = Util.scale(LoadTexture(Constants.SPRITETEXTUREPATH + "player_right.png"), 3);
        this.left = Util.scale(LoadTexture(Constants.SPRITETEXTUREPATH + "player_left.png"), 3);

        this.upStraight =
                Util.scale(LoadTexture(Constants.SPRITETEXTUREPATH + "player_uphill_straight.png"), 3);
        this.upRight = Util.scale(LoadTexture(Constants.SPRITETEXTUREPATH + "player_uphill_right.png"), 3);
        this.upLeft = Util.scale(LoadTexture(Constants.SPRITETEXTUREPATH + "player_uphill_left.png"), 3);

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

    /** Set the texture of the player to the uphill straight texture */
    public void driveUpStraight() {
        this.texture = upStraight;
    }

    /** Set the texture of the player to the uphill right texture */
    public void driveUpRight() {
        this.texture = upRight;
    }

    /** Set the texture of the player to the uphill left texture */
    public void driveUpLeft() {
        this.texture = upLeft;
    }
}
