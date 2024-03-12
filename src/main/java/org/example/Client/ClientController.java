package org.example.Client;

import org.example.Repository.LogSaver;
import org.example.Server.ServerController;

public class ClientController {
    private boolean connected = false;
    private final String ID;
    private String login;
    private ClientView clientView;
    private ServerController server;

    public ClientController(String ID, String login) {
        this.ID = ID;
        this.login = login;
    }

    public ClientController(String ID) {
        this.ID = ID;
    }

    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
        connected = false;
    }
    public void setServerController(ServerController server) {
        this.server = server;
        connect();
    }

    public boolean connect(){
        return connected = server.connectFromServer(this);
    }

    public boolean stopConnect(){
        connected = false;
        return !connected;
    }

    public void sendMessageToServer(String message){
        if(connected){
            boolean sendSuccess = server.sendMessageToServer(this, message);
            if(!sendSuccess){
                clientView.showMessage("Failed to send message");
            }
        }
    }

    public LogSaver getSaverParams(){
        return new LogSaver<>(ID + ".chat", "chat", clientView);
    }

    public void killClient(){
        clientView.showMessage("Connection failed");
        server.removeClient(this);
    }

    public ClientView getClientView(){
        return clientView;
    }

    public String login() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
