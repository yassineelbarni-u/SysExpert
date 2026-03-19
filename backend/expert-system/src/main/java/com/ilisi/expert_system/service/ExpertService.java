package com.ilisi.expert_system.service;

import com.ilisi.expert_system.engine.InferenceEngine;
import com.ilisi.expert_system.engine.KnowledgeBase;
import com.ilisi.expert_system.engine.Rule;
import com.ilisi.expert_system.model.ConsultationRequest;
import com.ilisi.expert_system.model.ConsultationResponse;
import com.ilisi.expert_system.model.Question;
import com.ilisi.expert_system.model.Recommendation;
import com.ilisi.expert_system.model.RuleTrace;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service métier du système expert.
 * Orchestre la base de connaissances et le moteur d'inférence.
 * Spring injecte automatiquement ce bean grâce à @Service et @Autowired.
 */
@Service
public class ExpertService {

    private final KnowledgeBase knowledgeBase;
    private final InferenceEngine inferenceEngine;

    /**
     * Injection de dépendances par constructeur (bonne pratique Spring).
     * Spring instancie automatiquement KnowledgeBase et l'injecte ici.
     */
    public ExpertService(KnowledgeBase knowledgeBase) {
        this.knowledgeBase = knowledgeBase;
        this.inferenceEngine = new InferenceEngine(knowledgeBase.getRules());
    }

    /**
     * Lance une consultation : inférence + recommandations + trace.
     */
    public ConsultationResponse consult(ConsultationRequest request) {
        inferenceEngine.reset();

        InferenceEngine.InferenceResult result = inferenceEngine.infer(request.getFacts());
        Map<String, Object> finalFacts = result.finalFacts();
        List<Map<String, Object>> traceData = result.trace();

        List<Map<String, Object>> recData = inferenceEngine.getRecommendations(finalFacts);
        List<String> explanations = inferenceEngine.explain();

        List<Recommendation> recommendations = recData.stream()
                .map(r -> new Recommendation(
                        (String) r.get("type"),
                        (String) r.get("recommendation"),
                        (String) r.get("confidence"),
                        (String) r.getOrDefault("raison", ""),
                        (String) r.getOrDefault("details", "")))
                .toList();

        @SuppressWarnings("unchecked")
        List<RuleTrace> inferenceTrace = traceData.stream()
                .map(t -> new RuleTrace(
                        (int) t.get("iteration"),
                        (String) t.get("rule_name"),
                        (String) t.get("rule_description"),
                        (Map<String, Object>) t.get("conditions_matched"),
                        (Map<String, Object>) t.get("new_facts"),
                        (String) t.get("rule_explanation")))
                .toList();

        return new ConsultationResponse(recommendations, finalFacts, inferenceTrace, explanations);
    }

    /**
     * Retourne la liste des questions à poser à l'utilisateur.
     */
    public List<Question> getQuestions() {
        List<Map<String, Object>> rawQuestions = knowledgeBase.getQuestions();
        List<Question> questions = new ArrayList<>();
        for (Map<String, Object> q : rawQuestions) {
            @SuppressWarnings("unchecked")
            List<String> options = (List<String>) q.get("options");
            questions.add(new Question(
                    (String) q.get("id"),
                    (String) q.get("question"),
                    (String) q.get("type"),
                    options));
        }
        return questions;
    }

    /**
     * Retourne les informations sur toutes les règles de la base de connaissances.
     */
    public List<Rule> getRules() {
        return knowledgeBase.getRules();
    }

    /**
     * Retourne le nombre total de règles.
     */
    public int getRulesCount() {
        return knowledgeBase.getRules().size();
    }
}
