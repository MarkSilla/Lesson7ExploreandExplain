import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

class Registration {
    public static void registerParticipant(String webinar, String name) {
        try (FileWriter writer = new FileWriter(webinar + ".txt", true)) {
            writer.write(name + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    public static void printParticipants(String webinar, JTextArea textArea) {
        try (BufferedReader reader = new BufferedReader(new FileReader(webinar + ".txt"))) {
            String line;
            StringBuilder sb = new StringBuilder();
            sb.append("Participants registered for ").append(webinar).append(":\n");
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            textArea.setText(sb.toString());
        } catch (IOException e) {
            textArea.setText("Error reading from file.");
        }
    }
}

public class RegistrationSystem extends JFrame implements ActionListener {
    private JTextField webinarField, nameField;
    private JTextArea textArea;

    public RegistrationSystem() {
        setTitle("Webinar Registration System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel for Registration
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(new Color(173, 216, 230)); // Light Blue Background

        topPanel.add(new JLabel("Webinar (webinar1/webinar2/webinar3):"));
        webinarField = new JTextField(10);
        topPanel.add(webinarField);

        topPanel.add(new JLabel("Participant Name:"));
        nameField = new JTextField(10);
        topPanel.add(nameField);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        registerButton.setBackground(new Color(60, 179, 113)); // Medium Sea Green
        registerButton.setForeground(Color.WHITE);
        topPanel.add(registerButton);

        add(topPanel, BorderLayout.NORTH);

        // Middle Panel for Print Participants
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout());
        middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        middlePanel.setBackground(new Color(255, 240, 245)); // Lavender Blush Background

        JButton printButton = new JButton("Print Participants");
        printButton.addActionListener(this);
        printButton.setBackground(new Color(70, 130, 180)); // Steel Blue
        printButton.setForeground(Color.WHITE);
        middlePanel.add(printButton);

        add(middlePanel, BorderLayout.CENTER);

        // Bottom Panel for Text Area
        textArea = new JTextArea(10, 40);
        textArea.setFont(new Font("Serif", Font.PLAIN, 14));
        textArea.setMargin(new Insets(10, 10, 10, 10));
        textArea.setBorder(BorderFactory.createLineBorder(new Color(169, 169, 169))); // Dark Gray Border
        add(new JScrollPane(textArea), BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String webinar = webinarField.getText().trim().toLowerCase();
        String name = nameField.getText().trim();

        switch (command) {
            case "Register":
                Registration.registerParticipant(webinar, name);
                textArea.setText("Participant " + name + " registered for " + webinar + ".");
                clearFields();
                break;
            case "Print Participants":
                Registration.printParticipants(webinar, textArea);
                break;
        }
    }

    private void clearFields() {
        webinarField.setText("");
        nameField.setText("");
    }

    public static void main(String[] args) {
        new RegistrationSystem();
    }
}
