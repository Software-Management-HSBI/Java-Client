import static com.raylib.Jaylib.BLACK;
import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.BeginDrawing;
import static com.raylib.Raylib.ClearBackground;
import static com.raylib.Raylib.CloseWindow;
import static com.raylib.Raylib.DrawFPS;
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

import com.raylib.Raylib.Texture;

public class Game {
    double fps = 60;
    double step = (double) (1/fps);
    double dt = 1/30.0;
    int width = 1024;
    int height = 768;
    ArrayList<Util.Segment> segments;
    ArrayList<Texture> playerSprites;
    ArrayList<Background> backgroundSprites;
    Player player = null;
    Background surfaceSky = null;
    Background surfaceHills = null;
    Background surfaceTrees = null;
    // screen = None
    // background = None
    // sprites = None
    // resolution = None
    int roadWidth = 4000;
    int segmentLength = 200;
    int rumbleLength = 3;
    int trackLength = 0;
    int lanes = 8;
    int fov = 100;
    int cameraHeight = 1000;
    double cameraDepth = 1 / Math.tan((fov / 2) * Math.PI / 180);
    int drawDistance = 300;
    double playerX = 0;
    double playerZ = (int) (cameraHeight * cameraDepth);
    int fogDensity = 5;
    double position = 0;
    double speed = 0;
    double maxSpeed = segmentLength/step;
    double accel = maxSpeed/5;
    double breaking = -maxSpeed;
    double decel = -maxSpeed/5;
    double offRoadDecel = -maxSpeed/2;
    double offRoadLimit = maxSpeed/4;
    boolean keyLeft = false;
    boolean keyRight = false;
    boolean keyFaster = false;
    boolean keySlower = false;

    public Game() {
        InitWindow(width, height, "Racer");
        SetTargetFPS((int) fps);
        ClearBackground(BLACK);
        DrawFPS(20, 20);
        playerSprites = new ArrayList<>();
        backgroundSprites = new ArrayList<>();
        resetRoad();
        gameLoop();
    }

    public void gameLoop() {
        createPlayer();
        createBackground();

        while (!WindowShouldClose()) {
            if (IsKeyDown(KEY_LEFT))
                keyLeft = true;
            else 
                keyLeft = false;

            if (IsKeyDown(KEY_RIGHT))
                keyRight = true;
            else 
                keyRight = false;

            if (IsKeyDown(KEY_UP))
                keyFaster = true;
            else
                keyFaster = false;

            if (IsKeyDown(KEY_DOWN))
                keySlower = true;
            else
                keySlower = false;

            render();
            update(step);
            ClearBackground(BLACK);
        }
        CloseWindow();
    }

	public void resetRoad() {
		segments = new ArrayList<>();
		for (int n = 0; n < 500; n++) {
			HashMap<String, Integer> color = getRoadColor(n);
			segments.add(new Util().new Segment(n, n * segmentLength, (n + 1) * segmentLength, color));
		}
		segments.get(findSegment(playerZ).index + 2).color = Constants.STARTCOLORS;
		segments.get(findSegment(playerZ).index + 3).color = Constants.STARTCOLORS;

		for (int n = 0; n < rumbleLength; n++) {
			segments.get(segments.size() - 1 - n).color = Constants.FINISHCOLORS;
		}

		trackLength = segments.size() * segmentLength;
	}

    private Util.Segment findSegment(double n) {
        return segments.get((int) Math.floor(n/segmentLength) % segments.size());
    }
    
     HashMap<String, Integer> getRoadColor(double n) {
        if ((n / rumbleLength) % 2 == 0)
            return Constants.LIGHTCOLORS;
        else
            return Constants.DARKCOLORS;
    }

    public void update(double dt) {
        position = Util.increase(position, dt * speed, trackLength);

        var dx = dt * 2 * (speed/maxSpeed);
        
        if (keyLeft) {
            playerX = playerX - dx;
            if (speed > 0)
                player.driveLeft();
        } else if (keyRight) {
            playerX = playerX + dx;
            if (speed > 0)
                player.driveRight();
        } else 
            player.driveStraight();
        
        playerSprites.add(player.texture);

        if (keyFaster)
          speed = Util.accelerate(speed, accel, dt);
        else if (keySlower)
          speed = Util.accelerate(speed, breaking, dt);
        else
          speed = Util.accelerate(speed, decel, dt);

        if (((playerX < -1) || (playerX > 1)) && (speed > offRoadLimit))
          speed = Util.accelerate(speed, offRoadDecel, dt);

        playerX = Util.limit(playerX, -2, 2);
        speed   = Util.limit(speed, 0, maxSpeed);
    }

    public void render() {
        
        BeginDrawing();
        for (Texture texture : playerSprites) {
            DrawTexture(texture, player.x, player.y, WHITE);
        }
        playerSprites.clear();

        for (Background background : backgroundSprites) {
            DrawTexture(background.texture, background.x, background.y, WHITE);
        }
        EndDrawing();       
    }

    public void createPlayer() {
        player = new Player(width / 2, height - 100);
        player.x -= player.texture.width() / 2;
        playerSprites.add(player.texture);
    }

    public void createBackground() {
        surfaceSky = new Background(LoadTexture(Constants.BACKGROUNDTEXTUREPATH + "sky.png"), 0, 0);
        surfaceHills = new Background(LoadTexture(Constants.BACKGROUNDTEXTUREPATH + "hills.png"), 0, 0);
        surfaceTrees = new Background(LoadTexture(Constants.BACKGROUNDTEXTUREPATH + "trees.png"), 0, 0);

        backgroundSprites.add(surfaceSky);
        backgroundSprites.add(surfaceHills);
        backgroundSprites.add(surfaceTrees);
    }
}
