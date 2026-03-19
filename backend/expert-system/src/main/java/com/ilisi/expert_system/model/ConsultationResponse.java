package com.ilisi.expert_system.model;

import java.util.List;
import java.util.Map;

public class ConsultationResponse {

    private List<Recommendation> recommendations;
    private Map<String, Object> finalFacts;
    private List<RuleTrace> inferenceTrace;
    private List<String> explanations;

    public ConsultationResponse() {}

    public ConsultationResponse(List<Recommendation> recommendations,
                                Map<String, Object> finalFacts,
                                List<RuleTrace> inferenceTrace,
                                List<String> explanations) {
        this.recommendations = recommendations;
        this.finalFacts = finalFacts;
        this.inferenceTrace = inferenceTrace;
        this.explanations = explanations;
    }

    public List<Recommendation> getRecommendations() { return recommendations; }
    public void setRecommendations(List<Recommendation> recommendations) { this.recommendations = recommendations; }

    public Map<String, Object> getFinalFacts() { return finalFacts; }
    public void setFinalFacts(Map<String, Object> finalFacts) { this.finalFacts = finalFacts; }

    public List<RuleTrace> getInferenceTrace() { return inferenceTrace; }
    public void setInferenceTrace(List<RuleTrace> inferenceTrace) { this.inferenceTrace = inferenceTrace; }

    public List<String> getExplanations() { return explanations; }
    public void setExplanations(List<String> explanations) { this.explanations = explanations; }
}
