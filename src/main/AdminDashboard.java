package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminDashboard extends JFrame {
    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnCreateExam = new JButton("Change Exam");
        JButton btnAddQuestion = new JButton("Add Question");
        JButton btnViewResults = new JButton("View Results");
        JButton btnLogout = new JButton("Logout");

        add(btnCreateExam);
        add(btnAddQuestion);
        add(btnViewResults);
        add(btnLogout);

        btnCreateExam.addActionListener(e -> createExam());
        btnAddQuestion.addActionListener(e -> addQuestion());
        btnViewResults.addActionListener(e -> viewResults());
        btnLogout.addActionListener(e -> logout());
        setLocationRelativeTo(null);  // Center window on screen
        setVisible(true);
    }

    private void createExam() {
        String name = JOptionPane.showInputDialog("Enter exam name:");
        String id = JOptionPane.showInputDialog("Enter exam ID:");
        OnlineExamSystem.createNewExam(name, id);
    }

    private void addQuestion() {
        String text = JOptionPane.showInputDialog("Enter question text:");
        String options = JOptionPane.showInputDialog("Enter options (comma separated):");
        String correct = JOptionPane.showInputDialog("Enter correct option index (0-based):");
        
        try {
            OnlineExamSystem.addQuestion(
                text,
                options.split(","),
                Integer.parseInt(correct)
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    private void viewResults() {
        JOptionPane.showMessageDialog(this, OnlineExamSystem.getExamResults());
    }

    private void logout() {
        OnlineExamSystem.saveAllData();
        dispose();
        new LoginWindow().setVisible(true);
    }
}