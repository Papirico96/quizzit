package com.example.quizzit;

public class MyData {
    private String title;
    private String description;

    // Constructor que acepta título y descripción
    public MyData(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // Getter para el título
    public String getTitle() {
        return title;
    }

    // Getter para la descripción
    public String getDescription() {
        return description;
    }
}