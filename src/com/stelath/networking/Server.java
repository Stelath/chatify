package com.stelath.networking;
import java.net.*;
import java.io.*;

public class ConnectionHandler()
{
    public ConnectionHandler(Socket clientSocket)
    {

    }
}

public class Server
{
    // Initialize socket and input stream
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private DataInputStream in = null;

    public boolean startServer(int port)
    {
        // Starts server and waits for a connection
        try
        {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            Socket clientSocket = serverSocket.accept();
            Runnable connectionHandler = new ConnectionHandler(clientSocket);
            new Thread(connectionHandler).start();

            // takes input from the client socket
            in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));

            String line = "";

            // reads message from client until "Over" is sent
            while (!line.equals("Over"))
            {
                try
                {
                    line = in.readUTF();
                    System.out.println(line);

                }
                catch(IOException i)
                {
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");

            // close connection
            socket.close();
            in.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
        return false;
    }
}
