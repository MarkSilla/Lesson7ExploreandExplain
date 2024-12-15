import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

class Registration {
    // Register a participant by appending their name to the corresponding webinar file.
    public static void registerParticipant(String webinar, String name) {
        try (FileWriter writer = new FileWriter(webinar + ".txt", true)) {
            if (!name.isEmpty()) {
                writer.write(name.trim() + "\n");
                System.out.println("Successfully registered " + name + " for " + webinar);
            } else {
                System.out.println("No name provided.");
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Print participants by reading from the corresponding webinar file.
    public static void printParticipants(String webinar, JTextArea textArea) {
        File file = new File(webinar + ".txt");
        if (!file.exists()) {
            textArea.setText("No participants registered yet for " + webinar + ".");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            StringBuilder sb = new StringBuilder();
            sb.append("Participants registered for ").append(webinar).append(":\n");
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            if (sb.length() == ("Participants registered for " + webinar + ":\n").length()) {
                sb.append("No participants found.\n");
            }
            textArea.setText(sb.toString());
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
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

        System.out.println("Command: " + command);
        System.out.println("Webinar: " + webinar);
        System.out.println("Participant: " + name);

        switch (command) {
            case "Register":
                if (!webinar.isEmpty() && !name.isEmpty()) {
                    Registration.registerParticipant(webinar, name);
                    textArea.setText("Participant " + name + " registered for " + webinar + ".");
                    clearFields();
                } else {
                    textArea.setText("Please provide both webinar name and participant name.");
                }
                break;
            case "Print Participants":
                if (!webinar.isEmpty()) {
                    Registration.printParticipants(webinar, textArea);
                } else {
                    textArea.setText("Please provide a webinar name.");
                }
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
