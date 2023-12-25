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

    // Rectangles for GUI elements
    Jaylib.Rectangle laneTitle = new Jaylib.Rectangle(500, 150, 200, 50);
    Jaylib.Rectangle laneButtonSize1 = new Jaylib.Rectangle(500, 200, 200, 50);
    Jaylib.Rectangle laneButtonSize2 = new Jaylib.Rectangle(500, 250, 200, 50);
    Jaylib.Rectangle laneButtonSize3 = new Jaylib.Rectangle(500, 300, 200, 50);
    Jaylib.Rectangle laneButtonSize4 = new Jaylib.Rectangle(500, 350, 200, 50);

    Jaylib.Rectangle roadWidthSize = new Jaylib.Rectangle(120, 200, 300, 50);
    Jaylib.Rectangle cameraHeightSize = new Jaylib.Rectangle(120, 250, 300, 50);
    Jaylib.Rectangle drawDistanceSize = new Jaylib.Rectangle(120, 300, 300, 50);
    Jaylib.Rectangle fieldOfViewSize = new Jaylib.Rectangle(120, 350, 300, 50);
    Jaylib.Rectangle fogDensitySize = new Jaylib.Rectangle(120, 400, 300, 50);

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

    /** Updates the options based on user input. */
    public void update() {

        // Updates constants based on sliders
        Constants.ROADWIDTH = roadWidth;
        Constants.CAMERAHEIGHT = cameraHeight;
        Constants.DRAWDISTANCE = drawDistance;
        Constants.FOV = fieldOfView;
        Constants.FOGDENSITY = fogDensity;
    }

    /** Displays the background window with options. */
    public void showBackground() {
        if (show) {
            updateGui();
            checkButtonWindowSize();
        }
    }

    // Checks if a button in the dropdown window was clicked.
    private void checkButtonWindowSize() {
        if (CheckCollisionPointRec(GetMousePosition(), laneButtonSize1)) {
            if (IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
                Constants.LANES = 1;
            }
        } else if (CheckCollisionPointRec(GetMousePosition(), laneButtonSize2)) {
            if (IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
                Constants.LANES = 2;
            }
        } else if (CheckCollisionPointRec(GetMousePosition(), laneButtonSize3)) {
            if (IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
                Constants.LANES = 3;
            }
        } else if (CheckCollisionPointRec(GetMousePosition(), laneButtonSize4)) {
            if (IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
                Constants.LANES = 4;
            }
        }
    }

    // Updates the graphical user interface (GUI) for options.
    private void updateGui() {
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

        GuiButton(laneButtonSize1, "Lane 1");
        GuiButton(laneButtonSize2, "Lane 2");
        GuiButton(laneButtonSize3, "Lane 3");
        GuiButton(laneButtonSize4, "Lane 4");
    }
}
