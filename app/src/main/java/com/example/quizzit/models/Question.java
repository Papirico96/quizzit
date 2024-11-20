package com.example.quizzit.models;

public class Question {
    private int id;
    private int blockId;
    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int correctOption;

    public Question(int id, int blockId, String questionText, String option1, String option2, String option3, String option4, int correctOption) {
        this.id = id;
        this.blockId = blockId;
        this.questionText = questionText;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctOption = correctOption;
    }

    public int getId() {
        return id;
    }

    public int getBlockId() {
        return blockId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public int getCorrectOption() {
        return correctOption;
    }

    public String getOption(int optionIndex) {
        switch (optionIndex) {
            case 1:
                return option1;
            case 2:
                return option2;
            case 3:
                return option3;
            case 4:
                return option4;
            default:
                return null;
        }
    }
}