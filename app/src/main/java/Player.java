import static com.raylib.Raylib.*;

public class Player {
    private static final String texturePath = "src/main/resources/images/sprites/";

    public Texture texture;
    public Texture straight;
    public Texture right;
    public Texture left;
    public int x, y;

    public Player(int x, int y) {
        this.texture = LoadTexture(texturePath + "player_straight.png");
        this.straight = LoadTexture(texturePath + "player_straight.png");
        this.right = LoadTexture(texturePath + "player_right.png");
        this.left = LoadTexture(texturePath + "player_left.png");
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