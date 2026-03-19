package com.ilisi.expert_system.service;

import com.ilisi.expert_system.dto.*;
import com.ilisi.expert_system.engine.InferenceEngine;
import com.ilisi.expert_system.engine.InferenceResult;
import com.ilisi.expert_system.engine.KnowledgeBase;
import com.ilisi.expert_system.engine.Rule;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExpertService {

    private final List<Rule> knowledgeBase;
    private final InferenceEngine inferenceEngine;

    public ExpertService() {
        this.knowledgeBase = KnowledgeBase.createKnowledgeBase();
        this.inferenceEngine = new InferenceEngine(this.knowledgeBase);
    }

    /**
     * Exécute le moteur d'inférence sur les faits fournis et retourne
     * les recommandations, la trace d'exécution et les explications.
     */
    public ConsultationResponse consult(Map<String, Object> facts) {
        inferenceEngine.reset();

        InferenceResult result = inferenceEngine.infer(facts);

        List<Map<String, Object>> rawRecommendations =
                inferenceEngine.getRecommendations(result.getFinalFacts());

        List<Recommendation> recommendations = rawRecommendations.stream()
                .map(r -> new Recommendation(
                        (String) r.getOrDefault("type", ""),
                        String.valueOf(r.getOrDefault("recommendation", "")),
                        String.valueOf(r.getOrDefault("confidence", "moyenne")),
                        String.valueOf(r.getOrDefault("raison", "")),
                        String.valueOf(r.getOrDefault("details", ""))
                ))
                .collect(Collectors.toList());

        List<RuleTrace> inferenceTrace = result.getTrace().stream()
                .map(t -> {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> conditionsMatched =
                            (Map<String, Object>) t.getOrDefault("conditions_matched", Collections.emptyMap());
                    @SuppressWarnings("unchecked")
                    Map<String, Object> newFacts =
                            (Map<String, Object>) t.getOrDefault("new_facts", Collections.emptyMap());
                    return new RuleTrace(
                            (int) t.getOrDefault("iteration", 0),
                            String.valueOf(t.getOrDefault("rule_name", "")),
                            String.valueOf(t.getOrDefault("rule_description", "")),
                            conditionsMatched,
                            newFacts,
                            String.valueOf(t.getOrDefault("rule_explanation", ""))
                    );
                })
                .collect(Collectors.toList());

        List<String> explanations = inferenceEngine.explain();

        return new ConsultationResponse(recommendations, result.getFinalFacts(), inferenceTrace, explanations);
    }

    /**
     * Retourne la liste des questions du questionnaire.
     */
    public QuestionsResponse getQuestions() {
        List<Map<String, Object>> rawQuestions = KnowledgeBase.getQuestions();

        List<Question> questions = rawQuestions.stream()
                .map(q -> {
                    @SuppressWarnings("unchecked")
                    List<String> options = (List<String>) q.getOrDefault("options", Collections.emptyList());
                    return new Question(
                            (String) q.get("id"),
                            (String) q.get("question"),
                            (String) q.get("type"),
                            options
                    );
                })
                .collect(Collectors.toList());

        return new QuestionsResponse(questions);
    }

    /**
     * Retourne la liste de toutes les règles de la base de connaissances.
     */
    public RulesResponse getRules() {
        List<RuleInfo> rulesInfo = knowledgeBase.stream()
                .map(rule -> new RuleInfo(
                        rule.getName(),
                        rule.getDescription(),
                        rule.getPriority(),
                        rule.getConditions(),
                        rule.getConclusion(),
                        rule.toExplanation()
                ))
                .collect(Collectors.toList());

        return new RulesResponse(rulesInfo, rulesInfo.size());
    }

    /**
     * Retourne l'état de santé du système expert.
     */
    public Map<String, Object> getHealth() {
        Map<String, Object> health = new LinkedHashMap<>();
        health.put("status", "healthy");
        health.put("total_rules", knowledgeBase.size());
        health.put("message", "Système expert opérationnel");
        return health;
    }
}
