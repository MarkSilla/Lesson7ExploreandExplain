import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.*;

class Product {
    String name;
    double price;
    int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product name: " + name + " - $" + price + " Quantity: " + quantity;
    }
}

class Cart {
    private ArrayList<Product> products;

    public Cart() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void deleteProduct(String name) {
        products.removeIf(product -> product.name.equalsIgnoreCase(name));
    }

    public void sortProducts() {
        Collections.sort(products, Comparator.comparing(product -> product.name));
    }

    public void printCart(JTextArea textArea) {
        if (products.isEmpty()) {
            textArea.setText("The cart is empty.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Product product : products) {
                sb.append(product.toString()).append("\n");
            }
            textArea.setText(sb.toString());
        }
    }
}

public class OnlineCartSystem extends JFrame implements ActionListener {
    private Cart cart;
    private JTextField nameField, priceField, quantityField, deleteField;
    private JTextArea textArea;

    public OnlineCartSystem() {
        cart = new Cart();

        setTitle("Online Cart System");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel for Product Entry
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(new Color(173, 216, 230)); // Light Blue Background

        topPanel.add(new JLabel("Product Name:"));
        nameField = new JTextField(10);
        topPanel.add(nameField);

        topPanel.add(new JLabel("Product Price:"));
        priceField = new JTextField(10);
        topPanel.add(priceField);

        topPanel.add(new JLabel("Product Quantity:"));
        quantityField = new JTextField(10);
        topPanel.add(quantityField);

        JButton addButton = new JButton("Add Product");
        addButton.addActionListener(this);
        addButton.setBackground(new Color(60, 179, 113)); // Medium Sea Green
        addButton.setForeground(Color.WHITE);
        topPanel.add(addButton);

        add(topPanel, BorderLayout.NORTH);

        // Middle Panel for Delete Product
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout());
        middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        middlePanel.setBackground(new Color(255, 240, 245)); // Lavender Blush Background

        middlePanel.add(new JLabel("Delete Product by Name:"));
        deleteField = new JTextField(10);
        middlePanel.add(deleteField);

        JButton deleteButton = new JButton("Delete Product");
        deleteButton.addActionListener(this);
        deleteButton.setBackground(new Color(255, 69, 0)); // Red Orange
        deleteButton.setForeground(Color.WHITE);
        middlePanel.add(deleteButton);

        add(middlePanel, BorderLayout.CENTER);

        // Bottom Panel for Sort, Print Buttons and Text Area
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.setBackground(new Color(240, 255, 240)); // Honeydew Background

        JButton sortButton = new JButton("Sort Products");
        sortButton.addActionListener(this);
        sortButton.setBackground(new Color(70, 130, 180)); // Steel Blue
        sortButton.setForeground(Color.WHITE);
        bottomPanel.add(sortButton);

        JButton printButton = new JButton("Print Cart");
        printButton.addActionListener(this);
        printButton.setBackground(new Color(255, 165, 0)); // Orange
        printButton.setForeground(Color.WHITE);
        bottomPanel.add(printButton);

        add(bottomPanel, BorderLayout.SOUTH);

        textArea = new JTextArea(10, 40);
        textArea.setFont(new Font("Serif", Font.PLAIN, 14));
        textArea.setMargin(new Insets(10, 10, 10, 10));
        textArea.setBorder(BorderFactory.createLineBorder(new Color(169, 169, 169))); // Dark Gray Border
        add(new JScrollPane(textArea), BorderLayout.EAST);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Add Product":
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                cart.addProduct(new Product(name, price, quantity));
                clearFields();
                break;
            case "Delete Product":
                String deleteName = deleteField.getText();
                cart.deleteProduct(deleteName);
                clearFields();
                break;
            case "Sort Products":
                cart.sortProducts();
                cart.printCart(textArea);
                break;
            case "Print Cart":
                cart.printCart(textArea);
                break;
        }
    }

    private void clearFields() {
        nameField.setText("");
        priceField.setText("");
        quantityField.setText("");
        deleteField.setText("");
    }

    public static void main(String[] args) {
        new OnlineCartSystem();
    }
}
