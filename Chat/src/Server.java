import java.util.*;
import java.io.*;
import java.net.*;

public class Server {
    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private volatile boolean running = false;
    private ServerAppUI ui;
    private ArrayList<ClientHandler> handlers = new ArrayList<>();

    public Server(ServerAppUI ui) {
        this.ui = ui;
    }
    
    public void startServerSocket() {
        try {
            serverSocket = new ServerSocket(PORT);
            running = true;
            System.out.println("Server start!");
            startAcceptClients();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void startAcceptClients() {
        while (running) {
            try {
                clientSocket = serverSocket.accept();
                passClientToStream(clientSocket);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void passClientToStream(Socket clientSocket) {
        ClientHandler handler = new ClientHandler(clientSocket, ui);
        handlers.add(handler);
        Thread thread = new Thread(handler);
        thread.start();
    }

    public void stopServerSocket() {
        running = false;
        try {
            for(ClientHandler handler : handlers) {
                handler.closeClient();
            }
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}