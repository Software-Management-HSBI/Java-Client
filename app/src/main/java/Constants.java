import com.raylib.Raylib.Color;

import java.util.HashMap;

public class Constants {

    public static final double FPS = 60;
    public static final double STEP = (double) (1 / FPS);
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static int ROADWIDTH = 2000;
    public static final int SEGMENTLENGTH = 200;
    public static final int RUMBLELENGTH = 3;
    public static int LANES = 4;
    public static int FOV = 100;
    public static int CAMERAHEIGHT = 1000;
    public static final double CAMERADEPTH = 1 / Math.tan((FOV / 2) * Math.PI / 180);
    public static int DRAWDISTANCE = 300;
    public static int FOGDENSITY = 5;
    public static final double PLAYERZ = CAMERAHEIGHT * CAMERADEPTH;
    public static final double MAXSPEED = SEGMENTLENGTH / STEP;
    public static final double ACCEL = MAXSPEED / 5.0;
    public static final double BREAKING = -MAXSPEED;
    public static final double DECEL = -MAXSPEED / 5.0;
    public static final double OFFROADDECEL = -MAXSPEED / 2.0;
    public static final double OFFROADLIMIT = MAXSPEED / 4.0;
    public static final String SPRITETEXTUREPATH = "src/main/resources/images/sprites/";
    public static final String BACKGROUNDTEXTUREPATH = "src/main/resources/images/background/";

    public static final String UITEXTUREPATH = "src/main/resources/images/UI/";

    public static double SKYSPEED = 0.001;
    public static double HILLSPEED = 0.002;
    public static double TREESPEED = 0.003;

    public static final HashMap<String, Color> STARTCOLORS =
            new HashMap<String, Color>() {
                {
                    put("road", Util.color(255, 255, 255));
                    put("grass", Util.color(255, 255, 255));
                    put("rumble", Util.color(255, 255, 255));
                }
            };

    public static final HashMap<String, Color> FINISHCOLORS =
            new HashMap<String, Color>() {
                {
                    put("road", Util.color(0, 0, 0));
                    put("grass", Util.color(0, 0, 0));
                    put("rumble", Util.color(0, 0, 0));
                }
            };

    public static final HashMap<String, Color> LIGHTCOLORS =
            new HashMap<String, Color>() {
                {
                    put("road", Util.color(107, 107, 107));
                    put("grass", Util.color(16, 170, 16));
                    put("rumble", Util.color(85, 85, 85));
                    put("lane", Util.color(204, 204, 204));
                }
            };

    public static final HashMap<String, Color> DARKCOLORS =
            new HashMap<String, Color>() {
                {
                    put("road", Util.color(105, 105, 105));
                    put("grass", Util.color(0, 154, 0));
                    put("rumble", Util.color(187, 187, 187));
                }
            };

    public static final HashMap<String, Color> COLORS =
            new HashMap<String, Color>() {
                {
                    put("sky", Util.color(114, 215, 238));
                    put("tree", Util.color(0, 81, 8));
                    put("fog", Util.color(0, 81, 8));
                }
            };
}
