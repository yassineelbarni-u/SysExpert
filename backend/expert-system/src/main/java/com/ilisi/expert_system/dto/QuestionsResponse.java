package com.ilisi.expert_system.dto;

import java.util.List;

public class QuestionsResponse {

    private List<Question> questions;

    public QuestionsResponse() {}

    public QuestionsResponse(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }
}
