import java.util.HashMap;
import com.raylib.Raylib.Color;

public class Constants {
	public static final HashMap<String, Color> STARTCOLORS = new HashMap<String, Color>() {
		{
			put("road", Util.color(255, 255, 255));
			put("grass", Util.color(255, 255, 255));
			put("rumble", Util.color(255, 255, 255));
		}
	};
	
	public static final HashMap<String, Color> FINISHCOLORS = new HashMap<String, Color>() {
        {
            put("road", Util.color(0, 0, 0));
        	put("grass", Util.color(0, 0, 0));
            put("rumble", Util.color(0, 0, 0));
        }
    };

	public static final HashMap<String, Color> LIGHTCOLORS = new HashMap<String, Color>() {
        {
            put("road", Util.color(107, 107, 107));
            put("grass", Util.color(16, 170, 16));
            put("rumble", Util.color(85, 85, 85));
            put("lane", Util.color(204, 204, 204));
        }
    };

	public static final HashMap<String, Color> DARKCOLORS = new HashMap<String, Color>() { 
		{
            put("road", Util.color(105, 105, 105));
            put("grass", Util.color(0, 154, 0));
            put("rumble", Util.color(187, 187, 187));
        }
    };

	public static final HashMap<String, Color> COLORS = new HashMap<String, Color>() {
		{
			put("sky", Util.color(114, 215, 238));
			put("tree", Util.color(0, 81, 8));
			put("fog", Util.color(0, 81, 8));
		}
	};

	public static final String SPRITETEXTUREPATH = "src/main/resources/images/sprites/";
	public static final String BACKGROUNDTEXTUREPATH = "src/main/resources/images/background/";
}
