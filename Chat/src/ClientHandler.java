import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


class ClientHandler implements Runnable {
        private Socket socket;
        private String name;
        private PrintWriter out;
        private static Set<PrintWriter> clientWriters = new HashSet<>();
        private BufferedReader in;

        ClientHandler (Socket socket) {
            this.socket = socket;
        }

        public void startClientHandler() {
            createReaderFromSocket(socket);
            changeName();
            addClientToSet();
            broadcastMessage("До чату приєднався новий учасник! Привітайте " + name + "!");
            showClientMessageForAll();
            closeClient();
        }

        private void changeName() {
            try {
                name = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        public void addClientToSet() {
            createPrintWriter(socket);
            synchronized(clientWriters) {
                clientWriters.add(out);
            }
        }   
        
        private void createPrintWriter(Socket socket) {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void createReaderFromSocket(Socket socket){
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void broadcastMessage(String msg) {
            synchronized(clientWriters) {
                for (PrintWriter writer : clientWriters) {
                    writer.println(msg);
                }
            }
        }

        public void showClientMessageForAll() {
            String msg;
            try {
                while ((msg = in.readLine()) != null) {
                    broadcastMessage(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void closeClient() {
            try {
                socket.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
            synchronized(clientWriters) {
                clientWriters.remove(out);
            }
        }

        
 
        @Override
        public void run() {
            startClientHandler();
        }
        
    }
