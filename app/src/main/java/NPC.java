import static com.raylib.Raylib.ImageResize;
import static com.raylib.Raylib.LoadImageFromTexture;
import static com.raylib.Raylib.LoadTexture;
import static com.raylib.Raylib.LoadTextureFromImage;

import com.raylib.Raylib.Image;
import com.raylib.Raylib.Texture;

public class NPC {

    // TExture of the NPC
    public final Texture texture;
    
    // Z Koordinate of the NPC (race track length)
    public double z;

    // X Koordinate of the NPC (- or + 1.0 relative to the midle of the track)
    public double x;

    public double speed;

    public NPC(Texture pTexture, double pZ, double pX, double pSpeed) {
        this.texture = pTexture;
        this.z = pZ;
        this.x = pX;
        this.speed = pSpeed;
    }

    public static enum NPCType {

        CAR01(LoadTexture(Constants.SPRITETEXTUREPATH + "car01.png")),
        CAR02(LoadTexture(Constants.SPRITETEXTUREPATH + "car02.png")),
        CAR03(LoadTexture(Constants.SPRITETEXTUREPATH + "car03.png")),
        CAR04(LoadTexture(Constants.SPRITETEXTUREPATH + "car04.png")),
        SEMI(LoadTexture(Constants.SPRITETEXTUREPATH + "semi.png")),
        TRUCK(LoadTexture(Constants.SPRITETEXTUREPATH + "truck.png"));

        private Texture texture;
        
        private NPCType(Texture pTexture) {
            this.texture = pTexture;
        }

        public Texture getTexture() {
            return this.texture;
        }
    }
}
