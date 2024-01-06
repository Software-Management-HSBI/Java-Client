import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.DrawTexturePro;

public class Sprite {

    public Raylib.Texture texture;
    public double offset;

    public Sprite(Raylib.Texture texture, double offset) {
        this.texture = texture;
        this.offset = offset;
    }

}



