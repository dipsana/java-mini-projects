// Enjoy java quizzes
// src/main/java/com/dipsana/quiz/Main.java
package com.dipsana.quiz;

import com.dipsana.quiz.service.QuestionService;
import com.dipsana.quiz.ui.QuizApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class Main {

    // Delay execution
    static final void snooze(int timeInMs) {
        try {
            Thread.sleep(timeInMs);
        } catch (InterruptedException ex) {
            System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    // Init: Max Input, Buffered Reader
    public static final int MAX_INPUT = 1_000;
    private static final BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));

    // Global user input handler
    public static final String safeInput() {
        StringBuilder input = new StringBuilder("");
        int ch;

        // Take input safely
        try {
            // Read character by character
            while ((ch = BR.read()) != -1) {
                // Skip carriage returns
                if (ch == '\r') {
                    continue;
                }
                // Stop at newline
                if (ch == '\n') {
                    break;
                }
                // Don't exceed max length
                if (input.length() < MAX_INPUT) {
                    input.append((char) ch);
                }
            }
            // Abnormal termination (EOF, CTRL + C)
            if (ch == -1) {
                System.out.println("Calm down! Shutting down gracefully...");
                snooze(2000);
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println("Ops! An I/O error occurred: " + e.getMessage() + "\nShutting down gracefully...");
            snooze(4000);
            System.exit(1);
        }
        // Return trimmed input String
        return input.toString().trim();
    }

    // Clear pending input in welcome screen
    public static final void clearInput() {
        try {
            while (System.in.available() > 0) {
                System.in.read();
            }
        } catch (IOException e) {
            System.err.println("Whoops! An I/O error occurred: " + e.getMessage() + "\nShutting down gracefully...");
            snooze(4000);
            System.exit(1);
        }
    }

    // Close Buffered Reader
    static final void closeInput() {
        try {
            BR.close();
        } catch (IOException e) {
            System.err.println("Ops! Can't close BufferReader: " + e.getMessage() + "\nShutting down gracefully...");
            snooze(4000);
            System.exit(1);
        }
    }

    // Ask user if they want to play again
    static final boolean continuation() {
        System.out.print("\nDo you want to play again (y/n)?: ");
        clearInput();
        String choice = safeInput();

        // Recursive function that calls itself until user enters 'y'
        return choice.isEmpty() ? continuation()
        : switch (choice.charAt(0)) {
            case 'y' -> true;
            case 'n' -> false;
            default -> continuation();
        };
    }

    public static final void main(String args[]) {
        // Init:
        QuestionService service = new QuestionService();       // Questions
        QuizApp newQuiz = new QuizApp(service.getQuestions()); // PlayQuiz

        // Welcome screen (intuitive)
        {
            String[] msg = {
                "\n", "Hello!", "Welcome", "to", "Play", "Quiz", "@''@", "\n\n",
                "Get", "ready", "with", "your", "keyboard", "because...", "\n",
                "I'll", "be", "asking", "you", String.valueOf(service.getQuestions().size()), "questions!", "\n\n",
                "PRESS", "ENTER", "TO", "SKIP", "ANY", "QUESTION!", "\n\n",
                "DON'T", "EXCEED", String.valueOf(MAX_INPUT), "CHARACTERS!", "\n\n",
                "And", "good luck", ">", "''", "<", "\n"
            };
            for (String word : msg) {
                try {
                    Thread.sleep(500);
                    clearInput();
                    System.out.print(word + " ");
                } catch (InterruptedException e) {
                    System.out.print(word + " ");
                }
            }
        }

        // Play Quiz
        do {
            System.out.println();
            newQuiz.playQuiz();
        } while (continuation());
        closeInput();

        // Exit screen
        System.out.println(
            """
            
            I hope you had fun...
            Have a wonderful day @^^@
            
            Exiting... ~<. _ .>~
            """
        );
        snooze(6000);
    }
}
