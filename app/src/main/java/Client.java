import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class Client {

    private static Client instance;

    private String serverAddress;
    private Socket socket;

    private ArrayList<HashMap<String, Double>> currentData;

    private Client()
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

            // event Listener ########################################################################

            socket.on("road", (data) -> {System.out.println("received");});
            socket.on("update", (data)
            -> {
                for(Object dataObject : data) {
                    if(dataObject == null)
                        continue;

                    HashMap<String, Double> dataHashMap = null;

                    JSONObject dataJSONObject = (JSONObject) dataObject;
                    String dataString = dataJSONObject.toString();

                    try {
                        dataHashMap = new Gson().fromJson(dataString, HashMap.class);
                    } catch (JsonSyntaxException e) {
                        System.out.println("conversion to HashMap failed");
                        e.printStackTrace();
                    }

                    if(dataHashMap != null)
                        currentData.add(dataHashMap);
                }
                    // System.out.println("received");
                });
            }
        }
        
        public void sendPlayerData(int player, int position, double x)
        {
            /*
            content:
            1 : carUpdate
            */
    
            HashMap<String, Double> data = new HashMap<>();
            data.put("player", (double) player);
            data.put("position", (double) position);
            data.put("x", x);
            socket.emit("update",data);
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
        ArrayList<HashMap<String, Double>> data = new ArrayList<>();
        data.addAll(currentData);
        // current Data wird gelöscht
        // um eventuel keine Daten zu verlieren,
        // werden nur die Daten gelöscht die auch kopiert wurden

        // Wenns leer ist
        if(data.isEmpty()) {
            // System.out.println("No Data received");
            return null;
        }

        // letzte daten löschen
        currentData.removeAll(data);
        
        // wenns mehr als einer sind(zu viele)
        if(data.size() > 1) {
            // System.out.println("More than one Update, total: " + data.size());
            return data;
        }
        // wenns genau einer sind, wie es sein soll
        else
            return data;
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
