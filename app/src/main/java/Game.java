import static com.raylib.Jaylib.RAYWHITE;
import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import com.raylib.Raylib.Texture;

import java.util.ArrayList;
import java.util.Map;

/** The main game class that initializes the game and starts the game loop */
public class Game {
    public static ArrayList<Road.Segment> segments;
    public static ArrayList<Sound> sounds;
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



     static double maxY;

 OptionsManager optionsManager;


    static GameState gameState = GameState.MENU;

    /** Initializes the game and starts the game loop */
    public Game() {


        InitWindow(Constants.WIDTH, Constants.HEIGHT, "Racer");
        initSounds();
        SetTargetFPS((int) Constants.FPS);
        playerSprites = new ArrayList<>();
        backgroundSprites = new ArrayList<>();
        createBillboards();
        createPlants();
        createColumns();
        resetRoad();
        createPlayer();




        createBackground();

        optionsManager = OptionsManager.getInstance();
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


        addSprite(10,  billboard7, -1);
        addSprite(14,  billboard6, -1);
        addSprite(20,  billboard8, -1);
        addSprite(30,  billboard9, -1);



        for (int n = 0; n < Constants.SEGMENTLENGTH; n++) {
            if(n%2==0) {
                addSprite(n, Util.getRandomTexture(plants), 1);
            }

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
            case OPTION:
                optionsManager.update();
                // Tastendruck 'S' zeigt/versteckt die Optionen
                if (IsKeyPressed(KEY_P)) {
                    Game.gameState = GameState.MENU;
                }
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
            case OPTION:
                optionsManager.show = true;
                optionsManager.showBackground();
                break;


        }

        EndDrawing();
    }

    /** Create the player */
    public void createPlayer() {
        player = new Player(Constants.WIDTH / 2, Constants.HEIGHT - 150);
        player.x -= player.texture.width() * 3 / 2;
        playerSprites.add(player.texture);
        SPRITESCALE = (float) (0.3 *  1 /player.straight.width());


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

       for(Sound s : sounds){
           s.playSound();

       }
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

        double playerW  = Game.player.straight.width()*  0.3 * ((double) 1 /Game.player.straight.width());
        for(int n = 0; n < playerSegment.sprites.size(); n++) {
            Sprite sprite = playerSegment.sprites.get(n);
            double spriteW = Game.player.straight.width() * 0.3 * ((double) 1 / Game.player.straight.width());

            // Calculate the sprite's offset based on its position and width
            double spriteOffset = sprite.offset + spriteW / 2 * (sprite.offset > 0 ? 1 : -1);

            if (Util.overlap(playerX, playerW, spriteOffset, spriteW, 0.7)) {
                speed = Constants.MAXSPEED / 5;
                position = Util.increase(playerSegment.p1.world.z, -Constants.PLAYERZ, trackLength);
                // Stop in front of the sprite (at the front of the segment)
                break;
            }
        }


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

         maxY = Constants.HEIGHT;




        var x = 0;
        var dx = -(baseSegment.curve * baseSegmentPercent);

        ClearBackground(RAYWHITE); // Background will be deleted

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

        // Drawing of sprites
        renderSprites();

        for (Texture texture : playerSprites) {
            DrawTexture(texture, player.x, player.y, WHITE);
        }





        optionsManager.showBackground();
        playerSprites.clear();
    }

    private  void initSounds(){
        sounds = new ArrayList<>();
        sounds.add(new Sound(Constants.SOUNDSPATH,"racer"));
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

    public static void renderSprites(Road.Segment segment) {
        if (segment.sprites != null) {
            for (int i = 0; i < segment.sprites.size(); i++) {
                Sprite sprite = segment.sprites.get(i);
                float spriteScale = (float) segment.p1.screen.scale;
                float spriteX = (float) (segment.p1.screen.x + (spriteScale * sprite.offset * Constants.ROADWIDTH * Constants.WIDTH / 2));
                float spriteY = (float) segment.p1.screen.y;

                float offset = (sprite.offset < 0) ? -1 : 0;

                Util.sprite(sprite.texture, Constants.WIDTH, Constants.ROADWIDTH, spriteScale, spriteX, spriteY, offset, (float) -1, Constants.HEIGHT);
            }
        }
    }

    private static void renderSprites() {
        // Iterate through road segments and render sprites
        for (Road.Segment segment : Game.segments) {
            renderSprites(segment);
        }
    }



    /**
     * Adds a sprite to the specified segment at a given position with a texture and offset.
     *
     * This method creates a new Sprite with the provided texture and offset, and adds it
     * to the sprites list of the segment at the specified position in the Game's segments.
     * It is designed for use in a 2D environment, such as a game or graphical application.
     *
     * @param n The position of the segment where the sprite will be added.
     * @param sprite The texture of the sprite to be added.
     * @param offSet The offset of the sprite in a 2D space.
     * @throws IndexOutOfBoundsException If the specified position is out of bounds for the segments list.
     */
    public void addSprite(int n, Texture sprite, float offSet) {
        try {
            // Get the segment at the specified position and add a new sprite to its list
            Game.segments.get(n).sprites.add(new Sprite(sprite, offSet));
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("Invalid segment position: " + n);
        }
    }
}




