package com.ilisi.expert_system.engine;

import java.util.*;
import java.util.function.Predicate;

// Représente une règle d'inférence SI...ALORS
public class Rule {

    private final String name;
    private final Map<String, Object> conditions;
    private final Map<String, Object> conclusion;
    private final String description;
    private final int priority;
    private final Predicate<Map<String, Object>> customCondition;

    public Rule(String name, Map<String, Object> conditions, Map<String, Object> conclusion,
                String description, int priority, Predicate<Map<String, Object>> customCondition) {
        this.name = name;
        this.conditions = conditions;
        this.conclusion = conclusion;
        this.description = description;
        this.priority = priority;
        this.customCondition = customCondition;
    }

    public Rule(String name, Map<String, Object> conditions, Map<String, Object> conclusion,
                String description, int priority) {
        this(name, conditions, conclusion, description, priority, null);
    }

    public boolean evaluate(Map<String, Object> facts) {
        if (customCondition != null) {
            return customCondition.test(facts);
        }

        for (Map.Entry<String, Object> entry : conditions.entrySet()) {
            String key = entry.getKey();
            Object expectedValue = entry.getValue();

            if (!facts.containsKey(key)) {
                return false;
            }

            Object actualValue = facts.get(key);

            if (expectedValue instanceof List) {
                if (!((List<?>) expectedValue).contains(actualValue)) {
                    return false;
                }
            } else if (expectedValue instanceof Map && ((Map<?, ?>) expectedValue).containsKey("operator")) {
                @SuppressWarnings("unchecked")
                Map<String, Object> condMap = (Map<String, Object>) expectedValue;
                if (!evaluateOperator(actualValue, condMap)) {
                    return false;
                }
            } else if (!Objects.equals(actualValue, expectedValue)) {
                return false;
            }
        }

        return true;
    }

    // Évaluateur d'opérateur
    @SuppressWarnings("unchecked")
    private boolean evaluateOperator(Object actual, Map<String, Object> condition) {
        String operator = (String) condition.get("operator");
        Object value = condition.get("value");

        if (!(actual instanceof Comparable) || value == null) {
            return false;
        }

        Comparable<Object> actualComp = (Comparable<Object>) actual;

        switch (operator) {
            case ">":  return actualComp.compareTo(value) > 0;
            case "<":  return actualComp.compareTo(value) < 0;
            case ">=": return actualComp.compareTo(value) >= 0;
            case "<=": return actualComp.compareTo(value) <= 0;
            case "==": return actualComp.compareTo(value) == 0;
            case "!=": return actualComp.compareTo(value) != 0;
            default:   return false;
        }
    }

    public Map<String, Object> apply(Map<String, Object> facts) {
        return new HashMap<>(conclusion);
    }

    public String toExplanation() {
        StringBuilder condStr = new StringBuilder();
        for (Map.Entry<String, Object> entry : conditions.entrySet()) {
            if (condStr.length() > 0) condStr.append(" ET ");
            condStr.append(entry.getKey()).append(" = ").append(entry.getValue());
        }

        StringBuilder concStr = new StringBuilder();
        for (Map.Entry<String, Object> entry : conclusion.entrySet()) {
            if (concStr.length() > 0) concStr.append(", ");
            concStr.append(entry.getKey()).append(" = ").append(entry.getValue());
        }

        return "SI " + condStr + " ALORS " + concStr;
    }

    public String getName() { return name; }
    public Map<String, Object> getConditions() { return Collections.unmodifiableMap(conditions); }
    public Map<String, Object> getConclusion() { return Collections.unmodifiableMap(conclusion); }
    public String getDescription() { return description; }
    public int getPriority() { return priority; }

    @Override
    public String toString() {
        return "Rule(name='" + name + "', priority=" + priority + ")";
    }
}
