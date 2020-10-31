package com.stelath.networking;

// Import necessary packages
import java.net.*;
import java.io.*;

public class Client
{
    // Declare Global Variables
    private Socket socket;
    private DataInputStream input = null;
    private DataOutputStream out = null;

    private String username = "Unnamed";
    private ChatStorage chatStorage;

    private ServerHandler thread;

    public boolean connectToServer(String address, int port, String username, ChatStorage chatStorage)
    {
        this.username = username;
        this.chatStorage = chatStorage;

        // Establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // Sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());

            // Gets input from socket
            input = new DataInputStream(socket.getInputStream());

            // Sends username to the socket
            sendMessage(username + ", Joined the Chat!", false);

            System.out.println("Connected To: " + address);

            return true;
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
            return false;
        }
        catch(IOException i)
        {
            System.out.println(i);
            return false;
        }
    }

    public void sendMessage(String message, boolean includeUsername)
    {
        // Send Message
        try
        {
            if(includeUsername)
            {
                out.writeUTF(username + ":\n" + message);
            }
            else
            {
                out.writeUTF(message);
            }
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public void listenForMessages()
    {
        if (input != null)
        {
            // Create a new Thread
            thread = new ServerHandler(chatStorage, input);

            // Invoking the start() method
            thread.start();
        }
    }

    public void stopListeningForMessages()
    {
        thread.stop();
    }

    public void closeConnection()
    {
        // Close the connection
        try
        {
            out.writeUTF("EXITING PROGRAM");
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
}

class ServerHandler extends Thread
{
    private ChatStorage chatStorage;
    private DataInputStream input;

    public ServerHandler(ChatStorage chatStorage, DataInputStream input)
    {
        this.chatStorage = chatStorage;
        this.input = input;
    }

    @Override
    public void run()
    {
        while (true)
        {
            try {
                String inputUTF = input.readUTF();
                System.out.println(inputUTF);
                chatStorage.append(inputUTF, true);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
