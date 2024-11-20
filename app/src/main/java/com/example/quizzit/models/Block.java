package com.example.quizzit.models;

public class Block {
    private int id;
    private String title;
    private String description;

    public Block(int id, String name, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
