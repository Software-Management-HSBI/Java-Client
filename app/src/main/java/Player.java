import static com.raylib.Raylib.LoadTexture;

import com.raylib.Raylib.Texture;

public class Player {
    public Texture texture;
    public Texture straight;
    public Texture right;
    public Texture left;
    public int x, y;

    public Player(int x, int y) {
        this.texture = LoadTexture(Constants.TEXTUREPATH + "player_straight.png");
        this.straight = LoadTexture(Constants.TEXTUREPATH + "player_straight.png");
        this.right = LoadTexture(Constants.TEXTUREPATH + "player_right.png");
        this.left = LoadTexture(Constants.TEXTUREPATH + "player_left.png");
        this.x = x;
        this.y = y;
    }

    public void driveStraight() {
        this.texture = straight;
        // scale();
    }

    public void driveRight() {
        this.texture = right;
        // scale();
    }

    public void driveLeft() {
        this.texture = left;
        // scale();
    }
    
    // TODO: Test if this is needed and works
    // public void scale() {
    //     ImageResize(this.image, this.image.width() * 4, this.image.height() * 4);
    // }
}