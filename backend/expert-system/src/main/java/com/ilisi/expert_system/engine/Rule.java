package com.ilisi.expert_system.engine;

import java.util.*;
import java.util.function.Predicate;

/**
 * Représente une règle d'inférence SI...ALORS du système expert.
 */
public class Rule {

    private final String name;
    private final Map<String, Object> conditions;
    private final Map<String, Object> conclusion;
    private final String description;
    private final int priority;
    private final Predicate<Map<String, Object>> customCondition;

    public Rule(String name,
                Map<String, Object> conditions,
                Map<String, Object> conclusion,
                String description,
                int priority) {
        this(name, conditions, conclusion, description, priority, null);
    }

    public Rule(String name,
                Map<String, Object> conditions,
                Map<String, Object> conclusion,
                String description,
                int priority,
                Predicate<Map<String, Object>> customCondition) {
        this.name = name;
        this.conditions = conditions;
        this.conclusion = conclusion;
        this.description = description;
        this.priority = priority;
        this.customCondition = customCondition;
    }

    /**
     * Évalue si la règle s'applique aux faits donnés.
     */
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

            if (expectedValue instanceof List<?> list) {
                if (!list.contains(actualValue)) {
                    return false;
                }
            } else if (expectedValue instanceof Map<?, ?> condMap && condMap.containsKey("operator")) {
                @SuppressWarnings("unchecked")
                Map<String, Object> opCondition = (Map<String, Object>) condMap;
                if (!evaluateOperator(actualValue, opCondition)) {
                    return false;
                }
            } else if (!Objects.equals(actualValue, expectedValue)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Évalue un opérateur de comparaison.
     */
    private boolean evaluateOperator(Object actual, Map<String, Object> condition) {
        String operator = (String) condition.get("operator");
        Object value = condition.get("value");

        if (!(actual instanceof Comparable) || !(value instanceof Comparable)) {
            return false;
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        int cmp = ((Comparable) actual).compareTo(value);

        return switch (operator) {
            case ">" -> cmp > 0;
            case "<" -> cmp < 0;
            case ">=" -> cmp >= 0;
            case "<=" -> cmp <= 0;
            case "==" -> cmp == 0;
            case "!=" -> cmp != 0;
            default -> false;
        };
    }

    /**
     * Applique la règle et retourne les nouveaux faits.
     */
    public Map<String, Object> apply(Map<String, Object> facts) {
        return new LinkedHashMap<>(conclusion);
    }

    /**
     * Génère une explication textuelle SI...ALORS.
     */
    public String toExplanation() {
        List<String> condParts = new ArrayList<>();
        for (Map.Entry<String, Object> e : conditions.entrySet()) {
            condParts.add(e.getKey() + " = " + e.getValue());
        }
        String conditionsStr = String.join(" ET ", condParts);

        List<String> concParts = new ArrayList<>();
        for (Map.Entry<String, Object> e : conclusion.entrySet()) {
            concParts.add(e.getKey() + " = " + e.getValue());
        }
        String conclusionStr = String.join(", ", concParts);

        return "SI " + conditionsStr + " ALORS " + conclusionStr;
    }

    // Getters
    public String getName() { return name; }
    public Map<String, Object> getConditions() { return conditions; }
    public Map<String, Object> getConclusion() { return conclusion; }
    public String getDescription() { return description; }
    public int getPriority() { return priority; }

    @Override
    public String toString() {
        return "Rule(name='" + name + "', priority=" + priority + ")";
    }
}
