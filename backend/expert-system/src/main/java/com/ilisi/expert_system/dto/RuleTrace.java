package com.ilisi.expert_system.dto;

import java.util.Map;

public class RuleTrace {

    private int iteration;
    private String ruleName;
    private String ruleDescription;
    private Map<String, Object> conditionsMatched;
    private Map<String, Object> newFacts;
    private String ruleExplanation;

    public RuleTrace() {}

    public RuleTrace(int iteration, String ruleName, String ruleDescription,
                     Map<String, Object> conditionsMatched, Map<String, Object> newFacts,
                     String ruleExplanation) {
        this.iteration = iteration;
        this.ruleName = ruleName;
        this.ruleDescription = ruleDescription;
        this.conditionsMatched = conditionsMatched;
        this.newFacts = newFacts;
        this.ruleExplanation = ruleExplanation;
    }

    public int getIteration() { return iteration; }
    public void setIteration(int iteration) { this.iteration = iteration; }

    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }

    public String getRuleDescription() { return ruleDescription; }
    public void setRuleDescription(String ruleDescription) { this.ruleDescription = ruleDescription; }

    public Map<String, Object> getConditionsMatched() { return conditionsMatched; }
    public void setConditionsMatched(Map<String, Object> conditionsMatched) { this.conditionsMatched = conditionsMatched; }

    public Map<String, Object> getNewFacts() { return newFacts; }
    public void setNewFacts(Map<String, Object> newFacts) { this.newFacts = newFacts; }

    public String getRuleExplanation() { return ruleExplanation; }
    public void setRuleExplanation(String ruleExplanation) { this.ruleExplanation = ruleExplanation; }
}
