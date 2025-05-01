package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import users.Admin;
import users.Student;
import auth.User;

public class RegistrationWindow extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JTextField txtClass; // Only for students

    public RegistrationWindow() {
        setTitle("Registration");
        setSize(400, 200);
        setLayout(new GridLayout(4, 2, 5, 5));

        add(new JLabel("Username:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        add(new JLabel("Class (students only):"));
        txtClass = new JTextField();
        add(txtClass);

        JButton btnStudent = new JButton("Register as Student");
        JButton btnAdmin = new JButton("Register as Admin");
        add(btnStudent);
        add(btnAdmin);

        btnStudent.addActionListener(e -> registerStudent());
        btnAdmin.addActionListener(e -> registerAdmin());
    
        // Add this to the RegistrationWindow constructor:
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                new LoginWindow().setVisible(true); // Return to login when closed
            }
        });
    }

    private void registerStudent() {
        boolean success = OnlineExamSystem.registerStudent(
            txtUsername.getText(),
            new String(txtPassword.getPassword()),
            txtClass.getText()
        );
        showRegistrationResult(success);
    }

    private void registerAdmin() {
        boolean success = OnlineExamSystem.registerAdmin(
            txtUsername.getText(),
            new String(txtPassword.getPassword())
        );
        showRegistrationResult(success);
    }

    private void showRegistrationResult(boolean success) {
        if (success) {
            JOptionPane.showMessageDialog(this, "Registration successful!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}