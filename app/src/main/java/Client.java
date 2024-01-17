import java.net.URISyntaxException;
import java.util.HashMap;

import io.socket.client.IO;
import io.socket.client.Socket;

public class Client {

    private static Client instance;

    private String serverAddress;
    private Socket socket;


    private void Client()
    {
        socket.on("road", (data) -> {System.out.println("received");});
    }

    public static Client getInstance() {
        if(instance == null)
            instance = new Client();
        return instance;
    }

    public void connectToServer() {
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
        }
    }
    
    public void serverDisconnect() {
        if (socket != null) {
            if(socket.connected()) {
                socket.disconnect();
                socket = null;
                System.out.println("Disconected from the server");
            }        
        }
    }

    public boolean isConnected() {
        if(socket != null)
            return socket.connected();
        else
            return false;
    }
    
    public void sendTestMessage() {
        // socket.emit("start", "Test Message", (response) -> {System.out.println(response);});
        socket.emit("start", "Test Message");
        System.out.println("Send message");
    }
    
    public HashMap<String, Integer> receiveData() {

    }

    public void sendPlayerData(int position, double x)
    {
        /*
        content:
        1 : carUpdate
        */
        HashMap<String, Double> data = new HashMap<>();
        data.put("content", 1.0);
        data.put("position", (double) position);
        data.put("x", x);
        socket.emit("update ",data);
    }


    // void readFileForButtons(String[] data){
    //     int i = 0;
    //     for(String s: data){
    //         if(s.contains("true")){
    //             playerButtons.get(i).setSelect(true);
    //         }
    //         i++;
    //     }
    // }


}
