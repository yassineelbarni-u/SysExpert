package com.ilisi.expert_system.service;

import com.ilisi.expert_system.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExpertServiceTest {

    private ExpertService expertService;

    @BeforeEach
    void setUp() {
        expertService = new ExpertService();
    }

    @Test
    void testMicroservicesRecommendation() {
        Map<String, Object> facts = Map.of(
            "type_application", "web",
            "charge_utilisateurs", "élevée",
            "scalabilite", "critique",
            "budget", "moyen"
        );

        ConsultationResponse response = expertService.consult(facts);

        assertNotNull(response);
        assertFalse(response.getRecommendations().isEmpty());
        assertTrue(response.getFinalFacts().containsKey("architecture_recommandee"));
        assertEquals("Microservices", response.getFinalFacts().get("architecture_recommandee"));
    }

    @Test
    void testMonolithicRecommendation() {
        Map<String, Object> facts = Map.of(
            "budget", "limité",
            "taille_equipe", "petite",
            "complexite_metier", "faible"
        );

        ConsultationResponse response = expertService.consult(facts);

        assertNotNull(response);
        assertTrue(response.getFinalFacts().containsKey("architecture_recommandee"));
        assertEquals("Monolithique", response.getFinalFacts().get("architecture_recommandee"));
    }

    @Test
    void testCloudInfrastructureRecommendation() {
        Map<String, Object> facts = Map.of(
            "scalabilite", "critique",
            "disponibilite", "haute",
            "budget", "moyen"
        );

        ConsultationResponse response = expertService.consult(facts);

        assertNotNull(response);
        assertTrue(response.getFinalFacts().containsKey("infrastructure_recommandee"));
    }

    @Test
    void testCompleteScenario() {
        Map<String, Object> facts = Map.of(
            "type_application", "web",
            "charge_utilisateurs", "élevée",
            "scalabilite", "critique",
            "budget", "moyen",
            "taille_equipe", "grande",
            "equipe_distribuee", "oui",
            "complexite_metier", "élevée",
            "traitement_asynchrone", "oui",
            "disponibilite", "haute",
            "transactions_acid", "oui"
        );

        ConsultationResponse response = expertService.consult(facts);

        assertNotNull(response);
        assertFalse(response.getRecommendations().isEmpty());
        assertFalse(response.getInferenceTrace().isEmpty());
        assertFalse(response.getExplanations().isEmpty());
    }

    @Test
    void testGetQuestions() {
        QuestionsResponse response = expertService.getQuestions();

        assertNotNull(response);
        assertFalse(response.getQuestions().isEmpty());
        assertEquals(16, response.getQuestions().size());
    }

    @Test
    void testGetRules() {
        RulesResponse response = expertService.getRules();

        assertNotNull(response);
        assertFalse(response.getRules().isEmpty());
        assertEquals(response.getRules().size(), response.getTotalRules());
    }

    @Test
    void testGetHealth() {
        Map<String, Object> health = expertService.getHealth();

        assertNotNull(health);
        assertEquals("healthy", health.get("status"));
        assertNotNull(health.get("total_rules"));
    }
}
