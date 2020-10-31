package com.stelath.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Popup {
    private JPanel rootPanel;
    private JButton okButton;
    private JLabel popupLabel;

    public Popup(String popupText)
    {
        JFrame frame = new JFrame();
        frame.setContentPane(rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        //frame.setUndecorated(true);

        // Set Popup Text
        popupLabel.setText(popupText);

        // Ok Button Clicked
        okButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Dispose of window
                frame.dispose();
            }
        });
    }
}
