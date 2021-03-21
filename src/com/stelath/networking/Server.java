package com.stelath.networking;

import java.io.*;
import java.net.*;

// Server class
public class Server extends Thread
{
    // Instantiate Global Variables
    private String username = "Unnamed";
    private ChatStorage chatStorage;

    private ServerSocket serverSocket;

    private ThreadGroup clientHandlerThreadGroup = new ThreadGroup("ClientHandler");

    public static ClientHandler[] appendThread(ClientHandler[] arr, ClientHandler x)
    {
        int i;

        // create a new array of size n+1
        ClientHandler newarr[] = new ClientHandler[arr.length + 1];

        // insert the elements from
        // the old array into the new array
        // insert all elements till n
        // then insert x at n+1
        for (i = 0; i < (arr.length - 1); i++)
            newarr[i] = arr[i];

        newarr[arr.length] = x;

        return newarr;
    }

    public void sendMessageToClients(String message, boolean includeUsername)
    {
        ClientHandler[] threads = new ClientHandler[clientHandlerThreadGroup.activeCount()];
        while (clientHandlerThreadGroup.enumerate(threads, true ) == threads.length) {
            threads = new ClientHandler[threads.length * 2];
        }

        for (int i = 0; i < (threads.length - 1); i++) {
            if(includeUsername)
            {
                if(sendMessageToClient(threads[i], username + ":\n" + message + "\n"))
                    chatStorage.append(username + ":\n" + message + "\n", true);
            }
            else
            {
                if(sendMessageToClient(threads[i], message + "\n"))
                    chatStorage.append(message + "\n", true);
            }
        }
    }

    private boolean sendMessageToClient(ClientHandler client, String message)
    {
        try
        {
            client.dataOutput.writeUTF(message);
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean startServer(int port, String username, ChatStorage chatStorage)
    {
        // Set Global username variable
        this.username = username;
        this.chatStorage = chatStorage;

        // Server is listening on port passed through function
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void run()
    {
        int threadNumber = 1;

        // Running infinite loop for getting client request
        while (true)
        {
            Socket socket = null;

            try
            {
                // Socket object to receive incoming client requests
                socket = serverSocket.accept();

                System.out.println("A new client is connected : " + socket);

                // Obtaining input and out streams
                DataInputStream dataInput = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutput = new DataOutputStream(socket.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // Create a new thread object
                ClientHandler thread = new ClientHandler(socket, dataInput, dataOutput, Server.this, chatStorage, clientHandlerThreadGroup, threadNumber);

                // Add one to "threadNumber"
                threadNumber++;

                // Invoking the start() method
                thread.start();
            }
            catch (Exception e){
                try {
                    socket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }
}


// ClientHandler class
class ClientHandler extends Thread
{
    final DataInputStream dataInput;
    final DataOutputStream dataOutput;
    final Socket socket;
    final ChatStorage chatStorage;
    final Server server;


    // Constructor
    public ClientHandler(Socket socket, DataInputStream dataInput, DataOutputStream dataOutput, Server server, ChatStorage chatStorage, ThreadGroup tg, int threadNumber)
    {
        super(tg, ("ClientHandlerThread" + threadNumber));

        this.socket = socket;
        this.dataInput = dataInput;
        this.dataOutput = dataOutput;
        this.chatStorage = chatStorage;
        this.server = server;
    }

    @Override
    public void run()
    {
        String received;
        while (true)
        {
            try
            {
                // Recive Text From Client
                // Update text box with message from client
                received = dataInput.readUTF();

                // Send message back to clients
                server.sendMessageToClients(received, false);

                if(received.equals("EXITING PROGRAM"))
                {
                    System.out.println("Client " + this.socket + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.socket.close();
                    System.out.println("Connection closed");
                    break;
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        try
        {
            // Closing resources
            this.dataInput.close();
            this.dataOutput.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}