import static com.raylib.Raylib.*;

public class Background {
    public Texture texture;
    public int x, y;
    
    public Background(Texture texture, int x, int y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
    }
}
