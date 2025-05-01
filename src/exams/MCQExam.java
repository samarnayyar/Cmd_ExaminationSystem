package exams;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.Serializable;
import java.io.FileWriter;
import java.io.IOException;

// Swing/AWT imports
import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;
import javax.swing.AbstractButton;

import users.Student;

public class MCQExam implements Serializable {
    public static class Question implements Serializable {
        public String text;
        public String[] options;
        public int correctAnswer;

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

    // [Keep all your existing methods unchanged]
    // Only the imports and package declaration were the issues

    public void conductExam(Student student) {
        JFrame examFrame = new JFrame("Exam: " + name);
        examFrame.setSize(600, 500);
        examFrame.setLocationRelativeTo(null);
    
        JPanel mainPanel = new JPanel(new BorderLayout());
        JTextArea questionArea = new JTextArea();
        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
    
        JPanel optionsPanel = new JPanel(new GridLayout(0, 1));
        ButtonGroup optionGroup = new ButtonGroup();
    
        JButton submitButton = new JButton("Submit Answer");
        AtomicInteger score = new AtomicInteger(0);
        AtomicInteger currentQuestion = new AtomicInteger(0);
        List<Integer> selectedAnswers = new ArrayList<>();
    
        // Initialize with empty answers
        for (int i = 0; i < questions.size(); i++) {
            selectedAnswers.add(-1);
        }
    
        // Show first question
        showQuestion(currentQuestion.get(), questionArea, optionsPanel, optionGroup, selectedAnswers);
    
        submitButton.addActionListener(e -> {
            // Get selected answer
            int selectedIndex = -1;
            for (Enumeration<AbstractButton> buttons = optionGroup.getElements(); buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    selectedIndex = optionsPanel.getComponentZOrder(button);
                    break;
                }
            }
        
            // Store answer
            selectedAnswers.set(currentQuestion.get(), selectedIndex);
        
            // Check if correct
            if (selectedIndex == questions.get(currentQuestion.get()).correctAnswer) {
                score.incrementAndGet();
            }
        
            // Move to next question or finish
            if (currentQuestion.incrementAndGet() < questions.size()) {
                optionsPanel.removeAll();
                optionGroup.clearSelection();
                showQuestion(currentQuestion.get(), questionArea, optionsPanel, optionGroup, selectedAnswers);
                examFrame.revalidate();
                examFrame.repaint();
            } else {
                results.add(new String[]{student.getUsername(), String.valueOf(score.get())});
                JOptionPane.showMessageDialog(examFrame, 
                    "Exam completed! Score: " + score.get() + "/" + questions.size());
                examFrame.dispose();
            }
        });
    
        mainPanel.add(new JScrollPane(questionArea), BorderLayout.NORTH);
        mainPanel.add(optionsPanel, BorderLayout.CENTER);
        mainPanel.add(submitButton, BorderLayout.SOUTH);
    
        examFrame.add(mainPanel);
        examFrame.setVisible(true);
    }

    private void showQuestion(int index, JTextArea area, JPanel panel, ButtonGroup group, List<Integer> selectedAnswers) {
        Question q = questions.get(index);
        area.setText("Question " + (index+1) + " of " + questions.size() + ":\n" + q.text + "\n\nSelect one option:");
    
        for (int i = 0; i < q.options.length; i++) {
            JRadioButton option = new JRadioButton((i+1) + ". " + q.options[i]);
            group.add(option);
            panel.add(option);
        
            // Select previously chosen answer if exists
            if (selectedAnswers.get(index) == i) {
                option.setSelected(true);
            }
        }
    }

    // In MCQExam.java
    public void addQuestion(String text, String[] options, int correctAnswer) {
        questions.add(new Question(text, options, correctAnswer));
    }

    public String getResultsAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Exam Results for ").append(name).append(":\n");
        for (String[] result : results) {
            sb.append("Student: ").append(result[0])
            .append(" - Score: ").append(result[1])
            .append("/").append(questions.size()).append("\n");
        }
        return sb.toString();
    }
}   
