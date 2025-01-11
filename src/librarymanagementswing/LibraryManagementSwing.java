/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package librarymanagementswing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LibraryManagementSwing {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/LibraryDB";
    private static final String DB_USER = "root"; // Replace with your MySQL username
    private static final String DB_PASSWORD = ""; // Replace with your MySQL password
    private Connection connection;

    public LibraryManagementSwing() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        initComponents();
    }

    private void initComponents() {
        JFrame frame = new JFrame("Library Management System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create tabs
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add tabs
        tabbedPane.add("Add Book", createAddBookPanel());
        tabbedPane.add("Issue Book", createIssueBookPanel());
        tabbedPane.add("Return Book", createReturnBookPanel());
        tabbedPane.add("View Books", createViewBooksPanel());

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private JPanel createAddBookPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel titleLabel = new JLabel("Book Title:");
        JTextField titleField = new JTextField();
        JLabel authorLabel = new JLabel("Author:");
        JTextField authorField = new JTextField();
        JButton addButton = new JButton("Add Book");

        panel.add(titleLabel);
        panel.add(titleField);
        panel.add(authorLabel);
        panel.add(authorField);
        panel.add(new JLabel()); // Spacer
        panel.add(addButton);

        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            if (!title.isEmpty() && !author.isEmpty()) {
                try {
                    String query = "INSERT INTO books (title, author) VALUES (?, ?)";
                    PreparedStatement stmt = connection.prepareStatement(query);
                    stmt.setString(1, title);
                    stmt.setString(2, author);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    titleField.setText("");
                    authorField.setText("");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please fill in all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel createIssueBookPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel bookIdLabel = new JLabel("Book ID:");
        JTextField bookIdField = new JTextField();
        JLabel studentLabel = new JLabel("Student Name:");
        JTextField studentField = new JTextField();
        JButton issueButton = new JButton("Issue Book");

        panel.add(bookIdLabel);
        panel.add(bookIdField);
        panel.add(studentLabel);
        panel.add(studentField);
        panel.add(new JLabel()); // Spacer
        panel.add(issueButton);

        issueButton.addActionListener(e -> {
            String bookId = bookIdField.getText();
            String studentName = studentField.getText();
            if (!bookId.isEmpty() && !studentName.isEmpty()) {
                try {
                    String checkQuery = "SELECT * FROM books WHERE book_id = ? AND available = TRUE";
                    PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
                    checkStmt.setInt(1, Integer.parseInt(bookId));
                    ResultSet rs = checkStmt.executeQuery();

                    if (rs.next()) {
                        String issueQuery = "INSERT INTO issued_books (book_id, student_name, issue_date) VALUES (?, ?, CURDATE())";
                        PreparedStatement issueStmt = connection.prepareStatement(issueQuery);
                        issueStmt.setInt(1, Integer.parseInt(bookId));
                        issueStmt.setString(2, studentName);
                        issueStmt.executeUpdate();

                        String updateQuery = "UPDATE books SET available = FALSE WHERE book_id = ?";
                        PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
                        updateStmt.setInt(1, Integer.parseInt(bookId));
                        updateStmt.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Book issued successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        bookIdField.setText("");
                        studentField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Book not available or doesn't exist!", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please fill in all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        return panel;
    }


    public static void main(String[] args) {
        
    }
}