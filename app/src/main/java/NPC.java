import static com.raylib.Raylib.LoadTexture;

import com.raylib.Raylib.Texture;

public class NPC {

    // TExture of the NPC
    public final NPCType texture;
    
    // Z Koordinate of the NPC (race track length)
    public double z;

    // X Koordinate of the NPC (- or + 1.0 relative to the midle of the track)
    public double x;

    public double speed;

    public NPC(NPCType pTexture, double pZ, double pX, double pSpeed) {
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
