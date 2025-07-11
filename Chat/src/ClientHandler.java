import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ClientHandler implements Runnable {

    // === Поля ===
    private Socket socket;
    private String name;
    private PrintWriter out;
    private BufferedReader in;
    private static final Set<PrintWriter> clientWriters = new HashSet<>();
    private ServerAppUI ui;

    // === Конструктор ===
    public ClientHandler(Socket socket, ServerAppUI ui) {
        this.socket = socket;
        this.ui = ui;
    }

    // === Основний життєвий цикл клієнта ===
    @Override
    public void run() {
        startClientHandler();
    }

    private void startClientHandler() {
        createReaderFromSocket(socket);
        changeName();
        addClientToSet();
        ui.notifyNewUser(name);
        broadcastMessage("До чату приєднався новий учасник! Привітайте " + name + "!");
        showClientMessageForAll();
        closeClient();
    }
    
    // === Робота з потоками вводу/виводу ===
    private void createReaderFromSocket(Socket socket) {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createPrintWriter(Socket socket) {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // === Робота з іменем клієнта ===
    private void changeName() {
        try {
            name = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // === Додавання/видалення клієнта зі списку ===
    private void addClientToSet() {
        createPrintWriter(socket);
        synchronized (clientWriters) {
            clientWriters.add(out);
        }
    }

    public void closeClient() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized (clientWriters) {
            clientWriters.remove(out);
        }
    }

    // === Робота з повідомленнями ===
    private void broadcastMessage(String msg) {
        synchronized (clientWriters) {
            for (PrintWriter writer : clientWriters) {
                writer.println(msg);
            }
        }
    }

    private void showClientMessageForAll() {
        String msg;
        try {
            while ((msg = in.readLine()) != null) {
                broadcastMessage(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
