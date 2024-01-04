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

    public void draw(int segmentIndex, Road.Segment segment) {
        if (Game.segments.get(segmentIndex).sprite != null) {
            Sprite sprite = Game.segments.get(segmentIndex).sprite;
            double spriteScale = segment.p1.screen.scale;
            int spriteX = (int) (segment.p1.screen.x + (spriteScale * sprite.offset * Constants.ROADWIDTH * Constants.WIDTH / 2));
            int spriteY = (int) segment.p1.screen.y;

            // Rufe die drawSprite-Methode auf, um das Sprite zu zeichnen
            sprite.drawSprite(sprite.texture, Constants.WIDTH, Constants.HEIGHT,
                    Constants.ROADWIDTH, (float) spriteScale, spriteX, spriteY);
        }
    }



        public void drawSprite(Raylib.Texture sprite, float width, float height,
                           float roadWidth, float scale, float destX, float destY) {

        // Skalierung für die Projektion und relativ zur Straßenbreite (für Anpassungen an die Benutzeroberfläche)
        float destW = (sprite.width() * scale * width / 2) * (1.0f * roadWidth);
        float destH = (sprite.height() * scale * width / 2) * (1.0f * roadWidth);

        Jaylib.Rectangle sourceRect = new Jaylib.Rectangle(0, 0, sprite.width(), sprite.height());
        Jaylib.Rectangle destRect = new Jaylib.Rectangle(destX, destY, destW, destH);
        DrawTexturePro(sprite, sourceRect, destRect, new Jaylib.Vector2(0, 0), 0.0f, WHITE);
    }
}
