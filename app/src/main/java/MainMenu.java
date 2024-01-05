import static com.raylib.Jaylib.*;

import com.raylib.Jaylib;

/** The MainMenu class manages the game's main menu options using the Singleton Pattern. */
public class MainMenu {

    // Instance variable for the Singleton Pattern
    private static final MainMenu instance = new MainMenu();

    private Util.Background background;

    UtilButton singleplayerButton;
    UtilButton multiplayerButton;
    UtilButton exitButton;

    // Private constructor for the Singleton Pattern
    private MainMenu() {

        // Create Buttons
        singleplayerButton =
                new UtilButton(
                        (float) Constants.WIDTH / 2 - ((float) 250 / 2),
                        (float) Constants.HEIGHT / 2 - ((float) 50 / 2),
                        250,
                        50,
                        "SINGLEPLAYER");
        multiplayerButton =
                new UtilButton(
                        singleplayerButton.x(),
                        singleplayerButton.y() + singleplayerButton.height(),
                        250,
                        50,
                        "MULTIPLAYER");
        exitButton =
                new UtilButton(
                        multiplayerButton.x(),
                        multiplayerButton.y() + multiplayerButton.height(),
                        250,
                        50,
                        "EXIT");

        background =
                new Util.Background(
                        LoadTexture(Constants.UITEXTUREPATH + "backgroundMenu.png"), 0, 0);
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

        singleplayerButton.draw();
        multiplayerButton.draw();
        exitButton.draw();
    }

    /**Check if singleplayer or multiplayer button is clicked*/

    public void checkInput() {

        if (singleplayerButton.buttonClicked()) {
            Game.gameState = GameState.SINGLEPLAYER;
        } else if (multiplayerButton.buttonClicked()) {
            Game.gameState = GameState.MULTIPLAYER;
        } else if (exitButton.buttonClicked()) {
            System.exit(0);
        }
    }




}
