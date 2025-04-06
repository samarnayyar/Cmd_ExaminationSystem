package exams;

import users.*;
import java.util.*;
import java.io.*;

public class MCQExam implements Serializable {
    public static class Question implements Serializable {
        private String text;
        private String[] options;
        private int correctAnswer;

        public Question(String text, String[] options, int correctAnswer) {
            this.text = text;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }

        public void display() {
            System.out.println("\n" + text);
            for (int i = 0; i < options.length; i++) {
                System.out.println((i+1) + ". " + options[i]);
            }
        }

        public boolean isCorrect(int answer) {
            return answer-1 == correctAnswer;
        }
    }

    private String name;
    private String id;
    private List<Question> questions;
    private List<String[]> results;
    private static final String RESULTS_FILE = "exam_results.txt";

    public MCQExam(String name, String id) {
        this.name = name;
        this.id = id;
        this.questions = new ArrayList<>();
        this.results = new ArrayList<>();
    }

    public String getName() { return name; }
    public String getId() { return id; }

    public void addQuestion(String text, String[] options, int correctAnswer) {
        questions.add(new Question(text, options, correctAnswer));
    }

    public void addNewQuestionFromInput(Scanner scanner) {
        System.out.print("\nEnter question text: ");
        String text = scanner.nextLine();
        
        System.out.print("How many options? ");
        int optionCount = Integer.parseInt(scanner.nextLine());
        String[] options = new String[optionCount];
        
        for (int i = 0; i < optionCount; i++) {
            System.out.print("Enter option " + (i+1) + ": ");
            options[i] = scanner.nextLine();
        }
        
        System.out.print("Enter correct option number (1-" + optionCount + "): ");
        int correctAnswer = Integer.parseInt(scanner.nextLine()) - 1;
        
        addQuestion(text, options, correctAnswer);
        System.out.println("Question added successfully!");
    }

    public void conductExam(Student student) {
        Scanner scanner = new Scanner(System.in);
        int score = 0;
        
        System.out.println("\nStarting Exam: " + name);
        for (Question q : questions) {
            q.display();
            System.out.print("Your answer (1-" + q.options.length + "): ");
            try {
                int answer = scanner.nextInt();
                if (q.isCorrect(answer)) score++;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, Next question..");
                scanner.nextLine();
            }
        }
        
        results.add(new String[]{student.getUsername(), String.valueOf(score)});
        saveResultsToFile(student.getUsername(), score);
        System.out.println("\nExam completed! Your score: " + score + "/" + questions.size());
    }

    private void saveResultsToFile(String username, int score) {
        try (FileWriter writer = new FileWriter(RESULTS_FILE, true)) {
            writer.write("Exam: " + name + ", Student: " + username + ", Score: " + score + "/" + questions.size() + "\n");
        } catch (IOException e) {
            System.out.println("Error saving results: " + e.getMessage());
        }
    }

    public void showResults() {
        System.out.println("\nExam Results for " + name + ":");
        for (String[] result : results) {
            System.out.println("Student: " + result[0] + " - Score: " + result[1] + "/" + questions.size());
        }
    }

    public void showQuestions() {
        System.out.println("\nExam: " + name);
        for (int i = 0; i < questions.size(); i++) {
            System.out.println("\nQ" + (i+1) + ": " + questions.get(i).text);
            for (int j = 0; j < questions.get(i).options.length; j++) {
                System.out.println("  " + (j+1) + ". " + questions.get(i).options[j]);
            }
            System.out.println("  Correct Answer: " + (questions.get(i).correctAnswer+1));
        }
    }
}