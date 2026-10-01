// Store QNA with id

// src/main/java/com/dipsana/quiz/model/Question.java
package com.dipsana.quiz.model;

public final class Question {

    // Init: Question, Number, Options and Answer
    private final int id;
    private final String question;
    private final String opt1;
    private final String opt2;
    private final String opt3;
    private final String opt4;
    private final String answer;

    // Constructor
    public Question(int id, String question, String opt1, String opt2, String opt3, String opt4, String answer) {
        this.id = id;
        this.question = question;
        this.opt1 = opt1;
        this.opt2 = opt2;
        this.opt3 = opt3;
        this.opt4 = opt4;
        this.answer = answer;
    }

    // Answer getter
    public final String getAnswer() {
        return answer;
    }

    // Options getters:
    public final String getOpt1() {
        return opt1;
    }

    public final String getOpt2() {
        return opt2;
    }

    public final String getOpt3() {
        return opt3;
    }

    public final String getOpt4() {
        return opt4;
    }

    @Override
    public final String toString() {
        return "Q" + (id + 1) + ": " + question
                + "\nA) " + opt1
                + "\nB) " + opt2
                + "\nC) " + opt3
                + "\nD) " + opt4
                + "\nDo you know the answer (a/b/c/d)?";
    }

}
