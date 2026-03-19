package com.ilisi.expert_system.engine;

import java.util.List;
import java.util.Map;

// Résultat d'une inférence : faits finaux + trace d'exécution
public class InferenceResult {

    private final Map<String, Object> finalFacts;
    private final List<Map<String, Object>> trace;

    public InferenceResult(Map<String, Object> finalFacts, List<Map<String, Object>> trace) {
        this.finalFacts = finalFacts;
        this.trace = trace;
    }

    public Map<String, Object> getFinalFacts() { return finalFacts; }
    public List<Map<String, Object>> getTrace() { return trace; }
}
