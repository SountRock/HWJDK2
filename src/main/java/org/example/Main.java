package org.example;

import org.example.Client.ClientController;
import org.example.Client.ClientGUI;
import org.example.Server.ServerController;
import org.example.Server.ServerWindow;

public class Main {
    public static void main(String[] args) {
        //создание объектов сервера и создание связи между ними
        ServerWindow serverWindow = new ServerWindow();
        ServerController serverController = new ServerController("server1");
        serverController.setServerView(serverWindow);
        serverWindow.setServerController(serverController);

        //создание объектов клиента1 и создание связи между ними
        ClientGUI clientGUI1 = new ClientGUI();
        ClientController clientController1 = new ClientController("A51","Alex51");
        clientController1.setClientView(clientGUI1);
        clientGUI1.setClientController(clientController1);
        clientController1.setServerController(serverController);

        //создание объектов клиента2 и создание связи между ними
        ClientGUI clientGUI2 = new ClientGUI();
        ClientController clientController2 = new ClientController("ML","MariaLy");
        clientController2.setClientView(clientGUI2);
        clientGUI2.setClientController(clientController2);
        clientController2.setServerController(serverController);
    }
}