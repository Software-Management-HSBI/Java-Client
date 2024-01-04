import com.raylib.Raylib;

import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.DrawTexture;

public class Sprite {

    public Raylib.Texture texture;
    public double offset;
    public float destX;

    public Sprite(Raylib.Texture texture, double offset) {
        this.texture = texture;
        this.offset = offset;
    }

    public void draw(int segmentIndex, Road.Segment segment, float scale) {

        Sprite sprite = Game.segments.get(segmentIndex).sprite;
        if (sprite != null && sprite.texture != null) {

            double spriteScale = segment.p1.screen.scale;
            int spriteX = (int) (segment.p1.screen.x + (spriteScale * sprite.offset * Constants.ROADWIDTH * Constants.WIDTH / 2));
            int spriteY = (int) segment.p1.screen.y;

            float destW = (sprite.texture.width() * (float) spriteScale * Constants.WIDTH / 2) * (1.0f * Constants.ROADWIDTH) * scale;
            float destH = (sprite.texture.height() * (float) spriteScale * Constants.WIDTH / 2) * (1.0f * Constants.ROADWIDTH) * scale;

            DrawTexture(sprite.texture, (int) Game.segments.get(segmentIndex).p1.screen.x, (int) Game.segments.get(segmentIndex).p1.screen.y, WHITE);
        }
    }
}
