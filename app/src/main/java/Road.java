import com.raylib.Raylib.Color;

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
        int index;
        Util.Point p1;
        Util.Point p2;
        HashMap<String, Color> color;
        double curve;

        Segment(int index, double z1, double z2, HashMap<String, Color> color) {
            this.index = index;
            this.p1 = new Util().new Point(z1);
            this.p2 = new Util().new Point(z2);
            this.color = color;
        }
    }
    
    public static void addSegment(){
        int n = Game.segments.size();
    HashMap<String, Color> color = getRoadColor(n);
    Game.segments.add(
                    new Road().new Segment(
                    n,
                    (double) (n * Constants.SEGMENTLENGTH),
                    (double) ((n + 1) * Constants.SEGMENTLENGTH),
                    color));

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


    public static void addRoad(double enter, int hold, double leave, double curve) {
        int n;
        for( n = 0 ; n < enter ; n++)
            addSegment(Util.easeIn(0, curve, n/enter));
        for( n = 0 ; n < hold  ; n++)
            addSegment(curve);
        for( n = 0 ; n < leave ; n++)
            addSegment(Util.easeInOut(curve, 0, n/leave));
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

    public static void addStraight(int num) {
        num = (num == 0) ? ROAD.LENGTH.MEDIUM : num;
        addRoad(num, num, num, 0);
    }

    public static void addStraight() {
        addRoad(ROAD.LENGTH.MEDIUM,ROAD.LENGTH.MEDIUM,ROAD.LENGTH.MEDIUM,0);
    }

    public static void addCurve(int num, int curve) {
        num = (num == 0) ? ROAD.LENGTH.MEDIUM : num;
        curve = (curve == 0) ? ROAD.CURVE.MEDIUM : curve;
        addRoad(num, num, num, curve);
    }


    public static void addSCurves(){
        addRoad(ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM,  -ROAD.CURVE.EASY);
        addRoad(ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM,   ROAD.CURVE.MEDIUM);
        addRoad(ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM,   ROAD.CURVE.EASY);
        addRoad(ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM,  -ROAD.CURVE.EASY);
        addRoad(ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM,  -ROAD.CURVE.MEDIUM);
    }
}
