package com.ilisi.expert_system.model;

import java.util.Map;

public record RuleTrace(
        int iteration,
        String ruleName,
        String ruleDescription,
        Map<String, Object> conditionsMatched,
        Map<String, Object> newFacts,
        String ruleExplanation
) {}
