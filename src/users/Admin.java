package users;

import auth.*;
import java.util.*;
import java.io.*;

public class Admin extends User {
    private static class AdminProfile implements Serializable {
        private String adminId;
        private static int idCounter = 1000;

        public AdminProfile() {
            this.adminId = "A" + idCounter++;
        }

        public String getAdminId() { return adminId; }
    }

    private AdminProfile profile;
    private static ArrayList<Admin> allAdmins = new ArrayList<>(); 
    private static final String ADMIN_FILE = "admins.dat";

    public Admin(String username, String password) {
        super(username, password);
        this.profile = new AdminProfile();
        allAdmins.add(this);
    }

    public String getAdminId() {
        return profile.getAdminId();
    }

    @Override
    public void displayInfo() {
        System.out.println("Admin: " + getUsername() + ", ID: " + getAdminId());
    }

    public static void saveToFile() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ADMIN_FILE))) {
            oos.writeObject(allAdmins);
        }
    }

    public static ArrayList<Admin> getAllAdmins() {
        return allAdmins;
    }

    public static boolean usernameExists(String username) {
        return allAdmins.stream().anyMatch(a -> a.getUsername().equalsIgnoreCase(username));
    }
    @SuppressWarnings("unchecked")
    public static void loadFromFile() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ADMIN_FILE))) {
            allAdmins = (ArrayList<Admin>) ois.readObject();  // Now properly typed
        }
    }  
}