package com.example.quizzit.models;

import java.util.List;

public class QuestionBlock {
    private String name;
    private List<Question> questions;
    private int id;


    // Constructor
    public QuestionBlock(int id,String name, List<Question> questions) {
        this.id = id;
        this.name = name;
        this.questions = questions;
    }


    // Getter para obtener la lista de preguntas
    public List<Question> getQuestions() {
        return questions;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    // Método para agregar una pregunta al bloque
    public void addQuestion(Question question) {
        questions.add(question);
    }
}