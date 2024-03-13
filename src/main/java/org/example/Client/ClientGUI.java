package org.example.Client;

import org.example.WebView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientGUI extends JFrame implements WebView, Thread.UncaughtExceptionHandler{
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;
    private JTextArea log;
    private JTextField tfLogin;

    private JTextField tfMessage;
    private JButton btnSend;

    private ClientController clientController;

    public ClientGUI() {
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat Client");

        add(createTop(), BorderLayout.NORTH);
        add(createButtons(), BorderLayout.SOUTH);
        add(createLog());

        tfLogin.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                changeName();
                super.focusLost(e);
            }
        });

        tfMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    clientController.sendMessageToServer(tfMessage.getText());
                    tfMessage.setText("");
                }
                super.keyPressed(e);
            }
        });
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientController.sendMessageToServer(tfMessage.getText());
                tfMessage.setText("");
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                clientController.stopConnect();

                clientController.killClient();
                super.windowClosing(e);
            }
        });

        setVisible(true);
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
        this.clientController.setClientView(this);

        tfLogin.setText(clientController.login());
    }

    private JPanel createTop() {
        JPanel panelTob = new JPanel(new GridLayout(2, 1));

        JLabel labelLogin = new JLabel("Login: ");
        tfLogin = new JTextField();

        panelTob.add(labelLogin);
        panelTob.add(tfLogin);

        return panelTob;
    }

    private JPanel createButtons(){
        JPanel panelButton = new JPanel(new BorderLayout());
        tfMessage = new JTextField();
        btnSend = new JButton("Send");

        panelButton.add(tfMessage, BorderLayout.CENTER);
        panelButton.add(btnSend, BorderLayout.EAST);

        return panelButton;
    }

    private JScrollPane createLog(){
        log = new JTextArea();
        log.setEditable(false);

        return new JScrollPane(log);
    }

    @Override
    public void showMessage(String message) {
        log.append(message + "\n");
    }

    public void changeName() {
        clientController.setLogin(tfLogin.getText());
    }

    @Override
    public String getLog() {
        return log.getText();
    }

    @Override
    public void loadLog(String logText) {
        log.append(logText);
    }

    private void showException(Throwable e){
        JOptionPane.showMessageDialog(null, e.getMessage(), e.getMessage(), JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        showException(e);
    }
}
