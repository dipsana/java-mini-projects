// Ask, evaluate, calculate score and percentage
// src/main/java/com/dipsana/quiz/ui/QuizApp.java
package com.dipsana.quiz.ui;

import com.dipsana.quiz.model.Question;
import com.dipsana.quiz.Main;
import java.util.List;

public final class QuizApp {

    private int score = 0;
    private final List<Question> questions;
    private final int totalQuestions;

    // Constructor to initialize questions
    public QuizApp(List<Question> questions) {
        this.questions = questions;
        this.totalQuestions = questions.size();
    }

    // Main quiz logic
    public final void playQuiz() {

        for (Question q : questions) {
            System.out.println(q); // uses toString() of Question class

            // Take user input
            Main.clearInput();
            String userInput = Main.safeInput().toLowerCase(),
                    selectedAnswer = interpretInput(userInput, q),
                    correctAnswer = q.getAnswer();

            // If attempted or invalid input, show selected answer
            if (selectedAnswer.isEmpty()) {
                System.out.println("\nDon't worry! I got you @(> ^ <)@\nThe correct answer is: " + correctAnswer + "\n");
                continue;
            }
            System.out.println("\nYou entered: " + selectedAnswer);

            // Check answer
            if (selectedAnswer.equals(correctAnswer)) {
                System.out.println("That's correct!\n");
                score++;
            } else {
                System.out.println("Oh-ooh! You got it wrong >w' _ 'w<\nThe correct answer is: " + correctAnswer + "\n");
            }
        }

        // Final score
        System.out.println("Final Score: " + score + " out of " + totalQuestions + ".");
        System.out.printf("Percentage: %.2f%%%n%n", score * 100.0 / totalQuestions);
        System.out.println(
                score <= totalQuestions / 4
                        ? "You need to study harder!"
                        : score <= totalQuestions / 2
                                ? "Well tried!"
                                : score <= totalQuestions * .9
                                        ? "Nice!"
                                        : "Brilliant! You aced it!"
        );
        score = 0; // reset score
    }

    // Match user input with answer and options
    private String match(String input, Question q) {
        return q.getAnswer().equalsIgnoreCase(input) ? q.getAnswer()
                : q.getOpt1().equalsIgnoreCase(input) ? q.getOpt1()
                : q.getOpt2().equalsIgnoreCase(input) ? q.getOpt2()
                : q.getOpt3().equalsIgnoreCase(input) ? q.getOpt3()
                : q.getOpt4().equalsIgnoreCase(input) ? q.getOpt4()
                : "";
    }

    // Helper method to interpret user input
    private String interpretInput(String input, Question q) {

        // 1. Match & store user input
        String norm = match(input, q);

        // 2. If user entered raw answer, return it
        if (!norm.isEmpty()) {
            return norm;
        }

        // 3. Filter trash
        input = input.replaceAll("[^a-fh-in-pr-uwy1-4]", "");
        norm = switch (input) {
            case "y" -> "yes";
            case "n" -> "no";
            case "f" -> "false";
            case "t" -> "true";
            default -> "";
        };

        // 4. Re-check for true false cases
        if (!norm.isEmpty()) {
            norm = match(norm, q);
            if (!norm.isEmpty()) {
                return norm;
            }
        }

        // 5. Normalize input
        norm = input.replaceAll("(.)\\1+", "$1")        // 06. Initial collapse
                .replaceAll("opt|ion", "")              // 07. Strip opt/ion
                .replaceAll("first|1st|one|1", "a")     // 08. Map 'a'
                .replaceAll("second|2nd|two|2", "b")    // 09. Map 'b'
                .replaceAll("third|3rd|thre|3", "c")    // 10. Map 'c'
                .replaceAll("fourth|four|4th|4", "d")   // 11. Map 'd'
                .replaceAll("(.)\\1+", "$1");           // 12. Final collapse
        
        // 13. Return option that user selected
        return norm.length() < 2 ? switch (norm) {
            case "a" -> q.getOpt1();
            case "b" -> q.getOpt2();
            case "c" -> q.getOpt3();
            case "d" -> q.getOpt4();
            default -> ""; // invalid answer
        }: "";
    }
}
