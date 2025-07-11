import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    private static final int PORT = 12345;
    private String ipAdress;
    private String name;
    private Scanner scanner;
    private PrintWriter out;

    // === ПУБЛІЧНИЙ ІНТЕРФЕЙС ===

    public void start() {
        while(true){
            ipAdress = keybord("Введіть Ip адрес сервера: ");
            try{
                Socket socket = new Socket(ipAdress, PORT);
                setName(socket);
                getMsgFromServer(socket);
                sendMsgToServer(socket);
                break;
            } catch(UnknownHostException e) {
                System.out.println("Неправильний ip адрес сервера. Спробуйте ще раз.");
            } catch(IOException e) {
                System.out.println("Вибачте, виникла помилка. Спробуйте ще раз.");
            }
        }
    }

    private void sendMsgToServer(Socket socket) {
        String msg;
        initOut(socket);
        while((msg = keybord()) != null) 
            out.println(name + ": " + msg);
    }

    private void getMsgFromServer(Socket socket) {
        new Thread (() -> {
            String serverMsg;
            while ((serverMsg = readFromSocket(socket)) != null) {
                System.out.println(serverMsg);
            }
        }).start();
    }

    private void setName(Socket socket) {
        name = keybord("Введіть своє ім'я: ");
        initOut(socket);
        out.println(name);
    }

    // === ДОПОМІЖНІ МЕТОДИ ===

    private void initOut(Socket socket) {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String keybord(String prompt) {
        System.out.println(prompt);
        scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        return str;
    }

    private String keybord() {
        scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        return str;
    }

    private String readFromSocket(Socket socket) {
        try {
            scanner = new Scanner(socket.getInputStream());
            return scanner.nextLine();
        } catch(IOException | NoSuchElementException e) {
            System.out.println("Server was stopped!");
            return null;
        }
    }
}

