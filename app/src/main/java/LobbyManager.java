import static com.raylib.Jaylib.RED;
import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;

import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;
import java.util.ArrayList;

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
    String serverAddress;

    String[] testDaten = new String[4];

    private LobbyManager() {
        //Testdaten für die Buttons.
        testDaten[0] = "223,23121,true";
        testDaten[1] = "4,4r,false";
        testDaten[2] = "33,221,true";
        testDaten[3] = "112,55,true";
        
        lobbyImage = new Util.Background(LoadTexture(Constants.UITEXTUREPATH + "lobby.png"), 0, 0);
        
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
    
    public void update() {
        if (playerButtons != null) {
            for (UtilButton playerButton : playerButtons) {

                playerButton.update();
                if(playerButton.isSelect()){
                    playerButton.setText("READY");
                }
                else{
                    playerButton.setText("NOT READY");
                }
                
            }
        }

        if (allPlayerReady()) {

            // Here we can start the multiplayer session

        }

        // readFileForButtons(testDaten);
    }

    private void createButtons() {
        for (int i = 0; i < playerList.size(); i++) {
            int buttonX = Constants.WIDTH / 2 - (buttonWidth / 2);
            int buttonY = initialButtonY + i * (buttonHeight + buttonSpacing);

            playerButtons.add(
                    new UtilButton(
                            buttonX,
                            buttonY,
                            buttonWidth,
                            buttonHeight,
                            playerList.get(i) + "\n" + "NOT READY"));
        }
    }


    void drawLobby() {
        DrawTexture(lobbyImage.texture, 0, 0, WHITE);

        if (socket != null) {
            DrawText(String.valueOf(socket.connected()), 10, 10, 10, RED);
        }

        if (playerButtons != null) {
            for (UtilButton playerButton : playerButtons) {
                playerButton.drawWithButton();
            }
        }
    }

    boolean allPlayerReady() {
        return playerButtons.get(0).isSelect()
                && playerButtons.get(1).isSelect()
                && playerButtons.get(2).isSelect()
                && playerButtons.get(3).isSelect();
    }

    //Recieve data
    void connectToServer() {
        // Überprüfe, ob die Verbindung nicht bereits besteht
        // System.out.println(socket);
        // System.out.println(socket.connected());

        if (socket != null) {
            if(socket.connected())
                return;
        }

        else {
            // Ersetze "SERVER_IP" und "SERVER_PORT" durch die tatsächliche Adresse und den Port deines Servers
            serverAddress = "http://ec2-18-159-61-52.eu-central-1.compute.amazonaws.com:3000";
            
            // Erstelle eine Socket.IO-Verbindung zum Server
            try {
                socket = IO.socket(serverAddress);
                System.out.println("Connected to Server");
            } catch (URISyntaxException e) {
                System.out.println("Connectiong failed");
                throw new RuntimeException(e);
            }
            
            // Verbinde mit dem Server
            socket.connect();
            socket.on("road", (data) -> {System.out.println("received");});
        }
    }
    
    void serverDisconnect() {
        if (socket != null) {
            if(socket.connected()) {
                socket.disconnect();
                socket = null;
                System.out.println("Disconected from the server");
            }        
        }
    }
    
    void sendTestMessage() {
        // socket.emit("start", "Test Message", (response) -> {System.out.println(response);});
        socket.emit("start", "Test Message");
        System.out.println("Send message");
    }

    void readFileForButtons(String[] data){
        int i = 0;
        for(String s: data){
            if(s.contains("true")){
                playerButtons.get(i).setSelect(true);
            }
            i++;
        }
    }
}
