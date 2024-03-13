package org.example.Server;

import org.example.Client.ClientController;
import org.example.Client.ClientView;
import org.example.Repository.LogSaver;

import java.util.*;

public class ServerController {
    private boolean isServerWorking;
    private final String ID;
    private List<ClientController> clients;
    private ServerView serverView;
    private LogSaver<ServerView> serverSaver;
    private List<LogSaver<ClientView>> clientsSaver;
    private boolean clientsLogLoaded = false;

    public ServerController(String ID) {
        this.ID = ID;
        clients = new ArrayList<>();

        clientsSaver = new ArrayList<>();
    }

    public void setServerView(ServerView serverView) {
        this.serverView = serverView;

        serverSaver = new LogSaver<>(ID + "server.chat", "chat", serverView);
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
        }

        return connect;
    }

    public boolean removeClient(ClientController client){
        int indexClient = clients.indexOf(client);
        if(indexClient > -1){
            client.getClientView().showMessage("Connection failed");
            clientsSaver.get(indexClient).save();
            serverView.showMessage(client.login() + " disconnect to server");

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
                serverView.showMessage(clients.get(i).login() + " connect to server");

                if(!clientsLogLoaded) clientsSaver.get(i).load();
                clients.get(i).getClientView().showMessage("Connection success");
            }
        }
    }

    public void offServer(){
        isServerWorking = false;
        serverView.showMessage("Server stop");

        for (int i = 0; i < clients.size(); i++) {
            if(clients.get(i).stopConnect()){
                serverView.showMessage(clients.get(i).login() + " stop connect to server");
                clients.get(i).getClientView().showMessage("Connection stop");
            }
        }
    }

    public void clearServer(){
        isServerWorking = false;
        serverView.showMessage("Server stop");

        while (!clients.isEmpty()){
            removeClient( clients.get(0));
        }

        serverSaver.save();
    }
}
