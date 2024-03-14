package org.example.Server;

import org.example.Client.ClientController;
import org.example.Repository.Saver;
import org.example.WebView;

import java.util.*;

public class ServerController {
    private boolean isServerWorking;
    private final String ID;
    private List<ClientController> clients;
    private WebView serverView;
    private Saver<WebView> serverSaver;
    private List<Saver<WebView>> clientsSaver;
    private boolean clientsLogLoaded = false;

    public ServerController(String ID) {
        this.ID = ID;
        clients = new ArrayList<>();

        clientsSaver = new ArrayList<>();
    }

    public void setServerView(WebView serverView) {
        this.serverView = serverView;

        serverSaver = new Saver<>(ID + "server.chat", "chat", serverView);
        serverSaver.load();
    }

    public boolean sendMessageToServer(ClientController client, String message){
        if(!isServerWorking){
            return false;
        }

        String collectedMessage = client.login() + ": " + message;
        serverView.showMessage(collectedMessage);

        for (int i = 0; i < clients.size(); i++) {
            clients.get(i).getClientView().showMessage(collectedMessage);
        }

        return true;
    }

    public boolean connectFromServer(ClientController client){
        boolean connect = clients.contains(client);
        if(!connect){
            connect = clients.add(client);

            clientsSaver.add(client.getSaverParams());
            if(!clientsLogLoaded) clientsSaver.get(clientsSaver.size() - 1).load();

            client.getClientView().showMessage("Connection success");
            serverView.showMessage(client.login() + " connect to server");
        }

        return connect;
    }

    public boolean removeClient(ClientController client){
        int indexClient = clients.indexOf(client);
        if(indexClient > -1){
            client.getClientView().showMessage("Connection failed");
            serverView.showMessage(client.login() + " disconnect to server");

            clientsSaver.get(indexClient).save();

            clients.remove(indexClient);
            clientsSaver.remove(indexClient);

            return true;
        }

        return false;
    }

    public void onServer(){
        isServerWorking = true;
        serverView.showMessage("Server start");

        for (int i = 0; i < clients.size(); i++) {
            if(clients.get(i).connect()){
                serverView.showMessage("Start communication between " + clients.get(i).login() + " and the server");
            }
        }
    }

    public void offServer(){
        isServerWorking = false;
        serverView.showMessage("Server stop");

        for (int i = 0; i < clients.size(); i++) {
            clients.get(i).stopConnect();
            serverView.showMessage("Stop communication between " + clients.get(i).login() + " and the server");
        }
    }

    public void clearServer(){
        isServerWorking = false;
        serverView.showMessage("Server stop");

        while (!clients.isEmpty()){
            removeClient(clients.get(0));
        }

        serverSaver.save();
    }

    public String getID() {
        return ID;
    }
}
