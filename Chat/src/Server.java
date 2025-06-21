import java.util.*;
import java.io.*;
import java.net.*;

public class Server {
    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private volatile boolean running = false;

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
                if (running) {
                    e.printStackTrace();
                }
                // Если сервер закрыт, выбрасывается исключение - можно выйти из цикла
                break;
            }
        }
    }

    private void passClientToStream(Socket clientSocket) {
        new Thread(new ClientHandler(clientSocket)).start();
    }

    public void stopServerSocket() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}