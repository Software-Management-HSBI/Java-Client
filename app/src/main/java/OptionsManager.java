import static com.raylib.Jaylib.*;

import com.raylib.Jaylib;

/** The OptionsManager class manages the game options. Singleton pattern is used. */
public class OptionsManager {

    // Instance variable for the Singleton pattern
    private static final OptionsManager instance = new OptionsManager();

    // Display of options
    boolean show;

    // Game options
    private int roadWidth;
    private int cameraHeight;
    private int drawDistance;
    private int fogDensity;
    private int fieldOfView;


    UtilButton lane1 = new UtilButton(500, 200, 200, 50, "Lane1");
    UtilButton lane2 = new UtilButton(500, 250, 200, 50, "Lane2");
    UtilButton lane3 = new UtilButton(500, 300, 200, 50, "Lane3");
    UtilButton lane4 = new UtilButton(500, 350, 200, 50, "Lane4");


    Jaylib.Rectangle roadWidthSize = new Jaylib.Rectangle(120, 200, 300, 50);
    Jaylib.Rectangle cameraHeightSize = new Jaylib.Rectangle(120, 250, 300, 50);
    Jaylib.Rectangle drawDistanceSize = new Jaylib.Rectangle(120, 300, 300, 50);
    Jaylib.Rectangle fieldOfViewSize = new Jaylib.Rectangle(120, 350, 300, 50);
    Jaylib.Rectangle fogDensitySize = new Jaylib.Rectangle(120, 400, 300, 50);

    Jaylib.Rectangle controllSize = new Jaylib.Rectangle(120, 450, 300, 50);

    // Private constructor for the Singleton pattern
    private OptionsManager() {
        // Default values for options
        roadWidth = Constants.ROADWIDTH;
        cameraHeight = Constants.CAMERAHEIGHT;
        drawDistance = Constants.DRAWDISTANCE;
        fieldOfView = Constants.FOV;
        fogDensity = Constants.FOGDENSITY;
    }

    /**
     * Returns the only instance of the OptionsManager class.
     *
     * @return Instance of OptionsManager
     */
    public static OptionsManager getInstance() {
        return instance;
    }

    /**
     * Updates the options based on user input.
     */
    public void update() {

        // Updates constants based on sliders
        Constants.ROADWIDTH = roadWidth;
        Constants.CAMERAHEIGHT = cameraHeight;
        Constants.DRAWDISTANCE = drawDistance;
        Constants.FOV = fieldOfView;
        Constants.FOGDENSITY = fogDensity;
    }

    /**
     * Displays the background window with options.
     */
    public void showBackground() {
        if (show) {
            updateGui();
            checkButtonWindowSize();
        }
    }

    // Checks if a button in the dropdown window was clicked.
    private void checkButtonWindowSize() {

        if (lane1.buttonClicked()) {
            Constants.LANES = 1;
        }
        if (lane2.buttonClicked()) {
            Constants.LANES = 2;
        }
        if (lane3.buttonClicked()) {
            Constants.LANES = 3;
        }
            if (lane4.buttonClicked()) {
                Constants.LANES = 4;
            }


    }
        // Updates the graphical user interface (GUI) for options.
        private void updateGui () {
            DrawRectangle(
                    0, 0, Constants.WIDTH, Constants.HEIGHT, ColorAlpha(Util.color(0, 0, 0), 0.5f));
            DrawText("Lanes:", 500, 170, 20, GRAY);

            // Updates sliders and buttons
            roadWidth =
                    (int)
                            GuiSlider(
                                    roadWidthSize,
                                    "Road Width: " + roadWidth,
                                    "",
                                    roadWidth,
                                    500,
                                    3000);
            cameraHeight =
                    (int)
                            GuiSlider(
                                    cameraHeightSize,
                                    "Camera Height: " + cameraHeight,
                                    "",
                                    cameraHeight,
                                    500,
                                    5000);
            drawDistance =
                    (int)
                            GuiSlider(
                                    drawDistanceSize,
                                    "Draw Distance: " + drawDistance,
                                    "",
                                    drawDistance,
                                    100,
                                    500);
            fieldOfView =
                    (int)
                            GuiSlider(
                                    fieldOfViewSize,
                                    "Field of View: " + fieldOfView,
                                    "",
                                    fieldOfView,
                                    80,
                                    140);
            fogDensity =
                    (int)
                            GuiSlider(
                                    fogDensitySize,
                                    "Fog Density: " + fogDensity,
                                    "",
                                    fogDensity,
                                    0,
                                    50);

            DrawText("\nPress S for Options\n Press P for Pause", (int) controllSize.x(), (int) controllSize.y(),25,WHITE);


            lane1.draw();
            lane2.draw();
            lane3.draw();
            lane4.draw();

        }
    }
