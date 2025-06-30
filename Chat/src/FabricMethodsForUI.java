import java.awt.*;
import javax.swing.*;

public class FabricMethodsForUI extends JFrame {
    public JTextField createTextField(String text, boolean editField) {
        JTextField textField = new JTextField(text);
        textField.setPreferredSize(new Dimension(150, 25));
        textField.setEditable(editField);

        return textField;
    }

    public JTextField createTextField(boolean editField) {
        JTextField textField = new JTextField();
        textField.setEditable(editField);
        textField.setPreferredSize(new Dimension(150, 25));

        return textField;
    }

    public JLabel createLabel(String textLabel) {
        JLabel newLabel = new JLabel(textLabel);
        return newLabel;
    }

    public JButton createButton(String textButton) {
        JButton button = new JButton(textButton);
        return button;
    }

    public JTextArea createTextArea(boolean editArea, int width, int height) {
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(width, height));
        textArea.setEditable(editArea);
        return textArea;
    }

    public JTextArea createTextArea(String textInArea, boolean editArea, int width, int height) {
        JTextArea textArea = new JTextArea(textInArea);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(width, height));
        textArea.setEditable(editArea);
        return textArea;
    }

    public JFrame setupFrame(String title, int width, int heigth) {
        JFrame frame = new JFrame();

        frame.setTitle(title);
        frame.setSize(width, heigth);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        return frame;
    }
}
