import java.util.*;
import java.io.*;
import java.net.*;

public class Server {
    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private Socket clientSocket;

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
        while(true) {   
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            passClientToStream(clientSocket);
        }
    }

    private void passClientToStream(Socket clientSocket) {
        new Thread(new ClientHandler(clientSocket)).start();
    }
}
