import java.awt.*;
import javax.swing.*;

public class ClientAppUI extends FabricMethodsForUI{
    private JTextField ipAdressArea; // - Done
    private JTextField setNameArea; // - Done
    private JTextArea messagesArea;
    private JButton sendButton;
    private JButton connectButton; // - Done
    private JButton disconnectButton;
    private JButton changeNameButton;
    private JFrame frame;

    public ClientAppUI() {
        initComponents();
        layoutComponents();
        frame.setVisible(true);
    }

    public void startClient() {

    }

    private void layoutComponents() {
        bottomPanelLayoutComponents();
        topPanelLayoutComponents();
        topPanelLayoutComponents();
    }

    private void bottomPanelLayoutComponents() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JScrollPane messagesPane = new JScrollPane(messagesArea);
        bottomPanel.add(messagesPane, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        frame.add(bottomPanel, BorderLayout.SOUTH);
    }
    private void topPanelLayoutComponents() {
        JPanel topContainerPanel = createTopContainerPanel();
        frame.add(topContainerPanel, BorderLayout.NORTH);
    }

    private JPanel createTopContainerPanel() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        container.add(createIpPanel());
        container.add(createNamePanel());

        return container;
    }

    private JPanel createIpPanel() {
        JPanel ipPanel = new JPanel(new BorderLayout());
        ipPanel.add(ipAdressArea, BorderLayout.CENTER);
        ipPanel.add(connectButton, BorderLayout.WEST);
        ipPanel.add(disconnectButton, BorderLayout.EAST);
        return ipPanel;
    }

    private JPanel createNamePanel() {
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.add(setNameArea, BorderLayout.CENTER);
        namePanel.add(changeNameButton, BorderLayout.EAST);
        return namePanel;
    }


    public void initComponents() {
        frame = setupFrame("Chat", 600, 600);
        sendButton = createButton("Надіслати");
        connectButton = createButton("Приєднатися");
        disconnectButton = createButton("Від'єднатися");
        changeNameButton = createButton("Встановити ім'я");
        setNameArea = createTextField("Введіть ім'я", true);
        messagesArea = createTextArea(true, frame.getWidth(), 70);
        ipAdressArea = createTextField("Введіть ip адрессу сервера", true);
    }
}
