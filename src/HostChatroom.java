import com.stelath.networking.Server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HostChatroom {
    private JPanel rootPanel;
    private JLabel portLabel;
    private JLabel usernameLabel;
    private JButton hostButton;
    private JTextField usernameTextFeild;
    private JTextField portTextFeild;

    // Window
    private JFrame frame;

    // Add client variable so we can call on its methods
    public Server server;

    public HostChatroom() {
        hostButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (server.startServer(Integer.parseInt(portLabel.getText())))
                {
                    frame.dispose();
                }
                else
                {
                    new Popup("Something went wrong, Please try Again!");
                }
            }
        });
    }

    public void openHostChatWindow()
    {
        frame = new JFrame("Host Chatroom");
        frame.setContentPane(rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
