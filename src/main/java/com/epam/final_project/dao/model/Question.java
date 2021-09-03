package com.epam.final_project.dao.model;

import java.util.List;

public class Question {

    private long id;

    private String prompt;

    private List<String> variants;

    private List<Character> answers;

    public static Question createQuestion(String prompt, List<String> variants, List<Character> answer) {
        Question question = new Question();
        question.setPrompt(prompt);
        question.setVariants(variants);
        question.setAnswers(answer);
        return question;
    }

    public long getId() {
        return id;
    }

    public String getPrompt() {
        return prompt;
    }

    public List<String> getVariants() {
        return variants;
    }

    public List<Character> getAnswers() {
        return answers;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setVariants(List<String> variants) {
        this.variants = variants;
    }

    public void setAnswers(List<Character> answers) {
        this.answers = answers;
    }

    public String showVariants() {
        StringBuilder sb = new StringBuilder();
        for (String variant : variants) {
            sb.append(variant).append(',');
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    @Override
    public String toString() {
        return prompt + " : " + answers;
    }
}
