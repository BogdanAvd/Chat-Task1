import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class ClientConnectionDialog extends FabricMethodsForUI{

    private JTextField ipAdressArea;
    private JTextField setNameArea;
    private JButton connectButton;
    private JButton cancelButton;
    private JFrame frameConnectionDialog;
    private JLabel ipAdressLabel;
    private JLabel setNameLabel;
    
    public ClientConnectionDialog() {
        initComponents();
        layoutComponents();
        frameConnectionDialog.setVisible(true);
    }

    private void layoutComponents() {
        topPanelLayoutComponents();
        bottomPanelLayoutComponents();
    }

    private void topPanelLayoutComponents() {
        JPanel ipAdressPanel = new JPanel(new FlowLayout());
        ipAdressPanel.add(ipAdressLabel);
        ipAdressPanel.add(ipAdressArea);

        JPanel setNamePanel = new JPanel(new FlowLayout());
        setNamePanel.add(setNameLabel);
        setNamePanel.add(setNameArea);

        JPanel setParametersPanel = new JPanel(new GridLayout(2, 1));
        setParametersPanel.add(ipAdressPanel);
        setParametersPanel.add(setNamePanel);

        frameConnectionDialog.add(setParametersPanel, BorderLayout.NORTH);
    }

    private void bottomPanelLayoutComponents() {
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
        buttonsPanel.add(connectButton);
        buttonsPanel.add(cancelButton);

        frameConnectionDialog.add(buttonsPanel, BorderLayout.SOUTH);
    }

    public void initComponents() {
        frameConnectionDialog = setupFrame("Client Connection Dialog", 300, 400);
        connectButton = createButton("Приєднатися");
        cancelButton = createButton("Вийти");
        setNameArea = createTextField(true);
        ipAdressArea = createTextField( true);
        setNameLabel = createLabel("User name: ");
        ipAdressLabel = createLabel("IP Adress: ");
    }

}
