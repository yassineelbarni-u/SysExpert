package com.ilisi.expert_system.engine;

import java.util.*;

/**
 * Moteur d'inférence par chaînage avant.
 */
public class InferenceEngine {

    private final List<Rule> rules;
    private List<Rule> appliedRules;
    private List<Map<String, Object>> inferenceTrace;

    public InferenceEngine(List<Rule> rules) {
        this.rules = new ArrayList<>(rules);
        this.rules.sort(Comparator.comparingInt(Rule::getPriority).reversed());
        this.appliedRules = new ArrayList<>();
        this.inferenceTrace = new ArrayList<>();
    }

    /**
     * Lance l'inférence par chaînage avant jusqu'au point fixe.
     */
    public InferenceResult infer(Map<String, Object> initialFacts) {
        Map<String, Object> facts = new LinkedHashMap<>(initialFacts);
        appliedRules = new ArrayList<>();
        inferenceTrace = new ArrayList<>();

        boolean changed = true;
        int iteration = 0;
        int maxIterations = 100;

        while (changed && iteration < maxIterations) {
            changed = false;
            iteration++;

            for (Rule rule : rules) {
                if (!appliedRules.contains(rule) && rule.evaluate(facts)) {
                    Map<String, Object> newFacts = rule.apply(facts);

                    Map<String, Object> traceEntry = new LinkedHashMap<>();
                    traceEntry.put("iteration", iteration);
                    traceEntry.put("rule_name", rule.getName());
                    traceEntry.put("rule_description", rule.getDescription());
                    traceEntry.put("conditions_matched", rule.getConditions());
                    traceEntry.put("new_facts", newFacts);
                    traceEntry.put("rule_explanation", rule.toExplanation());
                    inferenceTrace.add(traceEntry);

                    facts.putAll(newFacts);
                    appliedRules.add(rule);
                    changed = true;
                }
            }
        }

        return new InferenceResult(facts, inferenceTrace);
    }

    /**
     * Extrait les recommandations structurées depuis la base de faits finale.
     */
    public List<Map<String, Object>> getRecommendations(Map<String, Object> facts) {
        List<Map<String, Object>> recommendations = new ArrayList<>();

        addRecommendation(recommendations, facts,
                "architecture_recommandee", "Architecture Applicative",
                "architecture_confidence", "architecture_raison", "architecture_details");

        addRecommendation(recommendations, facts,
                "scalabilite_type", "Stratégie de Scalabilité",
                "scalabilite_confidence", "scalabilite_raison", "scalabilite_details");

        addRecommendation(recommendations, facts,
                "langage_recommande", "Langage de Programmation",
                "langage_confidence", "langage_raison", "langage_details");

        addRecommendation(recommendations, facts,
                "infrastructure_recommandee", "Infrastructure",
                "infrastructure_confidence", "infrastructure_raison", "infrastructure_details");

        addRecommendation(recommendations, facts,
                "taille_serveur", "Taille et Configuration Serveur",
                "taille_serveur_confidence", "taille_serveur_raison", "taille_serveur_details");

        addRecommendation(recommendations, facts,
                "database_recommandee", "Base de Données",
                "database_confidence", "database_raison", "database_details");

        addRecommendation(recommendations, facts,
                "cache_recommande", "Système de Cache",
                "cache_confidence", "cache_raison", "cache_details");

        addRecommendation(recommendations, facts,
                "conteneurisation", "Conteneurisation et Orchestration",
                "conteneurisation_confidence", "conteneurisation_raison", "conteneurisation_details");

        addRecommendation(recommendations, facts,
                "cicd_recommande", "CI/CD et DevOps",
                "cicd_confidence", "cicd_raison", "cicd_details");

        addRecommendation(recommendations, facts,
                "monitoring_recommande", "Monitoring et Observabilité",
                "monitoring_confidence", "monitoring_raison", "monitoring_details");

        return recommendations;
    }

    private void addRecommendation(List<Map<String, Object>> list,
                                   Map<String, Object> facts,
                                   String key, String type,
                                   String confidenceKey, String raisonKey, String detailsKey) {
        if (facts.containsKey(key)) {
            Map<String, Object> rec = new LinkedHashMap<>();
            rec.put("type", type);
            rec.put("recommendation", facts.get(key));
            rec.put("confidence", facts.getOrDefault(confidenceKey, "moyenne"));
            rec.put("raison", facts.getOrDefault(raisonKey, ""));
            rec.put("details", facts.getOrDefault(detailsKey, ""));
            list.add(rec);
        }
    }

    /**
     * Génère des explications textuelles du raisonnement.
     */
    public List<String> explain() {
        List<String> explanations = new ArrayList<>();
        for (Map<String, Object> trace : inferenceTrace) {
            String explanation = "Règle '" + trace.get("rule_name") + "' appliquée: "
                    + trace.get("rule_description") + "\n"
                    + "  → " + trace.get("rule_explanation");
            explanations.add(explanation);
        }
        return explanations;
    }

    public void reset() {
        appliedRules = new ArrayList<>();
        inferenceTrace = new ArrayList<>();
    }

    /**
     * Résultat d'une inférence.
     */
    public record InferenceResult(Map<String, Object> facts, List<Map<String, Object>> trace) {}
}
