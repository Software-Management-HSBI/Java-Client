import com.raylib.Raylib;
import com.raylib.Raylib.Color;


import java.util.ArrayList;
import java.util.HashMap;

public class Road {

    /**
     * Create a Segment with the given index, z1, z2, and color
     *
     * @param index the index of the segment
     * @param z1 the z coordinate of the first World point
     * @param z2 the z coordinate of the second World point
     * @param color the color of the segment
     */
    public class Segment {
        public ArrayList<Raylib.Texture> sprites;
        int index;
        Util.Point p1;
        Util.Point p2;
        HashMap<String, Color> color;
        double curve;
        double fog;

        Segment(int index, double z1, double z2, double curve, HashMap<String, Color> color) {
            this.index = index;
            this.p1 = new Util().new Point(z1);
            this.p2 = new Util().new Point(z2);
            this.color = color;
            this.curve = curve;
            sprites = new ArrayList<>();
        }
    }

    public static void addSegment(double height, double curve) {
        int n = Game.segments.size();
        HashMap<String, Color> color = getRoadColor(n);

        Segment segment =
                new Road()
                .new Segment(
                        n,
                        (double) (n * Constants.SEGMENTLENGTH),
                        (double) ((n + 1) * Constants.SEGMENTLENGTH),
                        curve,
                        color);
        segment.p1.world.y = getLastY();
        segment.p2.world.y = height;

        Game.segments.add(segment);
    }

    /**
     * Get the color of the road based on the segment index
     *
     * @param index the index of the segment
     * @return the color of the road
     */
    static HashMap<String, Color> getRoadColor(double index) {
        if ((index / Constants.RUMBLELENGTH) % 2 == 0) return Constants.DARKCOLORS;
        else return Constants.LIGHTCOLORS;
    }

    /**
     * Find the segment based on the given position
     *
     * @param positionZ the position of the segment
     * @return the segment at the given position
     */
    public static Segment findSegment(double positionZ) {
        return Game.segments.get(
                (int) Math.floor(positionZ / Constants.SEGMENTLENGTH) % Game.segments.size());
    }

    public static void addRoad(int enter, int hold, int leave, int height, double curve) {
        double startY = getLastY();
        double endY = startY + (height * Constants.SEGMENTLENGTH);
        double total = enter + hold + leave;

        int n;
        for (n = 0; n < enter; n++)
            addSegment(Util.easeInOut(startY, endY, n / total), Util.easeIn(0, curve, n / enter));

        for (n = 0; n < hold; n++)
            addSegment(Util.easeInOut(startY, endY, (enter + n) / total), curve);

        for (n = 0; n < leave; n++)
            addSegment(
                    Util.easeInOut(startY, endY, (enter + hold + n) / total),
                    Util.easeInOut(curve, 0, n / leave));
    }

    // public void  addRoad(double enter, int hold, double leave, double curve) {
    //     int n;
    //     for( n = 0 ; n < enter ; n++)
    //         addSegment(Util.easeIn(0, curve, n/enter));
    //     for( n = 0 ; n < hold  ; n++)
    //         addSegment(curve);
    //     for( n = 0 ; n < leave ; n++)
    //         addSegment(Util.easeInOut(curve, 0, n/leave));
    // }

    private static double getLastY() {
        if (Game.segments.size() == 0) return 0;
        return Game.segments.get(Game.segments.size() - 1).p2.world.y;
    }

    public static class ROAD {
        public static class LENGTH {
            public static final int NONE = 0;
            public static final int SHORT = 25;
            public static final int MEDIUM = 50;
            public static final int LONG = 100;
        }

        public static class CURVE {
            public static final int NONE = 0;
            public static final int EASY = 2;
            public static final int MEDIUM = 4;
            public static final int HARD = 6;
        }

        public static class HILL {
            public static final int NONE = 0;
            public static final int LOW = 20;
            public static final int MEDIUM = 40;
            public static final int HIGH = 60;
        }
    }

    public static void addStraight(int num) {
        num = (num == 0) ? ROAD.LENGTH.MEDIUM : num;
        addRoad(num, num, num, 0, 0);
    }

    public static void addStraight() {
        addRoad(ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM, 0, 0);
    }

    public static void addCurve(int num, int curve) {
        num = (num == 0) ? ROAD.LENGTH.MEDIUM : num;
        curve = (curve == 0) ? ROAD.CURVE.MEDIUM : curve;
        addRoad(num, num, num, 0, curve);
    }

    public static void addHill(int num, int height) {
        num = (num == 0) ? ROAD.LENGTH.MEDIUM : num;
        height = (height == 0) ? ROAD.HILL.MEDIUM : height;
        addRoad(num, num, num, height, 0);
    }

    public static void addDownhillToEnd(int num) {
        num = (num == 0) ? ROAD.LENGTH.MEDIUM : num;
        addRoad(num, num, num, (int) (-getLastY() / Constants.SEGMENTLENGTH), 0);
    }

    public static void addSCurves() {
        addRoad(ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM, 0, -ROAD.CURVE.EASY);
        addRoad(ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM, 0, ROAD.CURVE.MEDIUM);
        addRoad(ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM, 0, ROAD.CURVE.EASY);
        addRoad(ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM, 0, -ROAD.CURVE.EASY);
        addRoad(ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM, 0, -ROAD.CURVE.MEDIUM);
    }

    static void addSprite(int n, Raylib.Texture sprite, int offset) {


                Game.segments.get(n).sprite = new Sprite(sprite, offset);



    }




}
