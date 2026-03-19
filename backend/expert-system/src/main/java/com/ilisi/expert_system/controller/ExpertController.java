package com.ilisi.expert_system.controller;

import com.ilisi.expert_system.dto.*;
import com.ilisi.expert_system.service.ExpertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class ExpertController {

    private static final Logger log = LoggerFactory.getLogger(ExpertController.class);

    private final ExpertService expertService;

    public ExpertController(ExpertService expertService) {
        this.expertService = expertService;
    }

    @PostMapping("/consult")
    public ResponseEntity<?> consult(@RequestBody ConsultationRequest request) {
        try {
            ConsultationResponse response = expertService.consult(request.getFacts());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erreur lors de la consultation", e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("detail", "Erreur lors de la consultation: " + e.getMessage()));
        }
    }

    @GetMapping("/questions")
    public ResponseEntity<?> getQuestions() {
        try {
            return ResponseEntity.ok(expertService.getQuestions());
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des questions", e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("detail", "Erreur lors de la récupération des questions: " + e.getMessage()));
        }
    }

    @GetMapping("/rules")
    public ResponseEntity<?> getRules() {
        try {
            return ResponseEntity.ok(expertService.getRules());
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des règles", e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("detail", "Erreur lors de la récupération des règles: " + e.getMessage()));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(expertService.getHealth());
    }
}
