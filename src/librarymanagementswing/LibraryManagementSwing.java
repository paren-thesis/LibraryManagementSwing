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

    public static void main(String[] args) {
        
    }
}