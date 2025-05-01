# Online Examination System

![Java](https://img.shields.io/badge/Java-17-blue)
![Swing](https://img.shields.io/badge/GUI-Swing-orange)

This project is a console-based Online Examination System built in Java. It is a complete examination system featuring a modern Swing GUI, with separate interfaces for administrators and students, secure authentication, and automatic scoring.

## Features

### **User Management**
- Secure login/registration for both Students and Admins
- Role-based access control
- Data persistence using serialization

### **Exam System**
- Interactive GUI for taking tests (MCQExam.java)
- Real-time question navigation
- Instant scoring and results

### **Admin Controls**
- Create/update exams (AdminDashboard.java)
- Add multiple-choice questions
- View student performances

## 📁 Project Structure

```
FinalProject/
├── auth/
│   ├── Authenticable.java
│   ├── Displayable.java
│   └── User.java
├── exams/
│   └── MCQExam.java
├── users/
│   ├── Admin.java
│   └── Student.java
└── main/
    └── AdminDashboard.java
    └── LoginWindow.java
    └── OnlineExamSystem.java
    └── RegisterationWindow.java
    └── StudentDashboard.java
```
## 🛠️ Technologies Used

### Fundamental Components
| Technology | Usage |
|------------|-------|
| **Java 17** | Main programming language |
| **Java Swing** | GUI framework for all interfaces |
| **OOP Principles** | Inheritance, Encapsulation, Polymorphism |
| **Serialization** | Persistent data storage (users/exams) |

### Key Implementations
- **Swing Components**: `JFrame`, `JPanel`, `JOptionPane`, `ButtonGroup`
- **Layout Managers**: `BorderLayout`, `GridLayout`, `FlowLayout`
- **Event Handling**: `ActionListener`, `WindowAdapter`

## 🚀 How to Run

1. Clone the repository:
   ```bash
   https://github.com/samarnayyar/OnlineExaminationSystem.git
   ```

2. Compile the program:
   ```bash  
   javac -d bin src/auth/*.java src/users/*.java src/exams/*.java src/main/*.java
   ```

3. Run the application:
   ```bash
   java -cp bin main.OnlineExamSystem
   ```

   
