package org.example.Client;

import org.example.Repository.Repository;
import org.example.Repository.Saver;
import org.example.Server.ServerController;
import org.example.WebView;

public class ClientController {
    private boolean connected = false;
    private final String ID;
    private String login;
    private WebView clientView;
    private ServerController server;

    public ClientController(String ID, String login) {
        this.ID = ID;
        this.login = login;
    }

    public ClientController(String ID) {
        this.ID = ID;
    }

    public void setClientView(WebView clientView) {
        this.clientView = clientView;
        connected = false;
    }
    public void setServerController(ServerController server) {
        this.server = server;
        connect();
    }

    public boolean connect(){
        connected = server.connectFromServer(this);
        return connected;
    }

    public boolean stopConnect(){
        clientView.showMessage("Connection stop");
        connected = false;

        return connected;
    }

    public void sendMessageToServer(String message){
        if(connected){
            boolean sendSuccess = server.sendMessageToServer(this, message);
            if(!sendSuccess){
                clientView.showMessage("Failed to send message");
            }
        }
    }

    public Repository getSaverParams(){
        Repository saver = new Saver();
        saver.setWorkDirectory(ID + ".chat", "chat");
        return saver;
    }

    public void killClient(){
        clientView.showMessage("Connection failed");
        server.removeClient(this);
    }

    public WebView getClientView(){
        return clientView;
    }

    public String login() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
