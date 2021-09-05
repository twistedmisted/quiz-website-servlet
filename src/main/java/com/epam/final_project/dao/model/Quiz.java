package com.epam.final_project.dao.model;

public class Quiz {

    private long id;

    private String name;

    private int time;

    private String difficulty;

    private String subject;

    public static Quiz createQuiz(String name, int time, String difficulty, String subject) {
        Quiz quiz = new Quiz();
        quiz.setName(name);
        quiz.setTime(time);
        quiz.setDifficulty(difficulty);
        quiz.setSubject(subject);
        return quiz;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Test: " + name + " " + time + " " + difficulty + " " + subject;
    }
}
