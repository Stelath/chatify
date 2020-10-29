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

    public boolean connectToServer(String address, int port, String username)
    {
        // Establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // Sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());

            // Sends username to the socket
            sendMessage(username);

            System.out.println("Connected To: " + address);
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
        return true;
    }

    public void sendMessage(String message)
    {
        // Send Message
        try
        {
            out.writeUTF(message);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public void closeConnection()
    {
        // Close the connection
        try
        {
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
