package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentDashboard extends JFrame {
    public StudentDashboard() {
        setTitle("Student Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnTakeExam = new JButton("Take Exam");
        JButton btnViewResults = new JButton("View Results");
        JButton btnLogout = new JButton("Logout");

        add(btnTakeExam);
        add(btnViewResults);
        add(btnLogout);

        btnTakeExam.addActionListener(e -> takeExam());
        btnViewResults.addActionListener(e -> viewResults());
        btnLogout.addActionListener(e -> logout());
        setLocationRelativeTo(null);  // Center window on screen
        setVisible(true);
    }

    private void takeExam(){
    if (OnlineExamSystem.getCurrentStudent() != null){
        OnlineExamSystem.conductExam();
    } else {
        JOptionPane.showMessageDialog(this, "No student logged in!", "Error", JOptionPane.ERROR_MESSAGE);}
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