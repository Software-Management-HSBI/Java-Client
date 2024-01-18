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
        if(data != null) {
            for(HashMap<String, Double> singleData : data) {

                // What type of data
                String content = "";
                for(String key : singleData.keySet()) {
                    if(singleData.get(key) == 4.2)
                        content = key;
                }

                System.out.println("Data reveived: " + singleData.keySet());
                switch(content) {
                    case "accept" -> {
                        UtilButton button = playerButtons.get((int) (double) singleData.get("player") - 1);
                        button.setText("NOT READY");
                        button.selected = true;
                        Game.playerThis = (int) (double) singleData.get("player");
                        for(UtilButton otherButton : playerButtons) {
                            otherButton.setSelectable(false);
                        }
                        button.setSelectable(true);
                        break;
                    }

                    case "lock" -> {
                        int playerIndex = (int) (double) singleData.get("player") - 1;
                        UtilButton button = playerButtons.get(playerIndex);
                        button.setSelectable(false);
                        button.setText("Player " + playerIndex + 1 + "NOT READY");

                        OtherPlayer newPlayer = new OtherPlayer(playerIndex + 1);
                        Game.otherPlayers.add(newPlayer);
                        break;
                    }
                    case "unlock" -> {
                        int playerIndex = (int) (double) singleData.get("player") - 1;
                        UtilButton button = playerButtons.get(playerIndex);
                        button.setSelectable(true);
                        button.setText("NOT READY");
                        
                        for(OtherPlayer player : Game.otherPlayers) {
                            if(player.player == playerIndex + 1) {
                                Game.otherPlayers.remove(player);
                                break;
                            }
                            else
                            System.out.println("Keine ahnung Junge(Lobby Manger Zeile 90)");
                        }
                        break;
                    }
                    case "playerReady" -> {
                        int playerIndex = (int) (double) singleData.get("player") - 1;
                        UtilButton button = playerButtons.get(playerIndex);

                        if(playerIndex + 1 == Game.playerThis)
                            button.setText("READY");
                        else {
                            button.setText("Plyer " + playerIndex + 1 + "READY");
                            Game.otherPlayers.get(playerIndex).ready = true;
                        }

                        break;
                    }
                    case "countDown" -> {

                        System.out.println(singleData.get("time"));
                        break;
                    }
                    case "finish" -> {

                        break;
                    }
                }
            }
        }


        // Button Listener
       for(int i = 0; i < playerButtons.size(); i++) {
            playerButtons.get(i).update();
            if(playerButtons.get(i).isSelect()) {
                playerButtons.get(i).setSelect(false);

                if(!playerButtons.get(i).selected) {
                    HashMap<String, Double> output = new HashMap<>();
                    output.put("player", i + 1.0);
                    Game.client.sendLobbyData("register", output);
                    System.out.println("Data send: register," + output);
                }
                else {
                    HashMap<String, Double> output = new HashMap<>();
                    output.put("player", i + 1.0);
                    Game.client.sendLobbyData("playerReady", output);
                    System.out.println("Data send: playerReady," + output);
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

            UtilButton newButton = new UtilButton(
                buttonX,
                buttonY,
                buttonWidth,
                buttonHeight,
                "Player " + i);
            
            playerButtons.add(newButton);
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
