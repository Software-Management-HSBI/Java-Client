import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.util.ArrayList;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;


public class LobbyManager {
    private static LobbyManager instance;
    private final int buttonWidth;
    private final int buttonHeight;
    private final int buttonSpacing;
    private final int initialButtonY;
    private Raylib.Texture lobbyImage;
    private ArrayList<String> playerList;
    private ArrayList<UtilButton> playerButtons;



    private LobbyManager() {

        // TODO: Hier wird dann statt dieser Liste eine Liste aller Spieler vom Server genommen
        playerList = new ArrayList<>();
        playerList.add("Spieler 1");
        playerList.add("Spieler 2");
        playerList.add("Spieler 3");
        playerList.add("Spieler 4");

        playerButtons = new ArrayList<>();


        // Erstelle Bereitschafts-Buttons für jeden Spieler
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
            int buttonX = (200 - buttonWidth) / 2;
            int buttonY = initialButtonY + i * (buttonHeight + buttonSpacing);


            playerButtons.add(new UtilButton(buttonX, buttonY, buttonWidth, buttonHeight,
                    playerList.get(i) + "\n PLAYER1"));

            playerButtons.add(new UtilButton(buttonX, buttonY, buttonWidth, buttonHeight,
                    playerList.get(i) + "\n PLAYER2"));


            playerButtons.add(new UtilButton(buttonX, buttonY, buttonWidth, buttonHeight,
                    playerList.get(i) + "\n PLAYER3"));

            playerButtons.add(new UtilButton(buttonX, buttonY, buttonWidth, buttonHeight,
                    playerList.get(i) + "\n PLAYER4"));
        }
    }


    public void update(){

        if(playerButtons!=null) {
            for (int i = 0; i < playerButtons.size(); i++) {
                if (playerButtons.get(i).buttonClicked()) {
                    System.out.println(playerList.get(i) + "READY");
                    playerButtons.get(i).setColor(Jaylib.GREEN);
                } else {
                    System.out.println(playerList.get(i) + "NOT READY");
                    playerButtons.get(i).setColor(Jaylib.GREEN);
                }
            }
        }
    }

    void drawLobby(){

        if(playerButtons!=null) {
            for (int i = 0; i < playerButtons.size(); i++) {
                playerButtons.get(i).drawWithButton();
            }
        }

    }

    boolean allPlayerReady(){

        for(int i =0;i<playerButtons.size();i++){
           return playerButtons.get(i).buttonClicked();
        }
        return false;

    }



}





