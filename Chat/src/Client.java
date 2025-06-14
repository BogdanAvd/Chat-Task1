import java.util.*;
import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws UnknownHostException, IOException {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введіть IP адрес сервера: ");
        String serverIp = console.readLine();

       Socket socket = new Socket(serverIp, 12345); // localhost встановлений для тестування
    
       new Thread(() -> {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {}
        }).start();

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        String input;
        while((input = console.readLine()) != null) {
            out.println(input);
        }
       }
    } 

