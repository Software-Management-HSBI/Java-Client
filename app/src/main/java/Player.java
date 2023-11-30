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

    public Player(int x, int y) {
        this.texture = LoadTexture(Constants.SPRITETEXTUREPATH + "player_straight.png");
        this.straight = LoadTexture(Constants.SPRITETEXTUREPATH + "player_straight.png");
        this.right = LoadTexture(Constants.SPRITETEXTUREPATH + "player_right.png");
        this.left = LoadTexture(Constants.SPRITETEXTUREPATH + "player_left.png");
        this.x = x;
        this.y = y;
    }

    public void driveStraight() {
        this.texture = straight;
        scale();
    }

    public void driveRight() {
        this.texture = right;
        scale();
    }

    public void driveLeft() {
        this.texture = left;
        scale();
    }
    
    public void scale() {
        Image image = LoadImageFromTexture(texture);
        ImageResize(image, this.texture.width() * 3, this.texture.height() * 3);
        texture = LoadTextureFromImage(image);
    }
}