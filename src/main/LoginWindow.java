package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import users.Student;
import users.Admin;

public class LoginWindow extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    // Temporary test accounts
    private static final String TEMP_STUDENT_USER = "teststudent";
    private static final String TEMP_STUDENT_PASS = "student123";
    private static final String TEMP_ADMIN_USER = "testadmin";
    private static final String TEMP_ADMIN_PASS = "admin123";

    public LoginWindow() {
        setTitle("Online Exam System - Login");
        setSize(350, 200);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Form panel with GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Username field
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1;
        txtUsername = new JTextField(15);
        formPanel.add(txtUsername, gbc);
        
        // Password field
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        txtPassword = new JPasswordField(15);
        formPanel.add(txtPassword, gbc);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton btnRegister = new JButton("Register");
        JButton btnLogin = new JButton("Login");
        JButton btnCancel = new JButton("Cancel");
        
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnCancel);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Event Handling
        btnLogin.addActionListener(e -> attemptLogin());
        btnCancel.addActionListener(e -> confirmExit());
        btnRegister.addActionListener(e -> {
            dispose();
            new RegistrationWindow().setVisible(true);
        });
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmExit();
            }
        });
        
        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void attemptLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        // Empty credentials check
        if (username.isEmpty() || password.isEmpty()) {
            showError("Username and password cannot be empty");
            return;
        }

        // First check temporary accounts (for testing)
        if (username.equals(TEMP_STUDENT_USER) && password.equals(TEMP_STUDENT_PASS)) {
            // Create a temporary student if not exists
            if (!Student.usernameExists(TEMP_STUDENT_USER)) {
                OnlineExamSystem.registerStudent(TEMP_STUDENT_USER, TEMP_STUDENT_PASS, "Test Class");
            }
            // Login the temporary student
            Student student = OnlineExamSystem.loginStudent(TEMP_STUDENT_USER, TEMP_STUDENT_PASS);
            JOptionPane.showMessageDialog(this, 
                "Logged in as temporary Student\n(Test account - use registration for real accounts)",
                "Login Successful", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new StudentDashboard().setVisible(true);
        } 
        else if (username.equals(TEMP_ADMIN_USER) && password.equals(TEMP_ADMIN_PASS)) {
            // Create a temporary admin if not exists
            if (!Admin.usernameExists(TEMP_ADMIN_USER)) {
                OnlineExamSystem.registerAdmin(TEMP_ADMIN_USER, TEMP_ADMIN_PASS);
            }
            // Login the temporary admin
            Admin admin = OnlineExamSystem.loginAdmin(TEMP_ADMIN_USER, TEMP_ADMIN_PASS);
            JOptionPane.showMessageDialog(this, 
                "Logged in as temporary Admin\n(Test account - use registration for real accounts)",
                "Login Successful", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new AdminDashboard().setVisible(true);
        }
        // Then check real registered accounts
        else {
            // Try student login
            Student student = OnlineExamSystem.loginStudent(username, password);
            if (student != null) {
                JOptionPane.showMessageDialog(this, 
                    "Logged in as Student: " + student.getUsername(),
                    "Login Successful", 
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new StudentDashboard().setVisible(true);
                return;
            }
        
            // Try admin login
            Admin admin = OnlineExamSystem.loginAdmin(username, password);
            if (admin != null) {
                JOptionPane.showMessageDialog(this, 
                    "Logged in as Admin: " + admin.getUsername(),
                    "Login Successful", 
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new AdminDashboard().setVisible(true);
                return;
            }
        
            // If neither worked
            showError("Invalid username or password\nTry temporary:\nStudent: teststudent/student123\nAdmin: testadmin/admin123");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, 
            message,
            "Login Error",
            JOptionPane.ERROR_MESSAGE);
    }

    private void confirmExit() {
        int choice = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to exit?",
            "Confirm Exit",
            JOptionPane.YES_NO_OPTION);
            
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}