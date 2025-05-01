package users;

import auth.*;
import java.util.*;
import java.io.*;

public class Student extends User {
    public static class StudentRecord implements Serializable {
        private String studentId;
        private String className;

        public StudentRecord(String studentId, String className) {
            if (className == null || className.trim().isEmpty()) {
                throw new IllegalArgumentException("Class cannot be empty");
            }
            this.studentId = studentId;
            this.className = className;
        }

        public String getStudentId() { return studentId; }
        public String getClassName() { return className; }
    }

    private StudentRecord record;
    private static ArrayList<Student> allStudents = new ArrayList<>();
    private static final String STUDENT_FILE = "students.dat";
    private static int idCounter = 1000;

    public Student(String username, String password, String className) {
        super(username, password);
        String studentId = "S" + idCounter++;
        this.record = new StudentRecord(studentId, className);
        allStudents.add(this);
    }

    @Override
    public void displayInfo() {
        System.out.println("Student: " + getUsername() + ", ID: " + 
               record.getStudentId() + ", Class: " + record.getClassName());
    }

    public static void saveToFile() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STUDENT_FILE))) {
            oos.writeObject(allStudents);
        }
    }

    public static ArrayList<Student> getAllStudents() {
        return allStudents;
    }

    public static boolean usernameExists(String username) {
        return allStudents.stream().anyMatch(s -> s.getUsername().equalsIgnoreCase(username));
    }

    @SuppressWarnings("unchecked")
    public static void loadFromFile() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STUDENT_FILE))) {
            allStudents = (ArrayList<Student>) ois.readObject();  // Now properly typed
        }
    }
}