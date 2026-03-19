package com.ilisi.expert_system.model;

import java.util.Map;

public class ConsultationRequest {

    private Map<String, Object> facts;

    public ConsultationRequest() {}

    public ConsultationRequest(Map<String, Object> facts) {
        this.facts = facts;
    }

    public Map<String, Object> getFacts() { return facts; }
    public void setFacts(Map<String, Object> facts) { this.facts = facts; }
}
