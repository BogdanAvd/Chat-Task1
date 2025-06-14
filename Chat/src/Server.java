import java.util.*;
import java.io.*;
import java.net.*;

public class Server {
    private static Set<PrintWriter> clientWriters = new HashSet<>();
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(12345);
        System.out.println("Server start!");
        while(true) {
            Socket client = server.accept();
            new Thread(new ClientHandler(client)).start();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private String name;
        private PrintWriter out;

        ClientHandler (Socket socket) {
            this.socket = socket;
        }
 
        @Override
        public void run() {
            System.out.println("Новий клієнт приєднався!");
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                synchronized(clientWriters) {
                    clientWriters.add(out);
                } 

                System.out.println("Введіть своє ім'я: ");
                name = in.readLine();

                String message;
                while((message = in.readLine()) != null) {
                    synchronized(clientWriters) {
                        for (PrintWriter writer : clientWriters) {
                            writer.println(name + ": " + message);
                        }
                    }
                }
            } catch(IOException e) {
                System.out.println("Клієнт від'єднався!");
            } finally {
                try {
                    socket.close();
                } catch(IOException e) {
                    e.setStackTrace(null);
                }
                synchronized(clientWriters) {
                    clientWriters.remove(out);
                }
            }
        }
        
    }
}
