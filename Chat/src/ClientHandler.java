import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ClientHandler {

    // === Поля ===
    private static final Set<ClientHandleObj> clients = new HashSet<>();

    // === Конструктор ===
    public ClientHandler() {
    }

    // === Основний життєвий цикл клієнта ===
    public Optional<ClientHandleObj> handleClient(Socket socket) {
        return startClientHandler(socket);
    }

    private Optional<ClientHandleObj> startClientHandler(Socket socket) {
        Optional<BufferedReader> readerFromSocket = createReaderFromSocket(socket);
        if (readerFromSocket.isPresent()) {
            ClientHandleObj clientHandleObj = new ClientHandleObj();
            BufferedReader reader = readerFromSocket.get();
            Optional<String> optionalName = getName(reader);
            if (optionalName.isPresent()) {
                String name = optionalName.get();
                broadcastMessage("До чату приєднався новий учасник! Привітайте " + name + "!");
                clientHandleObj.setName(name);
            }
            Optional<PrintWriter> printWriterOptional = createPrintWriter(socket);
            if (printWriterOptional.isPresent()) {
                PrintWriter printWriter = printWriterOptional.get();
                clientHandleObj.setSocket(socket);
                clientHandleObj.setIn(reader);
                clientHandleObj.setOut(printWriter);
                clientHandleObj.setAddress(socket.getInetAddress().getHostAddress());
                addClientToSet(clientHandleObj);
                new Thread(() -> {
                    showClientMessageForAll(reader);
                    closeClient(clientHandleObj);
                }).start();
                return Optional.of(clientHandleObj);
            }
        }
        return Optional.empty();
    }

    // === Робота з потоками вводу/виводу ===
    private Optional<BufferedReader> createReaderFromSocket(Socket socket) {
        try {
            return Optional.of(new BufferedReader(new InputStreamReader(socket.getInputStream())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Optional<PrintWriter> createPrintWriter(Socket socket) {
        try {
            return Optional.of(new PrintWriter(socket.getOutputStream(), true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // === Робота з іменем клієнта ===
    private Optional<String> getName(BufferedReader reader) {
        try {
            return Optional.of(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // === Додавання/видалення клієнта зі списку ===
    private void addClientToSet(ClientHandleObj client) {
        synchronized (clients) {
            clients.add(client);
        }
    }

    private void closeClient(ClientHandleObj client) {
        try {
            client.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized (clients) {
            clients.remove(client);
        }
    }

    // === Робота з повідомленнями ===
    private void broadcastMessage(String msg) {
        synchronized (clients) {
            for (ClientHandleObj client : clients) {
                client.getOut().println(msg);
            }
        }
    }

    private void showClientMessageForAll(BufferedReader reader) {
        String msg;
        try {
            while ((msg = reader.readLine()) != null) {
                broadcastMessage(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
