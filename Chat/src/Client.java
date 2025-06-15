import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private static final int PORT = 12345;
    private String ipAdress;
    private String name;
    Scanner scanner;
    PrintWriter out;

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

    public void sendMsgToServer(Socket socket) {
        String msg;
        initOut(socket);
        while((msg = keybord()) != null) 
            out.println(name + ": " + msg);
    }

    public void getMsgFromServer(Socket socket) {
        new Thread (() -> {
            String serverMsg;
            try {
                while ((serverMsg = readFromSocket(socket)) != null) {
                    System.out.println(serverMsg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void setName(Socket socket) {
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

    public String keybord(String prompt) {
        System.out.println(prompt);
        scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        return str;
    }

    public String keybord() {
        scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        return str;
    }

    public String readFromSocket(Socket socket) throws IOException {
        scanner = new Scanner(socket.getInputStream());
        return scanner.nextLine();
    }
}

