import static com.raylib.Jaylib.*;

import com.raylib.Jaylib;

import java.util.ArrayList;

/** The MainMenu class manages the game's main menu options using the Singleton Pattern. */
public class MainMenu {

    // Instance variable for the Singleton Pattern
    private static final MainMenu instance = new MainMenu();

    private Util.Background background;
    private ArrayList<UtilButton> button;

    // Private constructor for the Singleton Pattern
    private MainMenu() {
        button = new ArrayList<>();
        // Create Buttons
        button.add(
                new UtilButton(
                        (float) Constants.WIDTH / 2 - ((float) 250 / 2),
                        (float) Constants.HEIGHT / 2 - ((float) 50 / 2),
                        250,
                        50,
                        "SINGLEPLAYER"));
        button.add(
                new UtilButton(
                        button.get(0).x,
                        button.get(0).y + (button.get(0).height),
                        250,
                        50,
                        "MULTIPLAYER"));
        button.add(
                new UtilButton(
                        button.get(1).x,
                        button.get(1).y + (button.get(0).height),
                        250,
                        50,
                        "EXIT"));

        createBackground();
    }

    /**
     * Returns the sole instance of the MainMenu class.
     *
     * @return Instance of MainMenu
     */
    public static MainMenu getInstance() {
        return instance;
    }

    /** Displays the background window with menu options. */
    public void showBackground() {
        DrawTexture(background.texture, 0, 0, WHITE);
        for (UtilButton b : button) {
            b.draw();
        }
    }

    public void update() {
        checkInput();
    }

    private void checkInput() {
        // Check if singleplayer or multiplayer button is clicked
        if (button.get(0).buttonClicked()) {
            Game.gameState = GameState.SINGLEPLAYER;
        } else if (button.get(1).buttonClicked()) {
            Game.gameState = GameState.MULTIPLAYER;
        } else if (button.get(2).buttonClicked()) {
            System.exit(0);
        }
    }

    public void createBackground() {
        background =
                new Util.Background(
                        LoadTexture(Constants.UITEXTUREPATH + "backgroundMenu.png"), 0, 0);
    }

    class UtilButton {
        private Jaylib.Rectangle size;
        private float x, y, width, height;
        private String text;

        public UtilButton(float x, float y, float width, float height, String text) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.text = text;
            size = new Jaylib.Rectangle(x, y, width, height);
        }

        void draw() {
            GuiButton(size, text);
        }

        public boolean buttonClicked() {
            // Check if the mouse is over the button and left mouse button is pressed
            if (CheckCollisionPointRec(GetMousePosition(), size)) {
                return IsMouseButtonPressed(MOUSE_BUTTON_LEFT);
            }
            return false;
        }
    }
}
