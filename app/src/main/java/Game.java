import static com.raylib.Jaylib.RAYWHITE;
import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;

import com.raylib.Jaylib;
import com.raylib.Raylib.Texture;

import java.util.ArrayList;

/** The main game class that initializes the game and starts the game loop */
public class Game {
    public static ArrayList<Road.Segment> segments;
    ArrayList<Texture> playerSprites;
    ArrayList<Util.Background> backgroundSprites;
    static Player player = null;
    Util.Background surfaceSky, surfaceSky2 = null;
    Util.Background surfaceHills, surfaceHills2 = null;
    Util.Background surfaceTrees, surfaceTrees2 = null;



    Texture billboard1;
    Texture billboard2;
    Texture billboard3;
    Texture billboard4;
    Texture billboard5;
    Texture billboard6;
    Texture billboard7;
    Texture billboard8;
    Texture billboard9;

    Texture[] billboards;


    static Texture tree1;
    Texture tree2;
    Texture palm_tree;

    Texture[] plants;

    Texture column;



    int trackLength = 0;
    double playerX = 0;
    double position = 0;
    double speed = 0;

    double centrifugal = 0.3;
    boolean keyLeft = false;
    boolean keyRight = false;
    boolean keyFaster = false;
    boolean keySlower = false;
    double skyOffset;
    double hillOffset;
    double treeOffset;

    MainMenu mainMenu;

     static double SPRITESCALE;

     Road.Segment segment;


 OptionsManager optionsManager;
    static GameState gameState = GameState.MENU;

    /** Initializes the game and starts the game loop */
    public Game() {

        optionsManager = OptionsManager.getInstance();
        InitWindow(Constants.WIDTH, Constants.HEIGHT, "Racer");
        SetTargetFPS((int) Constants.FPS);
        playerSprites = new ArrayList<>();
        backgroundSprites = new ArrayList<>();
        createBillboards();
        createPlants();
        createColumns();
        resetRoad();
        createPlayer();




        createBackground();
        mainMenu = MainMenu.getInstance();
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

            // Debugg
            // System.out.println("Segement y: " + Road.findSegment(position).p1.world.y);
        }
        CloseWindow();
    }

    /** Initialize the road segments and set the track length */
    public void resetRoad() {
        segments = new ArrayList<>();
        Road.addStraight(Road.ROAD.LENGTH.MEDIUM);

        Road.addRoad(
                Road.ROAD.LENGTH.MEDIUM,
                Road.ROAD.LENGTH.MEDIUM,
                Road.ROAD.LENGTH.MEDIUM,
                Road.ROAD.HILL.MEDIUM,
                Road.ROAD.CURVE.MEDIUM);
        Road.addRoad(
                Road.ROAD.LENGTH.MEDIUM,
                Road.ROAD.LENGTH.MEDIUM,
                Road.ROAD.LENGTH.MEDIUM,
                -Road.ROAD.HILL.MEDIUM,
                -Road.ROAD.CURVE.MEDIUM);




        segments.get(Road.findSegment(Constants.PLAYERZ).index + 2).color = Constants.STARTCOLORS;
        segments.get(Road.findSegment(Constants.PLAYERZ).index + 3).color = Constants.STARTCOLORS;

        for (int n = 0; n < Constants.RUMBLELENGTH; n++) {
            segments.get(segments.size() - 1 - n).color = Constants.FINISHCOLORS;
        }
        resetSprites();
    }

    private void resetSprites() {


        for(int i = 0;i<100;i++){
            Road.addSprite(i,billboard7,10);


        }









    }



    /** Update the position, speed and texture of the player */
    public void update(double dt) {
        switch (gameState) {
            case MENU:
                mainMenu.checkInput();
                break;
            case SINGLEPLAYER:
                updateSinglePlayer(dt);

                break;
                // case MULTIPLAYER:

            default:
                // Handle unexpected values of gameState
                gameState = GameState.MENU;
                break;
        }
    }

    /** Render different gameStates */
    public void render() {
        BeginDrawing();

        switch (gameState) {
            case MENU:
                mainMenu.showBackground();
                break;
            case SINGLEPLAYER:
                renderSinglePlayer();

                break;
                // case MULTIPLAYER:

        }

        EndDrawing();
    }

    /** Create the player */
    public void createPlayer() {
        player = new Player(Constants.WIDTH / 2, Constants.HEIGHT - 150);
        player.x -= player.texture.width() * 3 / 2;
        playerSprites.add(player.texture);
        SPRITESCALE =  0.3 * ((double) 1 /player.straight.width());


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

        surfaceSky2 =
                new Util.Background(
                        LoadTexture(Constants.BACKGROUNDTEXTUREPATH + "sky.png"),
                        surfaceSky.texture.width(),
                        0);
        surfaceHills2 =
                new Util.Background(
                        LoadTexture(Constants.BACKGROUNDTEXTUREPATH + "hills.png"),
                        -surfaceHills.texture.width(),
                        0);
        surfaceTrees2 =
                new Util.Background(
                        LoadTexture(Constants.BACKGROUNDTEXTUREPATH + "trees.png"),
                        -surfaceTrees.texture.width(),
                        0);

        backgroundSprites.add(surfaceSky2);
        backgroundSprites.add(surfaceHills2);
        backgroundSprites.add(surfaceTrees2);
    }

    void drawTextureParallax(
            float parallaxOffSet, Util.Background texture1, Util.Background texture2) {

        DrawTextureEx(
                texture1.texture, new Jaylib.Vector2(parallaxOffSet, texture1.y), 0, 1, WHITE);
        DrawTextureEx(
                texture2.texture,
                new Jaylib.Vector2(parallaxOffSet - texture2.texture.width(), texture1.y),
                0,
                1,
                WHITE);
    }

    // updates all the logic of the singleplayer mode
    private void updateSinglePlayer(double dt) {
        Road.Segment playerSegment = Road.findSegment(position + Constants.PLAYERZ);
        double speedPercent = speed / Constants.MAXSPEED;
        double dx =
                dt * 2 * speedPercent; // at top speed, should be able to cross from left to right
        // (-1 to +1) in 1 second

        trackLength = segments.size() * Constants.SEGMENTLENGTH;


        position = Util.increase(position, dt * speed, trackLength);

        skyOffset =
                Util.increase(
                        skyOffset, Constants.SKYSPEED * playerSegment.curve * speedPercent, 1);
        hillOffset =
                Util.increase(
                        hillOffset, Constants.HILLSPEED * playerSegment.curve * speedPercent, 1);
        treeOffset =
                Util.increase(
                        treeOffset, Constants.TREESPEED * playerSegment.curve * speedPercent, 1);

        double incline = playerSegment.p2.world.y - playerSegment.p1.world.y;

        playerX = playerX - (dx * speedPercent * playerSegment.curve * centrifugal);

        if (keyLeft && speed > 0) {
            playerX = playerX - dx;
            if (incline > 0) player.driveUpLeft();
            else player.driveLeft();
        } else if (keyRight && speed > 0) {
            playerX = playerX + dx;
            if (incline > 0) player.driveUpRight();
            else player.driveRight();
        } else {
            if (incline > 0) player.driveUpStraight();
            else player.driveStraight();
        }

        playerSprites.add(player.texture);

        if (keyFaster) speed = Util.accelerate(speed, Constants.ACCEL, dt);
        else if (keySlower) speed = Util.accelerate(speed, Constants.BREAKING, dt);
        else speed = Util.accelerate(speed, Constants.DECEL, dt);

        if (((playerX < -1) || (playerX > 1)) && (speed > Constants.OFFROADLIMIT))
            speed = Util.accelerate(speed, Constants.OFFROADDECEL, dt);

        playerX = Util.limit(playerX, -2, 2);
        speed = Util.limit(speed, 0, Constants.MAXSPEED);

          // Tastendruck 'S' zeigt/versteckt die Optionen
        if (IsKeyPressed(KEY_S)) {
            optionsManager.show = !optionsManager.show;
        }

        optionsManager.update();
    }

    // renders all the graphics for the singleplayer mode
    private void renderSinglePlayer() {
        Road.Segment baseSegment = Road.findSegment(position);
        double baseSegmentPercent = Util.percentRemaining((int) position, Constants.SEGMENTLENGTH);

        Road.Segment playerSegment = Road.findSegment(position + Constants.PLAYERZ);
        double playerSegmentPercent =
                Util.percentRemaining(
                        (int) (position + Constants.PLAYERZ), Constants.SEGMENTLENGTH);

        double playerY =
                Util.interpolate(
                        playerSegment.p1.world.y, playerSegment.p2.world.y, playerSegmentPercent);

        double maxY = Constants.HEIGHT;
        System.out.println( baseSegment.p1.screen.scale);




        var x = 0;
        var dx = -(baseSegment.curve * baseSegmentPercent);

        ClearBackground(RAYWHITE); // Hier wird der Hintergrund gelöscht

        float parallaxSurfaceSky = (float) (skyOffset * surfaceSky.texture.width());
        float parallaxSurfaceHills = (float) (hillOffset * surfaceHills.texture.width());
        float parallaxSurfaceTree = (float) (treeOffset * surfaceTrees.texture.width());

        drawTextureParallax(parallaxSurfaceSky, surfaceSky, surfaceSky2);
        drawTextureParallax(parallaxSurfaceHills, surfaceHills, surfaceHills2);
        drawTextureParallax(parallaxSurfaceTree, surfaceTrees, surfaceTrees2);

        for (int i = 0; i < Constants.DRAWDISTANCE; i++) {
             segment = segments.get((baseSegment.index + i) % segments.size());
            var segmentLooped = segment.index < baseSegment.index;
            segment.fog =
                    Util.exponentialFog((double) i / Constants.DRAWDISTANCE, Constants.FOGDENSITY);

            x = (int) (x + dx);
            dx = dx + segment.curve;

            Util.project(
                    segment.p1,
                    (playerX * Constants.ROADWIDTH) - x,
                    playerY + Constants.CAMERAHEIGHT,
                    position - (segmentLooped ? trackLength : 0),
                    Constants.CAMERADEPTH,
                    Constants.WIDTH,
                    Constants.HEIGHT,
                    Constants.ROADWIDTH);

            Util.project(
                    segment.p2,
                    (playerX * Constants.ROADWIDTH) - x - dx,
                    playerY + Constants.CAMERAHEIGHT,
                    position - (segmentLooped ? trackLength : 0),
                    Constants.CAMERADEPTH,
                    Constants.WIDTH,
                    Constants.HEIGHT,
                    Constants.ROADWIDTH);

            if ((segment.p1.camera.z <= Constants.CAMERADEPTH)
                    || (segment.p2.screen.y >= maxY)
                    || (segment.p2.screen.y >= segment.p1.screen.y)) continue;

            Util.segment(
                    Constants.WIDTH,
                    Constants.LANES,
                    (int) segment.p1.screen.x,
                    (int) segment.p1.screen.y,
                    (int) segment.p1.screen.w,
                    (int) segment.p2.screen.x,
                    (int) segment.p2.screen.y,
                    (int) segment.p2.screen.w,
                    segment.fog,
                    segment.color);

            maxY = segment.p2.screen.y;




        }

        for (Texture texture : playerSprites) {
            DrawTexture(texture, player.x, player.y, WHITE);
        }





        for(int a =0;a<segments.size();a++){
            Game.segments.get(a).sprite.draw(a,baseSegment);
        }







        optionsManager.showBackground();
        playerSprites.clear();
    }

    private void createBillboards(){

        billboard1 =  LoadTexture(Constants.SPRITETEXTUREPATH + "billboard01.png");
        billboard2 =  LoadTexture(Constants.SPRITETEXTUREPATH + "billboard02.png");
        billboard3 =  LoadTexture(Constants.SPRITETEXTUREPATH + "billboard03.png");
        billboard4 =  LoadTexture(Constants.SPRITETEXTUREPATH + "billboard04.png");
        billboard5 =  LoadTexture(Constants.SPRITETEXTUREPATH + "billboard05.png");
        billboard6 =  LoadTexture(Constants.SPRITETEXTUREPATH + "billboard06.png");
        billboard7 =  LoadTexture(Constants.SPRITETEXTUREPATH + "billboard07.png");
        billboard8 =  LoadTexture(Constants.SPRITETEXTUREPATH + "billboard08.png");
        billboard9 =  LoadTexture(Constants.SPRITETEXTUREPATH + "billboard09.png");

        billboards = new Texture[]{billboard1,billboard2,billboard3,billboard4,billboard5,
                billboard6,billboard7,billboard8,billboard9};


        }
    private void createPlants(){

        tree1 =  LoadTexture(Constants.SPRITETEXTUREPATH + "tree1.png");
        tree2 =  LoadTexture(Constants.SPRITETEXTUREPATH + "tree2.png");
        palm_tree =  LoadTexture(Constants.SPRITETEXTUREPATH + "palm_tree.png");
        plants = new Texture[]{tree1,tree2,palm_tree};


    }

    private void createColumns(){
        column =LoadTexture(Constants.SPRITETEXTUREPATH + "column.png");
    }
}


