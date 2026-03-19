package com.ilisi.expert_system.engine;

import java.util.List;
import java.util.Map;

/**
 * Représente une règle d'inférence SI...ALORS du système expert.
 */
public class Rule {

    private final String name;
    private final Map<String, Object> conditions;
    private final Map<String, Object> conclusion;
    private final String description;
    private final int priority;

    public Rule(String name, Map<String, Object> conditions, Map<String, Object> conclusion,
                String description, int priority) {
        this.name = name;
        this.conditions = conditions;
        this.conclusion = conclusion;
        this.description = description;
        this.priority = priority;
    }

    /**
     * Évalue si la règle est applicable selon les faits courants.
     */
    @SuppressWarnings("unchecked")
    public boolean evaluate(Map<String, Object> facts) {
        for (Map.Entry<String, Object> entry : conditions.entrySet()) {
            String key = entry.getKey();
            Object expectedValue = entry.getValue();

            if (!facts.containsKey(key)) {
                return false;
            }

            Object actualValue = facts.get(key);

            if (expectedValue instanceof List) {
                List<Object> expectedList = (List<Object>) expectedValue;
                if (!expectedList.contains(actualValue)) {
                    return false;
                }
            } else {
                if (!expectedValue.equals(actualValue)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Applique la règle et retourne les nouveaux faits à ajouter.
     */
    public Map<String, Object> apply(Map<String, Object> facts) {
        return Map.copyOf(conclusion);
    }

    /**
     * Génère une explication SI...ALORS de la règle.
     */
    public String toExplanation() {
        StringBuilder conditions = new StringBuilder();
        for (Map.Entry<String, Object> entry : this.conditions.entrySet()) {
            if (conditions.length() > 0) {
                conditions.append(" ET ");
            }
            conditions.append(entry.getKey()).append(" = ").append(entry.getValue());
        }

        StringBuilder conclusion = new StringBuilder();
        for (Map.Entry<String, Object> entry : this.conclusion.entrySet()) {
            if (conclusion.length() > 0) {
                conclusion.append(", ");
            }
            conclusion.append(entry.getKey()).append(" = ").append(entry.getValue());
        }

        return "SI " + conditions + " ALORS " + conclusion;
    }

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
