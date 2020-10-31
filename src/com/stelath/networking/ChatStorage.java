package com.stelath.networking;

import javax.swing.*;

public class ChatStorage
{
    private JTextArea chatBox;
    public String[] chat = new String[0];

    public void append(String stringToAppend, boolean update)
    {
        int i;

        String newarr[] = new String[chat.length + 1];

        for (i = 0; i < (chat.length - 1); i++)
            newarr[i] = chat[i];

        newarr[chat.length] = stringToAppend;

        chat = newarr;

        if (update)
            chatBox.append(stringToAppend);
    }

    public String readAsString()
    {
        String returnString = "";
        for(int i = 0; i < (chat.length - 1); i++)
        {
            returnString = chat[i] + returnString;
        }
        return returnString;
    }

    public ChatStorage(JTextArea chat)
    {
        chatBox = chat;
    }
}
