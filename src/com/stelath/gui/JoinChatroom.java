package com.stelath.gui;

import com.stelath.networking.ChatStorage;
import com.stelath.networking.Client;
import com.stelath.Chatify;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JoinChatroom {
    // Declare fourm variables
    private JPanel rootPanel;
    private JTextField serverAdressTextFeild;
    private JTextField usernameTextFeild;
    private JButton joinButton;
    private JLabel usernameLabel;
    private JLabel iPLabel;
    private JLabel portLabel;
    private JTextField portTextFeild;

    // Window
    private JFrame frame;

    // Add client variable so we can call on its methods
    public Client client;

    public Chatify chatify;
    public ChatStorage chatStorage;

    public void openJoinChatWindow()
    {
        frame = new JFrame("Join Chatroom");
        frame.setContentPane(rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public JoinChatroom()
    {
        joinButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (client.connectToServer(serverAdressTextFeild.getText(),
                        Integer.parseInt(portTextFeild.getText()),
                        usernameTextFeild.getText(),
                        chatStorage))
                {
                    chatify.clientOrServer = true;
                    client.listenForMessages();
                    frame.dispose();
                }
                else
                {
                    new Popup("Something went wrong, Please try Again!");
                }
            }
        });
    }
}
