import java.util.*;
import java.io.*;
import java.net.*;

public class Server {
    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private Socket clientSocket;

    private boolean serverStatusFlag = true;
    
    private final ClientHandler clientHandler = new ClientHandler();

    public void startServerSocket() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server start!");
        startAcceptClients();
    }

    private void startAcceptClients() {
        while(serverStatusFlag) {   
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            passClientToStream(clientSocket);
        }
    }

    private void passClientToStream(Socket clientSocket) {
        clientHandler.handleClient(clientSocket);
    }
    
    public void stopServerSocket() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverStatusFlag = false;
                serverSocket.close();
                System.out.println("Server stopped!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
