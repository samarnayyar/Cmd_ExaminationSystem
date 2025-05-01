package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import users.*;
import exams.*;

public class OnlineExamSystem {
    // Core system data
    private static MCQExam currentExam;
    private static Student currentStudent;
    private static Admin currentAdmin;
    private static final String EXAMS_FILE = "exams.dat";

    public static void main(String[] args) {
        initializeSystem();
        
        // Launch the Swing login window
        SwingUtilities.invokeLater(() -> {
            new LoginWindow().setVisible(true);
        });
    }

    private static void initializeSystem() {
        // Load existing exam or create default
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(EXAMS_FILE))) {
            currentExam = (MCQExam) ois.readObject();
        } catch (Exception e) {
            // Create default exam if file doesn't exist
            currentExam = new MCQExam("Java Fundamentals", "JAVA101");
            currentExam.addQuestion("Which OOPs concept allows a class to inherit properties from another class?", new String[]{"Polymorphism", "Inheritance", "Encapsulation", "Abstraction"}, 1);
            currentExam.addQuestion("Which keyword is used to achieve method overriding in Java?", new String[]{"static", "new", "@Override", "final"}, 2);
            currentExam.addQuestion("Which keyword prevents a class from being inherited?", new String[]{"static", "private", "final", "abstract"}, 2);
            currentExam.addQuestion("What is the main concept of OOPs?", new String[]{"Writing code without errors", "Modeling real-world objects and their relationships", "Organizing code into functions", "Making code run faster"}, 1);
            currentExam.addQuestion("What is encapsulation in OOPs?", new String[]{"Hiding implementation details and exposing only necessary features", "Making all variables public", "Creating multiple versions of a function", "Binding data and methods into a single unit"}, 0);
            currentExam.addQuestion("What is an abstract class?", new String[]{"A class that cannot be instantiated", "A class that cannot have any methods", "A class that can be instantiated", "A class with only static methods"}, 0);
        }
        
        // Load users
        try {
            Student.loadFromFile();
            Admin.loadFromFile();
        } catch (Exception e) {
            System.err.println("Error loading user data: " + e.getMessage());
        }
    }


    public static boolean registerStudent(String username, String password, String className) {
        if (Student.usernameExists(username)) {
            return false;
        }
        new Student(username, password, className);
        return true;
    }

    public static Student loginStudent(String username, String password) {
        for (Student s : Student.getAllStudents()) {
            if (s.login(username, password)) {
                currentStudent = s;
                return s;
            }
        }
        return null;
    }


    public static boolean registerAdmin(String username, String password) {
        if (Admin.usernameExists(username)) {
            return false;
        }
        new Admin(username, password);
        return true;
    }

    public static Admin loginAdmin(String username, String password) {
        for (Admin a : Admin.getAllAdmins()) {
            if (a.login(username, password)) {
                currentAdmin = a;
                return a;
            }
        }
        return null;
    }

    public static void createNewExam(String name, String id) {
        currentExam = new MCQExam(name, id);
        saveExamData();
    }

    public static void addQuestion(String text, String[] options, int correctAnswer) {
        if (currentExam != null) {
            currentExam.addQuestion(text, options, correctAnswer);
            saveExamData();
        }
    }

    public static void conductExam() {
        if (currentExam != null && currentStudent != null) {
            currentExam.conductExam(currentStudent);
            saveExamData();
        } else{
        System.err.println("No exam available or no student logged in");
        }
    }

    public static String getExamResults() {
        return currentExam != null ? currentExam.getResultsAsString() : "No exam available";
    }

    private static void saveExamData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(EXAMS_FILE))) {
            oos.writeObject(currentExam);
        } catch (IOException e) {
            System.err.println("Error saving exam data: " + e.getMessage());
        }
    }

    public static void saveAllData() {
        try {
            Student.saveToFile();
            Admin.saveToFile();
            saveExamData();
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    public static Student getCurrentStudent() {
        return currentStudent;
    }
}