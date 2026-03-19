package com.ilisi.expert_system.dto;

import java.util.List;

public class RulesResponse {

    private List<RuleInfo> rules;
    private int totalRules;

    public RulesResponse() {}

    public RulesResponse(List<RuleInfo> rules, int totalRules) {
        this.rules = rules;
        this.totalRules = totalRules;
    }

    public List<RuleInfo> getRules() { return rules; }
    public void setRules(List<RuleInfo> rules) { this.rules = rules; }

    public int getTotalRules() { return totalRules; }
    public void setTotalRules(int totalRules) { this.totalRules = totalRules; }
}
