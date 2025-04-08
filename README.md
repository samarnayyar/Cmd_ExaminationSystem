# Online Examination System

This project is a console-based Online Examination System built in Java. It streamlines the exam process by providing separate functionalities for admins and students, supporting secure logins, question management, and instant result generation in a clean, modular structure.

## Features

- Secure login system for Admin and Student
- Admin can create and manage multiple-choice exams
- Student can attempt exams and get instant results
- Automatic evaluation and scoring
- Modular design using packages and OOP principles

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
    └── OnlineExamSystem.java
```
## 🛠 Technologies Used
  - Java
  - OOP Concepts (Inheritance, Interfaces, Encapsulation)
  - Package-based structure

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

   
