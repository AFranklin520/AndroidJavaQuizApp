/*
 * Anthony Franklin
 * afranklin18@cnm.edu
 * FranklinP6
 * 09/14/2022
 * Question.java
 *
 * Simple True/False Quiz application
 * */

package com.cis2237.franklin_p1;

public class Question {

    private int testResId;
    private boolean answerTrue;

    public static final Question[] questions = new Question[]{
            new Question(R.string.question1, true),
            new Question(R.string.question2, true),
            new Question(R.string.question3, false),
            new Question(R.string.question4, true),
            new Question(R.string.question5, true),
            new Question(R.string.question6, false),
            new Question(R.string.question7, false),
            new Question(R.string.question8, false),
            new Question(R.string.question9, true),
            new Question(R.string.question11, false),
            new Question(R.string.question12, false),
            new Question(R.string.question13, true),
            new Question(R.string.question14, false),
            new Question(R.string.question15, true),
            new Question(R.string.question16, false),
            new Question(R.string.question17, true),
            new Question(R.string.question18, false),
            new Question(R.string.question19, true),
            new Question(R.string.question20, true)

    };

    private Question(int testResId, boolean answerTrue){
        this.testResId = testResId;
        this.answerTrue = answerTrue;
    }

    public int getTestResId(){
        return testResId;
    }

    public boolean isAnswerTrue(){
        return answerTrue;
    }

}
