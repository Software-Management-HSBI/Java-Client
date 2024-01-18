import com.raylib.Raylib.Texture;

import static com.raylib.Raylib.LoadTexture;

import java.util.HashMap;

public class OtherPlayer {

    public static final HashMap<Integer, Texture> PLAYERCOLOURS
    = new HashMap<Integer, Texture>() {{
        put(1, LoadTexture(Constants.SPRITETEXTUREPATH + "player_straight.png"));
        put(2, LoadTexture(Constants.SPRITETEXTUREPATH + "player_straight.png"));
        put(3, LoadTexture(Constants.SPRITETEXTUREPATH + "player_straight.png"));
        put(4, LoadTexture(Constants.SPRITETEXTUREPATH + "player_straight.png"));
    }};

    public int player;

    private Texture texture;

    public int position;

    public double x;

    public boolean ready = false;

    public boolean finish = false;

    public OtherPlayer(int pPlayer) {
        this.player = pPlayer;
        this.texture = PLAYERCOLOURS.get(pPlayer);
    }

    public Texture getTexture() {
        return this.texture;
    }
}