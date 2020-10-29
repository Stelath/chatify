// Import necisary packages
import com.stelath.networking.Server;
import com.stelath.networking.Client;

import javax.swing.*;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Chatify
{
    // Initiate the Server and Client classes
    private Server server;
    private Client client;

    // Declare form variables
    private JPanel rootPanel;
    private JTextField messageTextFeild;
    private JTextArea chatTextArea;
    private JButton sendButton;
    private JButton joinChatButton;
    private JButton hostChatButton;

    public Chatify()
    {
        joinChatButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                JoinChatroom joinChatroom = new JoinChatroom();
                client = new Client();
                joinChatroom.client = client;
                joinChatroom.openJoinChatWindow();
            }
        });

        hostChatButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                HostChatroom hostChatroom = new HostChatroom();
                server = new Server();
                hostChatroom.server = server;
                hostChatroom.openHostChatWindow();
            }
        });
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Chatify");
        frame.setContentPane(new Chatify().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setMinimumSize(new Dimension(100, 100));
    }
}
