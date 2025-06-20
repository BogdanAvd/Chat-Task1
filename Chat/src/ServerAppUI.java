import java.awt.*;
import javax.swing.*;

public class ServerAppUI extends JFrame{
    private JButton startButton;
    private JButton stopButton;
    private JTextArea logArea;
    private JLabel statusLabel;
    private Thread serverThread;
    private Server server;

    
    // Методи створення інтерфейсу Сервера
        
    private void startServer() {
        startButton.setEnabled(false);
        setStatus("запущено");
        appendLog("Сервер запускається...");

        server = new Server();
        serverThread = new Thread(() -> {
            server.startServerSocket();
        });
        serverThread.start();
        appendLog("Сервер запущений!\nЧекаємо нових клієнтів...");
    }

    
    public ServerAppUI() {
        initComponents();
        layoutComponents();
        addListenersForButtons();
        setVisible(true);
    }


    private void stopServer() {
    if (server != null) {
        server.stopServerSocket();
        serverThread.interrupt();
        setStatus("зупинено");
        appendLog("Сервер зупинено.");
        startButton.setEnabled(true);
    }
}
    

    private void initComponents() {
        setupFrame("Вікно керування сервером", 600, 600);
        startButton = createButton("Запустити сервер");
        stopButton = createButton("Зупинити сервер");
        logArea = createTextArea(false);
        statusLabel = createLabel("Статус: зупинено");
    }


    private void layoutComponents() {
        JScrollPane scrollPane = new JScrollPane(logArea);
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(startButton, BorderLayout.WEST);
        topPanel.add(statusLabel, BorderLayout.CENTER);
        topPanel.add(stopButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }


    private void addListenersForButtons() {
        startButton.addActionListener(e -> startServer());
        stopButton.addActionListener(e -> stopServer());
    }


    private void setStatus(String status) {
        statusLabel.setText("Статус: " + status);
    }


    private void appendLog(String message) {
        logArea.append(message + "\n");
    }


    // Фабричні методи
    
    
    private void setupFrame(String title, int width, int heigth) {
        setTitle(title);
        setSize(width, heigth);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    

    private JButton createButton(String textButtom) {
        JButton button = new JButton(textButtom);
        return button;
    }


    private JTextArea createTextArea(boolean editArea) {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(editArea);
        return textArea;
    }


    private JTextArea createArea(String textInArea, boolean editArea) {
        JTextArea textArea = new JTextArea(textInArea);
        textArea.setEditable(editArea);
        return textArea;
    }
    

    private JLabel createLabel(String textLabel) {
        JLabel newLabel = new JLabel(textLabel);
        return newLabel;
    }

}
