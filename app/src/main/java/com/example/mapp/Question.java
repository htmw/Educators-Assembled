package com.example.mapp;

public class Question {

    private String text;
    private boolean completed;
    private String comment;

    public Question(String text, boolean completed, String comment) {
        this.text = text;
        this.completed = completed;
        this.comment = comment;
    }

    public String getText() {
        return text;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
