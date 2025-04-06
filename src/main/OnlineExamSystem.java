package main;

import auth.*;
import users.*;
import exams.*;
import java.util.*;
import java.io.*;

public class OnlineExamSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static Student currentStudent = null;
    private static Admin currentAdmin = null;
    private static List<MCQExam> exams = new ArrayList<>();
    private static final String EXAMS_FILE = "exams.dat";

    public static void main(String[] args) {
        initializeSystem();
        
        boolean running = true;
        while (running) {
            System.out.println("\n--------- ONLINE EXAM SYSTEM ---------");
            System.out.println("1. Student Portal");
            System.out.println("2. Admin Portal");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1: handleStudentPortal(); break;
                    case 2: handleAdminPortal(); break;
                    case 3: running = false; break;
                    default: System.out.println("Invalid choice...");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
        
        saveData();
        System.out.println("System shutdown successfully");
    }

    private static void initializeSystem() {
        MCQExam javaExam = new MCQExam("Java Fundamentals", "JAVA101");
        javaExam.addQuestion("Which OOPs concept allows a class to inherit properties from another class?", new String[]{"Polymorphism", "Inheritance", "Encapsulation", "Abstraction"}, 1);
        javaExam.addQuestion("Which keyword is used to achieve method overriding in Java?", new String[]{"static", "new", "@Override", "final"}, 2);
        javaExam.addQuestion("Which keyword prevents a class from being inherited?", new String[]{"static", "private", "final", "abstract"}, 2);
        javaExam.addQuestion("What is the main concept of OOPs?", new String[]{"Writing code without errors", "Modeling real-world objects and their relationships", "Organizing code into functions", "Making code run faster"}, 1);
        javaExam.addQuestion("What is encapsulation in OOPs?", new String[]{"Hiding implementation details and exposing only necessary features", "Making all variables public", "Creating multiple versions of a function", "Binding data and methods into a single unit"}, 0);
        javaExam.addQuestion("What is an abstract class?", new String[]{"A class that cannot be instantiated", "A class that cannot have any methods", "A class that can be instantiated", "A class with only static methods"}, 0);
        exams.add(javaExam);
    }

    private static void saveData() {
        try {
            Student.saveToFile();
            Admin.saveToFile();
            saveExamsToFile();
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private static void saveExamsToFile() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(EXAMS_FILE))) {
            oos.writeObject(exams);
        }
    }

    private static void handleStudentPortal() {
        while (true) {
            System.out.println("\nSTUDENT PORTAL");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Back");
            System.out.print("Enter choice: ");
            
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1: registerStudent(); break;
                case 2: if (loginStudent()) { studentDashboard(); return; } break;
                case 3: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void registerStudent() {
        try {
            System.out.print("\nUsername: ");
            String username = scanner.nextLine();
            
            if (Student.usernameExists(username)) {
                System.out.println("Username already exists!");
                return;
            }
            
            System.out.print("Password: ");
            String password = scanner.nextLine();
            System.out.print("Class: ");
            String className = scanner.nextLine();
            
            new Student(username, password, className);
            System.out.println("Registration successful! Your ID will be assigned automatically.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static boolean loginStudent() {
        System.out.print("\nUsername: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        for (Student s : Student.getAllStudents()) {
            if (s.login(username, password)) {
                currentStudent = s;
                System.out.println("Login successful!");
                return true;
            }
        }
        System.out.println("Invalid credentials!");
        return false;
    }

    private static void studentDashboard() {
        while (currentStudent != null) {
            System.out.println("\nSTUDENT DASHBOARD");
            System.out.println("1. View Profile");
            System.out.println("2. Take Exam");
            System.out.println("3. View Results");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");
            
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1: currentStudent.displayInfo(); break;
                case 2: takeExam(); break;
                case 3: viewAllResults(); break;
                case 4: currentStudent = null; return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void takeExam() {
        if (exams.isEmpty()) {
            System.out.println("No exams available!");
            return;
        }
        
        System.out.println("\nAvailable Exams:");
        for (int i = 0; i < exams.size(); i++) {
            System.out.println((i+1) + ". " + exams.get(i).getName() + " (" + exams.get(i).getId() + ")");
        }
        
        System.out.print("Select exam to take: ");
        try {
            int examChoice = Integer.parseInt(scanner.nextLine()) - 1;
            
            if (examChoice >= 0 && examChoice < exams.size()) {
                exams.get(examChoice).conductExam(currentStudent);
            } else {
                System.out.println("Invalid exam choice!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number!");
        }
    }

    private static void viewAllResults() {
        for (MCQExam exam : exams) {
            exam.showResults();
        }
    }

    private static void handleAdminPortal() {
        while (true) {
            System.out.println("\nADMIN PORTAL");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Back");
            System.out.print("Enter choice: ");
            
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1: registerAdmin(); break;
                case 2: if (loginAdmin()) { adminDashboard(); return; } break;
                case 3: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void registerAdmin() {
        try {
            System.out.print("\nUsername: ");
            String username = scanner.nextLine();
            
            if (Admin.usernameExists(username)) {
                System.out.println("Username already exists!");
                return;
            }
            
            System.out.print("Password: ");
            String password = scanner.nextLine();
            
            Admin admin = new Admin(username, password);
            System.out.println("Admin registered! Your ID: " + admin.getAdminId());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static boolean loginAdmin() {
        System.out.print("\nUsername: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        for (Admin a : Admin.getAllAdmins()) {
            if (a.login(username, password)) {
                currentAdmin = a;
                System.out.println("Login successful!");
                return true;
            }
        }
        System.out.println("Invalid credentials.");
        return false;
    }

    private static void adminDashboard() {
        while (currentAdmin != null) {
            System.out.println("\nADMIN DASHBOARD");
            System.out.println("1. View Students");
            System.out.println("2. View Exams");
            System.out.println("3. Create New Exam");
            System.out.println("4. Add Questions to Exam");
            System.out.println("5. View Exam Results");
            System.out.println("6. Logout");
            System.out.print("Enter choice: ");
            
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1: currentAdmin.viewAllStudents(); break;
                case 2: viewAllExams(); break;
                case 3: createNewExam(); break;
                case 4: addQuestionsToExam(); break;
                case 5: viewAllResults(); break;
                case 6: currentAdmin = null; return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void viewAllExams() {
        System.out.println("\n--- Available Exams ---");
        for (int i = 0; i < exams.size(); i++) {
            System.out.println((i+1) + ". " + exams.get(i).getName() + " (" + exams.get(i).getId() + ")");
        }
    }

    private static void createNewExam() {
        System.out.print("\nEnter exam name: ");
        String name = scanner.nextLine();
        System.out.print("Enter exam ID: ");
        String id = scanner.nextLine();
        
        exams.add(new MCQExam(name, id));
        System.out.println("Exam created successfully!");
    }

    private static void addQuestionsToExam() {
        if (exams.isEmpty()) {
            System.out.println("No exams available! Create an exam first.");
            return;
        }
        
        viewAllExams();
        System.out.print("Select exam to add questions to: ");
        try {
            int examChoice = Integer.parseInt(scanner.nextLine()) - 1;
            
            if (examChoice >= 0 && examChoice < exams.size()) {
                boolean adding = true;
                while (adding) {
                    exams.get(examChoice).addNewQuestionFromInput(scanner);
                    System.out.print("Add another question? (y/n): ");
                    adding = scanner.nextLine().equalsIgnoreCase("y");
                }
            } else {
                System.out.println("Invalid exam choice!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number!");
        }
    }
}