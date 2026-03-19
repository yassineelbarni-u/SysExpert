package com.ilisi.expert_system.controller;

import com.ilisi.expert_system.engine.Rule;
import com.ilisi.expert_system.model.ConsultationRequest;
import com.ilisi.expert_system.model.ConsultationResponse;
import com.ilisi.expert_system.model.Question;
import com.ilisi.expert_system.service.ExpertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contrôleur REST du système expert.
 * Expose les mêmes endpoints que l'API Python FastAPI.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class ExpertController {

    private final ExpertService expertService;

    /**
     * Spring injecte automatiquement ExpertService grâce à l'injection par constructeur.
     */
    public ExpertController(ExpertService expertService) {
        this.expertService = expertService;
    }

    /**
     * POST /api/consult
     * Lance une consultation du système expert avec les faits fournis.
     */
    @PostMapping("/consult")
    public ResponseEntity<ConsultationResponse> consult(@RequestBody ConsultationRequest request) {
        ConsultationResponse response = expertService.consult(request);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/questions
     * Retourne la liste des questions à poser à l'utilisateur.
     */
    @GetMapping("/questions")
    public ResponseEntity<Map<String, Object>> getQuestions() {
        List<Question> questions = expertService.getQuestions();
        Map<String, Object> response = new HashMap<>();
        response.put("questions", questions);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/rules
     * Retourne toutes les règles de la base de connaissances.
     */
    @GetMapping("/rules")
    public ResponseEntity<Map<String, Object>> getRules() {
        List<Rule> rules = expertService.getRules();
        List<Map<String, Object>> rulesInfo = rules.stream()
                .map(rule -> {
                    Map<String, Object> info = new HashMap<>();
                    info.put("name", rule.getName());
                    info.put("description", rule.getDescription());
                    info.put("priority", rule.getPriority());
                    info.put("conditions", rule.getConditions());
                    info.put("conclusion", rule.getConclusion());
                    info.put("explanation", rule.toExplanation());
                    return info;
                })
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("rules", rulesInfo);
        response.put("total_rules", expertService.getRulesCount());
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/health
     * Vérifie que le service est opérationnel.
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "healthy");
        status.put("total_rules", expertService.getRulesCount());
        status.put("message", "Système expert opérationnel");
        return ResponseEntity.ok(status);
    }
}
