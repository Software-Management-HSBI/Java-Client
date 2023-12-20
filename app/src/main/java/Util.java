import static com.raylib.Raylib.ColorAlpha;
import static com.raylib.Raylib.DrawRectangle;
import static com.raylib.Raylib.DrawTriangle;

import com.raylib.Raylib;
import com.raylib.Raylib.Color;
import com.raylib.Raylib.Texture;
import com.raylib.Raylib.Vector2;

import java.util.Date;
import java.util.HashMap;

/** Utility class containing helper methods and classes */
public class Util {
    /**
     * Get the current timestamp
     *
     * @return the current timestamp
     */
    public static long getCurrentTimestamp() {
        return new Date().getTime();
    }

    /**
     * Limit a value between a minimum and maximum
     *
     * @return the limited value
     */
    public static double limit(double value, double minimum, double maximum) {
        return Math.max(minimum, Math.min(value, maximum));
    }

    /**
     * Get a random integer between min and max
     *
     * @return a random integer between min and max
     */
    public static int randomInt(int min, int max) {
        return (int) Math.round(interpolate(min, max, Math.random()));
    }

    /**
     * Interpolates between two values based on a percentage
     *
     * @return the interpolated value
     */
    public static double interpolate(double a, double b, double percent) {
        return a + (b - a) * percent;
    }

    /**
     * Randomly selects an element from an array of options
     *
     * @param options an array of options
     * @return a randomly selected element from the array
     */
    public static int randomChoice(int[] options) {
        return options[randomInt(0, options.length - 1)];
    }

    /**
     * Calculate the remaining percentage of a given value relative the total value
     *
     * @return the remaining percentage of the value relative to the total value
     */
    public static double percentRemaining(int value, int total) {
        return (value % (double) total) / total;
    }

    /**
     * Accelerate based on the current speed, acceleration, and delta time
     *
     * @param speed current speed
     * @param accel acceleration
     * @param dt delta time
     * @return the new speed
     */
    public static double accelerate(double speed, double accel, double dt) {
        return speed + (accel * dt);
    }

    /**
     * Calculate exponential fog based on distance and density
     *
     * @return the fog factor based on the exponential function
     */
    public static double exponentialFog(double distance, double density) {
        return 1 / Math.pow(Math.E, (distance * distance * density));
    }

    /**
     * Increase a value by a given increment, wrapping around if it exceeds the maximum value
     *
     * @return the increased value with wrapping
     */
    public static double increase(double start, double increment, double max) {
        double result = start + increment;

        while (result >= max) result -= max;

        while (result < 0) result += max;

        return result;
    }

    /**
     * Check if two ranges overlap based on a given percentage
     *
     * @param percent the percentage determining the overlap threshold
     * @return true if the ranges overlap, false otherwise
     */
    public static boolean overlap(double x1, double w1, double x2, double w2, double percent) {
        double half = (percent > 0 ? percent : 1) / 2;
        double min1 = x1 - (w1 * half);
        double max1 = x1 + (w1 * half);
        double min2 = x2 - (w2 * half);
        double max2 = x2 + (w2 * half);

        return !((max1 < min2) || (min1 > max2));
    }

    /**
     * Set the camera and screen coordinates of a given point based on the camera and screen
     *
     * @param p the point to project
     * @return the projected point
     */
    public static Point project(
            Point p,
            double cameraX,
            double cameraY,
            double cameraZ,
            double cameraDepth,
            double width,
            double height,
            double roadWidth) {
        p.camera.x = Math.max(p.world.x, 0) - cameraX;
        p.camera.y = Math.max(p.world.y, 0) - cameraY;
        p.camera.z = Math.max(p.world.z, 0) - cameraZ;
        if (p.camera.z == 0) p.camera.z = 1;
        p.screen.scale = cameraDepth / p.camera.z;
        p.screen.x = Math.round((width / 2) + (p.screen.scale * p.camera.x * width / 2));
        p.screen.y = Math.round((height / 2) - (p.screen.scale * p.camera.y * height / 2));
        p.screen.w = Math.round((p.screen.scale * roadWidth * width / 2));
        return p;
    }

    /**
     * Draw a 4-sided polygon with the given coordinates and color using two triangles
     *
     * @param color the fill color of the polygon
     */
    static void polygon(
            int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, Color color) {
        Vector2 v1 = new Vector2();
        Vector2 v2 = new Vector2();
        Vector2 v3 = new Vector2();
        Vector2 v4 = new Vector2();
        v1.x(x1).y(y1);
        v2.x(x2).y(y2);
        v3.x(x3).y(y3);
        v4.x(x4).y(y4);
        DrawTriangle(v1, v3, v4, color);
        DrawTriangle(v1, v2, v3, color);
        v1.close();
        v2.close();
        v3.close();
        v4.close();
    }

    /**
     * Draw a segment of the road with the given coordinates
     *
     * @param width the width of the road
     * @param lanes the number of lanes on the road
     * @param fog the fog factor
     * @param colors the colors of the road
     */
    static void segment(
            int width,
            int lanes,
            int x1,
            int y1,
            int w1,
            int x2,
            int y2,
            int w2,
            double fog,
            HashMap<String, Color> colors) {
        int r1 = (int) rumbleWidth(w1, lanes);
        int r2 = (int) rumbleWidth(w2, lanes);
        int l1 = (int) laneMarkerWidth(w1, lanes);
        int l2 = (int) laneMarkerWidth(w2, lanes);

        DrawRectangle(0, y2, width, y1 - y2, colors.get("grass"));

        polygon(x1 - w1 - r1, y1, x1 - w1, y1, x2 - w2, y2, x2 - w2 - r2, y2, colors.get("rumble"));
        polygon(x1 + w1, y1, x1 + w1 + r1, y1, x2 + w2 + r2, y2, x2 + w2, y2, colors.get("rumble"));
        polygon(x1 - w1, y1, x1 + w1, y1, x2 + w2, y2, x2 - w2, y2, colors.get("road"));

        if (colors.containsKey("lane")) {
            int lanew1 = w1 * 2 / lanes;
            int lanew2 = w2 * 2 / lanes;
            int lanex1 = x1 - w1 + lanew1;
            int lanex2 = x2 - w2 + lanew2;
            for (int lane = 1; lane < lanes; lanex1 += lanew1, lanex2 += lanew2, lane++)
                polygon(
                        lanex1 - l1 / 2,
                        y1,
                        lanex1 + l1 / 2,
                        y1,
                        lanex2 + l2 / 2,
                        y2,
                        lanex2 - l2 / 2,
                        y2,
                        colors.get("lane"));
        }

        fog(0, y1, width, y1-y2, fog);
    }

    /**
     * Draw a rectangle representing fog based on the fog factor
     *
     * @param fog the fog factor
     */
    static void fog(int x, int y, int width, int height, double fog) {
        if (fog < 1) {
            Color color = ColorAlpha(Constants.COLORS.get("fog"), (float) (1 - fog));
            DrawRectangle(x, y, width, height, color);
        }
    }

    /**
     * Calculate the width of the rumble strips based on the projected road width and number of
     * lanes
     *
     * @return the width of the rumble strips
     */
    static double rumbleWidth(double projectedRoadWidth, int lanes) {
        return projectedRoadWidth / Math.max(6, 2 * lanes);
    }

    /**
     * Calculate the width of the lane markers based on the projected road width and number of lanes
     *
     * @return the width of the lane markers
     */
    static double laneMarkerWidth(double projectedRoadWidth, int lanes) {
        return projectedRoadWidth / Math.max(32, 8 * lanes);
    }


    public static double easeIn(double a, double b, double percent) {
        return a + (b - a) * Math.pow(percent, 2);
    }

    public static double easeOut(double a, double b, double percent) {
        return a + (b - a) * (1 - Math.pow(1 - percent, 2));
    }

    public  static double easeInOut(double a, double b, double percent) {
        return a + (b - a) * ((-Math.cos(percent * Math.PI) / 2) + 0.5);
    }

    /**
     * Create a new raylib color with the given RGB values
     *
     * @return a new raylib color
     */
    public static Color color(int r, int g, int b) {
        Color color = new Raylib.Color();
        color.r((byte) r);
        color.g((byte) g);
        color.b((byte) b);
        color.a((byte) 255);
        return color;
    }

    /**
     * Create a Background at the given coordinates and texture
     *
     * @param texture the texture of the background
     * @param x the x coordinate of the background
     * @param y the y coordinate of the background
     */
     public static class  Background {

         Texture texture;
         int x,y;
         public Background(Texture texture, int x, int y){
             this.texture = texture;
             this.x =x;
             this.y = y;
         }
    };

    /**
     * Create a Point representing a World, Camera, and Screen point
     *
     * @param z the z coordinate of the World point
     * @return a new Point containing points for World, Camera, and Screen
     */
    public class Point {
        World world;
        Camera camera;
        Screen screen;

        Point(double z) {
            this.world = new World(z);
            this.camera = new Camera();
            this.screen = new Screen();
        }
    }

    /** Create a World point with a given z coordinate */
    public static class World {
        double x;
        double y;
        double z;

        World(double z) {
            this.x = 0;
            this.y = 0;
            this.z = z;
        }
    }

    /** Create an empty Camera point */
    public class Camera {
        double x;
        double y;
        double z;

        Camera() {
            this.x = 0;
            this.y = 0;
            this.z = 0;
        }
    }

    /** Create an empty Screen point */
    public class Screen {
        double scale;
        double x;
        double y;
        double w;

        Screen() {
            this.scale = 0;
            this.x = 0;
            this.y = 0;
            this.w = 0;
        }
    }
}