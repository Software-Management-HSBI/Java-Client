import static com.raylib.Jaylib.*;

import com.raylib.Jaylib;

/** The MainMenu class manages the game's main menu options using the Singleton Pattern. */
public class MainMenu {

    // Instance variable for the Singleton Pattern
    private static final MainMenu instance = new MainMenu();

    private Util.Background background;

    UtilButton backButton;
    OptionsManager optionsManager;
    UtilButton singleplayerButton;
    UtilButton multiplayerButton;
    UtilButton exitButton;

    UtilButton optionButton;

    // Private constructor for the Singleton Pattern
    private MainMenu() {

        optionsManager = OptionsManager.getInstance();
        // Create Buttons


        singleplayerButton =
                new UtilButton(50,50,200,50,"Start");


        multiplayerButton =
                new UtilButton(50,Constants.HEIGHT-100,200,50,"Multiplayer");

        exitButton = new UtilButton(Constants.WIDTH-250,Constants.HEIGHT-100,200,50,"Beenden");

        background =
                new Util.Background(
                        LoadTexture(Constants.UITEXTUREPATH + "backgroundMenu.png"), 0, 0);

        optionButton =
                new UtilButton(Constants.WIDTH-250,50,200,50,"Options");
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
        optionButton.draw();
        multiplayerButton.draw();
        exitButton.draw();
    }

    /**Check if singleplayer or multiplayer button is clicked*/

    public void checkInput() {

        if (singleplayerButton.buttonClicked()) {
            Game.gameState = GameState.SINGLEPLAYER;
            optionsManager.show = false;
        } else if (multiplayerButton.buttonClicked()) {
            Game.gameState = GameState.MULTIPLAYER;
        } else if (exitButton.buttonClicked()) {
            System.exit(0);
        }else if(optionButton.buttonClicked()){
            Game.gameState = GameState.OPTION;
        }
    }




}
