import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import io.socket.client.IO;
import io.socket.client.Socket;

public class Client {

    private static Client instance;

    private String serverAddress;
    private Socket socket;

    private ArrayList<HashMap<String, Double>> currentData;

    private void Client()
    {
        currentData = new ArrayList<HashMap<String, Double>>();
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

            // event Listener

            socket.on("road", (data) -> {System.out.println("received");});
            socket.on("update", (data)
            -> {
                try {
                    // auch wenn es mit try und catch umgeben ist, kommt hier eine Warnung, deswegen wird sie unterdrückt
                    @SuppressWarnings("unchecked")
                    HashMap<String, Double>[] castData = (HashMap<String, Double>[]) data;
                    for(HashMap<String, Double> singleData : castData) {
                        currentData.add(singleData);
                    }

                } catch (ClassCastException e) {
                    System.out.println("Wrong Data Type " + e.getMessage());
                    // Handle the exception or provide appropriate fallback
                }
            });
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
    
    public ArrayList<HashMap<String, Double>> receiveData() {
        ArrayList<HashMap<String, Double>> data = currentData;
        // current Data wird gelöscht
        // um eventuel keine Daten zu verlieren,
        // werden nur die Daten gelöscht die auch kopiert wurden

        for(HashMap<String, Double> singleData : data) {
            currentData.remove(singleData);
        }

        if(data.isEmpty()) {
            System.out.println("No Data received");
            return null;
        }
        else if(data.size() > 1) {
            System.out.println("More than one Update, total: " + data.size());
            return null;
        }
        else
            return data;
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
