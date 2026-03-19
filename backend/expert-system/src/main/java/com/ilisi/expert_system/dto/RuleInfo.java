package com.ilisi.expert_system.dto;

import java.util.Map;

public class RuleInfo {

    private String name;
    private String description;
    private int priority;
    private Map<String, Object> conditions;
    private Map<String, Object> conclusion;
    private String explanation;

    public RuleInfo() {}

    public RuleInfo(String name, String description, int priority,
                    Map<String, Object> conditions, Map<String, Object> conclusion,
                    String explanation) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.conditions = conditions;
        this.conclusion = conclusion;
        this.explanation = explanation;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public Map<String, Object> getConditions() { return conditions; }
    public void setConditions(Map<String, Object> conditions) { this.conditions = conditions; }

    public Map<String, Object> getConclusion() { return conclusion; }
    public void setConclusion(Map<String, Object> conclusion) { this.conclusion = conclusion; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
}
