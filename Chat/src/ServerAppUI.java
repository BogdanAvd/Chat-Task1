import java.awt.*;
import javax.swing.*;

public class ServerAppUI extends FabricMethodsForUI{
    private JButton startButton;
    private JButton stopButton;
    private JTextArea logArea;
    private JLabel statusLabel;
    private Thread serverThread;
    private Server server;
    private JFrame frame;
    
    // Методи створення інтерфейсу Сервера
        
    private void startServer() {
        startButton.setEnabled(false);
        setStatus("запущено");
        appendLog("Сервер запускається...");

        server = new Server(this);
        serverThread = new Thread(() -> {
            server.startServerSocket();
        });
        serverThread.start();
        appendLog("Сервер запущений!\nЧекаємо нових клієнтів...");
    }

    public void notifyNewUser(String name) {
        if (name != null) {
            appendLog("Приєднався новий учасник: " + name);
        }
    }
    
    public ServerAppUI() {
        initComponents();
        layoutComponents();
        addListenersForButtons();
        frame.setVisible(true);
    }


    private void stopServer() {
    if (server != null) {
        server.stopServerSocket();
        setStatus("зупинено");
        appendLog("Сервер зупинено.");
        startButton.setEnabled(true);
    }
}
    

    private void initComponents() {
        frame = setupFrame("Вікно керування сервером", 600, 600);
        startButton = createButton("Запустити сервер");
        stopButton = createButton("Зупинити сервер");
        logArea = createTextArea(false, frame.getHeight(), frame.getWidth());
        statusLabel = createLabel("Статус: зупинено");
    }


    private void layoutComponents() {
        JScrollPane scrollPane = new JScrollPane(logArea);
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(startButton, BorderLayout.WEST);
        topPanel.add(statusLabel, BorderLayout.CENTER);
        topPanel.add(stopButton, BorderLayout.EAST);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
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

}
