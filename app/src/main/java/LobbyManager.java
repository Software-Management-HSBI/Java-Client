import io.socket.client.Socket;

import java.util.ArrayList;

import static com.raylib.Jaylib.RED;
import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;

public class LobbyManager {
    private static LobbyManager instance;
    private final int buttonWidth;
    private final int buttonHeight;
    private final int buttonSpacing;
    private final int initialButtonY;
    private final Util.Background lobbyImage;
    private final ArrayList<String> playerList;
    private final ArrayList<UtilButton> playerButtons;



    private Socket socket;

    private LobbyManager() {
        lobbyImage = new Util.Background(
                LoadTexture(Constants.UITEXTUREPATH + "lobby.png"), 0, 0);

        playerList = new ArrayList<>();
        playerList.add("Spieler 1");
        playerList.add("Spieler 2");
        playerList.add("Spieler 3");
        playerList.add("Spieler 4");

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

    private void createButtons() {
        for (int i = 0; i < playerList.size(); i++) {
            int buttonX = Constants.WIDTH / 2 - (buttonWidth / 2);
            int buttonY = initialButtonY + i * (buttonHeight + buttonSpacing);

            playerButtons.add(new UtilButton(buttonX, buttonY, buttonWidth, buttonHeight,
                    playerList.get(i) + "\n" + "NOT READY"));
        }
    }

    public void update() {
        if (playerButtons != null) {
            for (UtilButton playerButton : playerButtons) {
                playerButton.update();
                playerButton.setText(playerButton.isSelect() ? "READY" : "NOT READY");
            }
        }

        if (allPlayerReady()) {

            // Here we can start the multiplayer session

        }

        GameState currentGameState = Game.gameState;
        if (currentGameState == GameState.LOBBY && socket != null && !socket.connected()) {
            connectToServer();
        } else if (currentGameState != GameState.LOBBY && socket != null && socket.connected()) {
            serverDisconnect();
        }
    }

    void drawLobby() {
        DrawTexture(lobbyImage.texture, 0, 0, WHITE);

        if(socket!=null){
            DrawText(String.valueOf(socket.connected()),10,10,10, RED);
        }

        if (playerButtons != null) {
            for (UtilButton playerButton : playerButtons) {
                playerButton.drawWithButton();
            }
        }
    }

    boolean allPlayerReady() {
        return playerButtons.get(0).isSelect() &&
                playerButtons.get(1).isSelect() &&
                playerButtons.get(2).isSelect() &&
                playerButtons.get(3).isSelect();
    }

    void connectToServer() {
        // Überprüfe, ob die Verbindung nicht bereits besteht
        if (socket != null && !socket.connected()) {
            socket.on(Socket.EVENT_CONNECT, args -> {
                System.out.println("Verbunden mit dem Socket.IO-Server");
                sendToServer();
            });
            socket.connect();
        }
    }

    void sendToServer() {
        // Beispiel socket.emit("NachrichtVomClient", "Hallo, Server!");
    }

    void serverDisconnect() {
        if (socket != null && socket.connected()) {
            socket.disconnect();
        }
    }
}
