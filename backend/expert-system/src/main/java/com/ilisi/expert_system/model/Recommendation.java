package com.ilisi.expert_system.model;

public class Recommendation {

    private String type;
    private String recommendation;
    private String confidence;
    private String raison;
    private String details;

    public Recommendation() {}

    public Recommendation(String type, String recommendation, String confidence, String raison, String details) {
        this.type = type;
        this.recommendation = recommendation;
        this.confidence = confidence;
        this.raison = raison;
        this.details = details;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }

    public String getConfidence() { return confidence; }
    public void setConfidence(String confidence) { this.confidence = confidence; }

    public String getRaison() { return raison; }
    public void setRaison(String raison) { this.raison = raison; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
}
