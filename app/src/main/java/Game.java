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

import java.util.ArrayList;
import java.util.HashMap;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Texture;

public class Game {
    double fps = 60;
    double step = (double) (1 / fps);
    double dt = 1 / 30.0;
    int width = 1024;
    int height = 768;
    ArrayList<Util.Segment> segments;
    ArrayList<Texture> playerSprites;
    ArrayList<Util.Background> backgroundSprites;
    Player player = null;
    Util.Background surfaceSky = null;
    Util.Background surfaceHills = null;
    Util.Background surfaceTrees = null;
    int roadWidth = 2000;
    int segmentLength = 200;
    int rumbleLength = 3;
    int trackLength = 0;
    int lanes = 4;
    int fov = 100;
    int cameraHeight = 1000;
    double cameraDepth = 1 / Math.tan((fov / 2) * Math.PI / 180);
    int drawDistance = 300;
    double playerX = 0;
    double playerZ = cameraHeight * cameraDepth;
    int fogDensity = 5;
    double position = 0;
    double speed = 0;
    double maxSpeed = segmentLength / step;
    double accel = maxSpeed / 5.0;
    double breaking = -maxSpeed;
    double decel = -maxSpeed / 5.0;
    double offRoadDecel = -maxSpeed / 2.0;
    double offRoadLimit = maxSpeed / 4.0;
    boolean keyLeft = false;
    boolean keyRight = false;
    boolean keyFaster = false;
    boolean keySlower = false;

    public Game() {
        InitWindow(width, height, "Racer");
        SetTargetFPS((int) fps);
        playerSprites = new ArrayList<>();
        backgroundSprites = new ArrayList<>();
        resetRoad();
        gameLoop();
    }

    public void gameLoop() {
        createPlayer();
        createBackground();

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
            update(step);
        }
        CloseWindow();
    }

    public void resetRoad() {
        segments = new ArrayList<>();
        for (int n = 0; n < 500; n++) {
            HashMap<String, Color> color = getRoadColor(n);
            segments.add(
                    new Util().new Segment(n, n * segmentLength, (n + 1) * segmentLength, color));
        }
        segments.get(findSegment(playerZ).index + 2).color = Constants.STARTCOLORS;
        segments.get(findSegment(playerZ).index + 3).color = Constants.STARTCOLORS;

        for (int n = 0; n < rumbleLength; n++) {
            segments.get(segments.size() - 1 - n).color = Constants.FINISHCOLORS;
        }

        trackLength = segments.size() * segmentLength;
    }

    private Util.Segment findSegment(double n) {
        return segments.get((int) Math.floor(n / segmentLength) % segments.size());
    }

    HashMap<String, Color> getRoadColor(double n) {
        if ((n / rumbleLength) % 2 == 0) return Constants.DARKCOLORS;
        else return Constants.LIGHTCOLORS;
    }

    public void update(double dt) {
        position = Util.increase(position, dt * speed, trackLength);

        var dx = dt * 2 * (speed / maxSpeed);

        if (keyLeft) {
            playerX = playerX - dx;
            if (speed > 0) player.driveLeft();
        } else if (keyRight) {
            playerX = playerX + dx;
            if (speed > 0) player.driveRight();
        } else player.driveStraight();

        playerSprites.add(player.texture);

        if (keyFaster) speed = Util.accelerate(speed, accel, dt);
        else if (keySlower) speed = Util.accelerate(speed, breaking, dt);
        else speed = Util.accelerate(speed, decel, dt);

        if (((playerX < -1) || (playerX > 1)) && (speed > offRoadLimit))
            speed = Util.accelerate(speed, offRoadDecel, dt);

        playerX = Util.limit(playerX, -2, 2);
        speed = Util.limit(speed, 0, maxSpeed);
    }

    public void render() {
        var baseSegment = findSegment(position);
        double maxY = height;

        BeginDrawing();
        for (Util.Background background : backgroundSprites) {
            DrawTexture(background.texture(), background.x(), background.y(), WHITE);
        }

        for (int i = 0; i < drawDistance; i++) {
            var segment = segments.get((baseSegment.index + i) % segments.size());
            var segmentLooped = segment.index < baseSegment.index;
            int segmentLoopedValue = 0;
            double segmentFog = Util.exponentialFog(i / drawDistance, fogDensity);

            if (segmentLooped) segmentLoopedValue = trackLength;

            segment.p1 =
                    Util.project(
                            segment.p1,
                            (playerX * roadWidth),
                            cameraHeight,
                            position - segmentLoopedValue,
                            cameraDepth,
                            width,
                            height,
                            roadWidth);

            segment.p2 =
                    Util.project(
                            segment.p2,
                            (playerX * roadWidth),
                            cameraHeight,
                            position - segmentLoopedValue,
                            cameraDepth,
                            width,
                            height,
                            roadWidth);

            if ((segment.p1.camera.z <= cameraDepth) || (segment.p2.screen.y >= maxY)) continue;

            Util.segment(
                    width,
                    lanes,
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

    public void createPlayer() {
        player = new Player(width / 2, height - 150);
        player.x -= player.texture.width() * 3 / 2;
        playerSprites.add(player.texture);
    }

    public void createBackground() {
        surfaceSky = new Util.Background(LoadTexture(Constants.BACKGROUNDTEXTUREPATH + "sky.png"), 0, 0);
        surfaceHills =
                new Util.Background(LoadTexture(Constants.BACKGROUNDTEXTUREPATH + "hills.png"), 0, 0);
        surfaceTrees =
                new Util.Background(LoadTexture(Constants.BACKGROUNDTEXTUREPATH + "trees.png"), 0, 0);

        backgroundSprites.add(surfaceSky);
        backgroundSprites.add(surfaceHills);
        backgroundSprites.add(surfaceTrees);
    }
}
