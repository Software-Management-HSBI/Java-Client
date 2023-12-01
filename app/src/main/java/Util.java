import static com.raylib.Raylib.ColorAlpha;
import static com.raylib.Raylib.DrawRectangle;
import static com.raylib.Raylib.DrawTriangle;

import java.util.Date;
import java.util.HashMap;

import com.raylib.Raylib;
import com.raylib.Raylib.Color;
import com.raylib.Raylib.Texture;
import com.raylib.Raylib.Vector2;

public class Util {
    public static long getCurrentTimestamp() {
        return new Date().getTime();
    }

    public static double limit(double value, double minimum, double maximum) {
        return Math.max(minimum, Math.min(value, maximum));
    }

    public static int randomInt(int min, int max) {
        return (int) Math.round(interpolate(min, max, Math.random()));
    }

    public static double interpolate(double a, double b, double percent) {
        return a + (b - a) * percent;
    }

    public static int randomChoice(int[] options) {
        return options[randomInt(0, options.length - 1)];
    }

    public static int percentRemaining(int n, int total) {
        return (n % total) / total;
    }

    public static double accelerate(double v, double accel, double dt) {
        return v + (accel * dt);
    }

    public static int easeIn(int a, int b, int percent) {
        return (int) (a + (b - a) * Math.pow(percent, 2));
    }

    public static int easeOut(int a, int b, int percent) {
        return (int) (a + (b - a) * (1 - Math.pow(1 - percent, 2)));
    }

    public static int easeInOut(int a, int b, int percent) {
        return (int) (a + (b - a) * (1 - Math.pow(1 - percent, 2)));
    }

    public static double exponentialFog(double distance, double density) {
        return 1 / Math.pow(Math.E, (distance * distance * density));
    }

    public static double increase(double start, double increment, double max) {
        double result = start + increment;

        while (result >= max) result -= max;

        while (result < 0) result += max;

        return result;
    }

    public static boolean overlap(double x1, double w1, double x2, double w2, double percent) {
        double half = (percent > 0 ? percent : 1) / 2;
        double min1 = x1 - (w1 * half);
        double max1 = x1 + (w1 * half);
        double min2 = x2 - (w2 * half);
        double max2 = x2 + (w2 * half);

        return !((max1 < min2) || (min1 > max2));
    }

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

    static void segment(
            int width,
            int lanes,
            int x1,
            int y1,
            int w1,
            int x2,
            int y2,
            int w2,
            int fog,
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

        fog(0, y1, width, y2 - y1, fog);
    }

    static void fog(int x, int y, int width, int height, int fog) {
        if (fog < 1) {
            Color color = ColorAlpha(Constants.COLORS.get("fog"), (1 - fog));
            DrawRectangle(x, y, width, height, color);
        }
    }

    static double rumbleWidth(double projectedRoadWidth, int lanes) {
        return projectedRoadWidth / Math.max(6, 2 * lanes);
    }

    static double laneMarkerWidth(double projectedRoadWidth, int lanes) {
        return projectedRoadWidth / Math.max(32, 8 * lanes);
    }

    public static Color color(int r, int g, int b) {
        Color color = new Raylib.Color();
        color.r((byte) r);
        color.g((byte) g);
        color.b((byte) b);
        color.a((byte) 255);
        return color;
    }

    public record Background(Texture texture, int x, int y) {};

    public class Segment {
        int index;
        Point p1;
        Point p2;
        HashMap<String, Color> color;

        Segment(int index, double z1, double z2, HashMap<String, Color> color) {
            this.index = index;
            this.p1 = new Point(z1);
            this.p2 = new Point(z2);
            this.color = color;
        }
    }

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

    public class World {
        double x;
        double y;
        double z;

        World(double z) {
            this.x = 0;
            this.y = 0;
            this.z = z;
        }

        World(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public class Camera {
        double x;
        double y;
        double z;

        Camera() {
            this.x = 0;
            this.y = 0;
            this.z = 0;
        }

        Camera(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

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

        Screen(double scale, double x, double y, double w) {
            this.scale = scale;
            this.x = x;
            this.y = y;
            this.w = w;
        }
    }
}
