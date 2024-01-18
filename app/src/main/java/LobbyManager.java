import static com.raylib.Jaylib.RED;
import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;

import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

public class LobbyManager {
    private static LobbyManager instance;
    private final int buttonWidth;
    private final int buttonHeight;
    private final int buttonSpacing;
    private final int initialButtonY;
    private final Util.Background lobbyImage;
    private final ArrayList<UtilButton> playerButtons;

    String serverAddress;

    private LobbyManager() {
        
        lobbyImage = new Util.Background(LoadTexture(Constants.UITEXTUREPATH + "lobby.png"), 0, 0);
        
        playerButtons = new ArrayList<>();
        buttonWidth = 200;
        buttonHeight = 50;
        buttonSpacing = 20;
        initialButtonY = 50;
        
        createButtons();
    }
    
    public static LobbyManager getInstance() {
        if (instance == null) {
            instance = new LobbyManager();
        }
        return instance;
    }
    
    public void update() {

        ArrayList<HashMap<String, Double>> data = Game.client.receiveLobbyData();
        for(HashMap<String, Double> singleData : data) {

            // What type of data
            String content = "";
            for(String key : singleData.keySet()) {
                if(singleData.get(key) == 4.2)
                    content = key;
            }

            switch(content) {
                case "accept" -> {
                    
                    break;
                }

                case "lock" -> {

                    break;
                }
                case "unlock" -> {

                    break;
                }
                case "playerReady" -> {

                    break;
                }
                case "countDown" -> {

                    break;
                }
                case "finish" -> {

                    break;
                }
            }
        }

        // if (playerButtons != null) {
            // for (UtilButton playerButton : playerButtons) {

            //     playerButton.update();
            //     if(playerButton.isSelect()){
            //         playerButton.setText("READY");
            //     }
            //     else{
            //         playerButton.setText("NOT READY");
            //     }
                
            // }
        // }

        // if (allPlayerReady()) {

            // Here we can start the multiplayer session

        // }

        // readFileForButtons(testDaten);
    }

    private void createButtons() {
        for (int i = 1; i <= 4; i++) {
            int buttonX = Constants.WIDTH / 2 - (buttonWidth / 2);
            int buttonY = initialButtonY + i * (buttonHeight + buttonSpacing);

            playerButtons.add(
                    new UtilButton(
                            buttonX,
                            buttonY,
                            buttonWidth,
                            buttonHeight,
                            "Player " + i));
        }
    }

    void drawLobby() {
        DrawTexture(lobbyImage.texture, 0, 0, WHITE);

        // if (Game.client != null) {
            DrawText(String.valueOf(Game.client.isConnected()), 10, 10, 10, RED);
        // }

        if (playerButtons != null) {
            for (UtilButton playerButton : playerButtons) {
                playerButton.drawWithButton();
            }
        }
    }

    boolean allPlayerReady() {
        return false;
    }

}
