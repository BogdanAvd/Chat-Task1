import java.util.*;
import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws UnknownHostException, IOException {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введіть IP адрес сервера: ");
        String serverIp = console.readLine();

        try {
            Socket socket = new Socket(serverIp, 12345);

            new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    // обработка ошибок
                }
            }).start();

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Введіть своє ім'я: ");
            String name = console.readLine();
            out.println(name);
            String input;
            while((input = console.readLine()) != null) {
                out.println(input);
            }
        } catch (UnknownHostException e) {
            System.out.println("Неправельний IP адрес.\nСпробуйте ще раз.");
            main(args); // Перезапуск клієнта
        }
    }
}

