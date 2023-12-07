import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.BeginDrawing;
import static com.raylib.Raylib.CloseWindow;
import static com.raylib.Raylib.DrawTexture;
import static com.raylib.Raylib.EndDrawing;
import static com.raylib.Raylib.InitWindow;
import static com.raylib.Raylib.IsKeyDown;
import static com.raylib.Raylib.KEY_DOWN;
import static com.raylib.Raylib.KEY_LEFT;
import static com.raylib.Raylib.KEY_RIGHT;
import static com.raylib.Raylib.KEY_UP;
import static com.raylib.Raylib.LoadTexture;
import static com.raylib.Raylib.SetTargetFPS;
import static com.raylib.Raylib.WindowShouldClose;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Texture;

import java.util.ArrayList;
import java.util.HashMap;

/** The main game class that initializes the game and starts the game loop */
public class Game {
    ArrayList<Util.Segment> segments;
    ArrayList<Texture> playerSprites;
    ArrayList<Util.Background> backgroundSprites;
    Player player = null;
    Util.Background surfaceSky = null;
    Util.Background surfaceHills = null;
    Util.Background surfaceTrees = null;
    int trackLength = 0;
    double playerX = 0;
    double position = 0;
    double speed = 0;

    double centrifugal = 0.3;
    boolean keyLeft = false;
    boolean keyRight = false;
    boolean keyFaster = false;
    boolean keySlower = false;
     double skyOffset =1;
     double hillOffset;
     double treeOffset;

    /** Initializes the game and starts the game loop */
    public Game() {
        InitWindow(Constants.WIDTH, Constants.HEIGHT, "Racer");
        SetTargetFPS((int) Constants.FPS);
        playerSprites = new ArrayList<>();
        backgroundSprites = new ArrayList<>();
        resetRoad();
        createPlayer();
        createBackground();
        gameLoop();
    }

    /** The game loop that renders and updates the game and checks for key presses */
    public void gameLoop() {
        while (!WindowShouldClose()) {
            if (IsKeyDown(KEY_LEFT)) keyLeft = true;
            else keyLeft = false;

            if (IsKeyDown(KEY_RIGHT)) keyRight = true;
            else keyRight = false;

            if (IsKeyDown(KEY_UP)) keyFaster = true;
            else keyFaster = false;

            if (IsKeyDown(KEY_DOWN)) keySlower = true;
            else keySlower = false;

            render();
            update(Constants.STEP);
        }
        CloseWindow();
    }

    /** Initialize the road segments and set the track length */
    public void resetRoad() {
        segments = new ArrayList<>();
        addStraight(ROAD.LENGTH.SHORT/4);
        addSCurves();
        addStraight(ROAD.LENGTH.LONG);
        addCurve(ROAD.LENGTH.MEDIUM, ROAD.CURVE.MEDIUM);
        addCurve(ROAD.LENGTH.LONG, ROAD.CURVE.MEDIUM);
        addStraight();
        addSCurves();
        addCurve(ROAD.LENGTH.LONG, -ROAD.CURVE.MEDIUM);
        addCurve(ROAD.LENGTH.LONG, ROAD.CURVE.MEDIUM);
        addStraight();
        addSCurves();
        addCurve(ROAD.LENGTH.LONG, -ROAD.CURVE.EASY);
        segments.get(findSegment(Constants.PLAYERZ).index + 2).color = Constants.STARTCOLORS;
        segments.get(findSegment(Constants.PLAYERZ).index + 3).color = Constants.STARTCOLORS;

        for (int n = 0; n < Constants.RUMBLELENGTH; n++) {
            segments.get(segments.size() - 1 - n).color = Constants.FINISHCOLORS;
        }

        trackLength = segments.size() * Constants.SEGMENTLENGTH;
    }

    /**
     * Find the segment based on the given position
     *
     * @param positionZ the position of the segment
     * @return the segment at the given position
     */
    private Util.Segment findSegment(double positionZ) {
        return segments.get(
                (int) Math.floor(positionZ / Constants.SEGMENTLENGTH) % segments.size());
    }

    /**
     * Get the color of the road based on the segment index
     *
     * @param index the index of the segment
     * @return the color of the road
     */
    HashMap<String, Color> getRoadColor(double index) {
        if ((index / Constants.RUMBLELENGTH) % 2 == 0) return Constants.DARKCOLORS;
        else return Constants.LIGHTCOLORS;
    }

    /** Update the position, speed and texture of the player */
    public void update(double dt) {
        var playerSegment = findSegment(position+Constants.PLAYERZ);
        var speedPercent  = speed/Constants.MAXSPEED;
        var dx            = dt * 2 * speedPercent; // at top speed, should be able to cross from left to right (-1 to +1) in 1 second

        position = Util.increase(position, dt * speed, trackLength);

        skyOffset = Util.increase(skyOffset, Constants.SKYSPEED  * playerSegment.curve * speedPercent, 1);
        hillOffset = Util.increase(hillOffset, Constants.HILLSPEED  * playerSegment.curve * speedPercent, 1);
        treeOffset = Util.increase(treeOffset, Constants.TREESPEED  * playerSegment.curve * speedPercent, 1);
        

        playerX = playerX - (dx * speedPercent * playerSegment.curve * centrifugal);
        if (keyLeft) {
            playerX = playerX - dx;
            if (speed > 0) player.driveLeft();
        } else if (keyRight) {
            playerX = playerX + dx;
            if (speed > 0) player.driveRight();
        } else player.driveStraight();

        playerSprites.add(player.texture);

        if (keyFaster) speed = Util.accelerate(speed, Constants.ACCEL, dt);
        else if (keySlower) speed = Util.accelerate(speed, Constants.BREAKING, dt);
        else speed = Util.accelerate(speed, Constants.DECEL, dt);

        if (((playerX < -1) || (playerX > 1)) && (speed > Constants.OFFROADLIMIT))
            speed = Util.accelerate(speed, Constants.OFFROADDECEL, dt);

        playerX = Util.limit(playerX, -2, 2);
        speed = Util.limit(speed, 0, Constants.MAXSPEED);
    }

    /** Render the background, road and player */
    public void render() {
        var baseSegment = findSegment(position);
        var basePercent = Util.percentRemaining(position, Constants.SEGMENTLENGTH);
        double maxY = Constants.HEIGHT;

        var x = 0;
        var dx = -(baseSegment.curve * basePercent);


        BeginDrawing();
        for (Util.Background background : backgroundSprites) {
                DrawTexture(background.texture, background.x, background.y, WHITE);
            }




        for (int i = 0; i < Constants.DRAWDISTANCE; i++) {
            var segment = segments.get((baseSegment.index + i) % segments.size());
            var segmentLooped = segment.index < baseSegment.index;
            int segmentLoopedValue = 0;
            double segmentFog =
                    Util.exponentialFog(i / Constants.DRAWDISTANCE, Constants.FOGDENSITY);


            Util.project(segment.p1, (playerX * Constants.ROADWIDTH) - x,      Constants.CAMERAHEIGHT, position - (segmentLooped ? trackLength : 0), Constants.CAMERADEPTH, Constants.WIDTH, Constants.HEIGHT, Constants.ROADWIDTH);
            Util.project(segment.p2, (playerX * Constants.ROADWIDTH) - x - dx, Constants.CAMERAHEIGHT, position - (segmentLooped ? trackLength : 0), Constants.CAMERADEPTH, Constants.WIDTH, Constants.HEIGHT, Constants.ROADWIDTH);

            x  = (int) (x + dx); //////
            dx = dx + segment.curve;


            if (segmentLooped) segmentLoopedValue = trackLength;

            segment.p1 =
                    Util.project(
                            segment.p1,
                            (playerX * Constants.ROADWIDTH),
                            Constants.CAMERAHEIGHT,
                            position - segmentLoopedValue,
                            Constants.CAMERADEPTH,
                            Constants.WIDTH,
                            Constants.HEIGHT,
                            Constants.ROADWIDTH);

            segment.p2 =
                    Util.project(
                            segment.p2,
                            (playerX * Constants.ROADWIDTH),
                            Constants.CAMERAHEIGHT,
                            position - segmentLoopedValue,
                            Constants.CAMERADEPTH,
                            Constants.WIDTH,
                            Constants.HEIGHT,
                            Constants.ROADWIDTH);

            if ((segment.p1.camera.z <= Constants.CAMERADEPTH) || (segment.p2.screen.y >= maxY))
                continue;

            Util.segment(
                    Constants.WIDTH,
                    Constants.LANES,
                    (int) segment.p1.screen.x,
                    (int) segment.p1.screen.y,
                    (int) segment.p1.screen.w,
                    (int) segment.p2.screen.x,
                    (int) segment.p2.screen.y,
                    (int) segment.p2.screen.w,
                    (int) segmentFog,
                    segment.color);

            maxY = segment.p2.screen.y;
        }

        for (Texture texture : playerSprites) {
            DrawTexture(texture, player.x, player.y, WHITE);
        }

        playerSprites.clear();
        EndDrawing();
    }



    /** Create the player */
    public void createPlayer() {
        player = new Player(Constants.WIDTH / 2, Constants.HEIGHT - 150);
        player.x -= player.texture.width() * 3 / 2;
        playerSprites.add(player.texture);
    }

    /** Create the background */
    public void createBackground() {
        surfaceSky =
                new Util.Background(LoadTexture(Constants.BACKGROUNDTEXTUREPATH + "sky.png"), 0, 0);
        surfaceHills =
                new Util.Background(
                        LoadTexture(Constants.BACKGROUNDTEXTUREPATH + "hills.png"), 0, 0);
        surfaceTrees =
                new Util.Background(
                        LoadTexture(Constants.BACKGROUNDTEXTUREPATH + "trees.png"), 0, 0);

        backgroundSprites.add(surfaceSky);
        backgroundSprites.add(surfaceHills);
        backgroundSprites.add(surfaceTrees);
    }



public void addSegment(double curve){
        int n = segments.size();
    HashMap<String, Color> color = getRoadColor(n);
    segments.add(
            new Util()
                    .new Segment(
                    n,
                    n * Constants.SEGMENTLENGTH,
                    (n + 1) * Constants.SEGMENTLENGTH,curve,
                    color));

}


    public void  addRoad(double enter, int hold, double leave, double curve) {
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


    }

    public void addStraight(int num) {
        num = (num == 0) ? ROAD.LENGTH.MEDIUM : num;
        addRoad(num, num, num, 0);
    }

    public void addStraight() {
        addRoad(ROAD.LENGTH.MEDIUM,ROAD.LENGTH.MEDIUM,ROAD.LENGTH.MEDIUM,0);
    }

    public void addCurve(int num, int curve) {
        num = (num == 0) ? ROAD.LENGTH.MEDIUM : num;
        curve = (curve == 0) ? ROAD.CURVE.MEDIUM : curve;
        addRoad(num, num, num, curve);
    }


    public void addSCurves(){
        addRoad(ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM,  -ROAD.CURVE.EASY);
        addRoad(ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM,   ROAD.CURVE.MEDIUM);
        addRoad(ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM,   ROAD.CURVE.EASY);
        addRoad(ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM,  -ROAD.CURVE.EASY);
        addRoad(ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM, ROAD.LENGTH.MEDIUM,  -ROAD.CURVE.MEDIUM);

    }
}
