package org.example.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


//С помошью интерфейса отслеживать проблему отсуствия подключения контрллера

public class ServerWindow extends JFrame implements ServerView {
    public static final int POX_X = 500;
    public static final int POX_Y = 550;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;

    private JTextField name;
    private JButton btnStart;
    private JButton btnStop;
    private JTextArea log;
    private ServerController serverController;

    public ServerWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POX_X, POX_Y, WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Chat server");

        add(createButtons(), BorderLayout.SOUTH);
        add(createLog(), BorderLayout.CENTER);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverController.onServer();
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverController.offServer();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                serverController.clearServer();

                super.windowClosing(e);
            }
        });

        setVisible(true);
    }

    private JPanel createButtons(){
        JPanel panelButtons = new JPanel(new GridLayout(1,2));
        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");
        panelButtons.add(btnStop);
        panelButtons.add(btnStart);

        return panelButtons;
    }

    private JScrollPane createLog(){
        log = new JTextArea();
        log.setEditable(false);

        return new JScrollPane(log);
    }

    public void setServerController(ServerController serverController){
        this.serverController = serverController;
        serverController.setServerView(this);
    }

    @Override
    public void showMessage(String message) {
        log.append(message + "\n");
    }

    @Override
    public String getLog() {
        return log.getText();
    }

    @Override
    public void loadLog(String logText) {
        log.append(logText);
    }
}
