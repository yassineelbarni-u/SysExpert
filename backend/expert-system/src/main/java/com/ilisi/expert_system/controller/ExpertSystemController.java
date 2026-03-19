package com.ilisi.expert_system.controller;

import com.ilisi.expert_system.engine.InferenceEngine;
import com.ilisi.expert_system.engine.KnowledgeBaseService;
import com.ilisi.expert_system.engine.Rule;
import com.ilisi.expert_system.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Contrôleur REST du système expert.
 * Expose les mêmes endpoints que le backend Python/FastAPI.
 */
@RestController
@RequestMapping("/api")
public class ExpertSystemController {

    private final List<Rule> knowledgeBase;
    private final InferenceEngine inferenceEngine;
    private final KnowledgeBaseService knowledgeBaseService;

    public ExpertSystemController(KnowledgeBaseService knowledgeBaseService) {
        this.knowledgeBaseService = knowledgeBaseService;
        this.knowledgeBase = knowledgeBaseService.createKnowledgeBase();
        this.inferenceEngine = new InferenceEngine(knowledgeBase);
    }

    /**
     * POST /api/consult
     * Effectue une consultation du système expert et retourne les recommandations.
     */
    @PostMapping("/consult")
    public ResponseEntity<ConsultationResponse> consult(@RequestBody ConsultationRequest request) {
        inferenceEngine.reset();

        InferenceEngine.InferenceResult result = inferenceEngine.infer(request.facts());
        Map<String, Object> finalFacts = result.facts();
        List<Map<String, Object>> trace = result.trace();

        List<Map<String, Object>> rawRecommendations = inferenceEngine.getRecommendations(finalFacts);
        List<Recommendation> recommendations = rawRecommendations.stream()
                .map(r -> new Recommendation(
                        (String) r.get("type"),
                        String.valueOf(r.get("recommendation")),
                        String.valueOf(r.getOrDefault("confidence", "moyenne")),
                        String.valueOf(r.getOrDefault("raison", "")),
                        String.valueOf(r.getOrDefault("details", ""))
                ))
                .toList();

        List<RuleTrace> inferenceTrace = trace.stream()
                .map(t -> new RuleTrace(
                        (int) t.get("iteration"),
                        (String) t.get("rule_name"),
                        (String) t.get("rule_description"),
                        castToMap(t.get("conditions_matched")),
                        castToMap(t.get("new_facts")),
                        (String) t.get("rule_explanation")
                ))
                .toList();

        List<String> explanations = inferenceEngine.explain();

        ConsultationResponse response = new ConsultationResponse(
                recommendations,
                finalFacts,
                inferenceTrace,
                explanations
        );

        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/questions
     * Retourne la liste des questions du questionnaire.
     */
    @GetMapping("/questions")
    public ResponseEntity<Map<String, Object>> getQuestions() {
        List<Map<String, Object>> rawQuestions = knowledgeBaseService.getQuestions();
        List<Question> questions = rawQuestions.stream()
                .map(q -> new Question(
                        (String) q.get("id"),
                        (String) q.get("question"),
                        (String) q.get("type"),
                        castToStringList(q.get("options"))
                ))
                .toList();

        return ResponseEntity.ok(Map.of("questions", questions));
    }

    /**
     * GET /api/rules
     * Retourne la liste des règles de la base de connaissances.
     */
    @GetMapping("/rules")
    public ResponseEntity<Map<String, Object>> getRules() {
        List<RuleInfo> rules = knowledgeBase.stream()
                .map(r -> new RuleInfo(
                        r.getName(),
                        r.getDescription(),
                        r.getPriority(),
                        r.getConditions(),
                        r.getConclusion(),
                        r.toExplanation()
                ))
                .toList();

        return ResponseEntity.ok(Map.of(
                "rules", rules,
                "total_rules", rules.size()
        ));
    }

    /**
     * GET /api/health
     * Vérifie l'état du système expert.
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "healthy",
                "total_rules", knowledgeBase.size(),
                "message", "Système expert opérationnel"
        ));
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> castToMap(Object obj) {
        if (obj instanceof Map<?, ?> map) {
            return (Map<String, Object>) map;
        }
        return Collections.emptyMap();
    }

    @SuppressWarnings("unchecked")
    private List<String> castToStringList(Object obj) {
        if (obj instanceof List<?> list) {
            return (List<String>) list;
        }
        return Collections.emptyList();
    }
}
