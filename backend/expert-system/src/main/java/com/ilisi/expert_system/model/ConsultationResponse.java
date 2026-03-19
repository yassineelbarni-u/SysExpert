package com.ilisi.expert_system.model;

import java.util.List;
import java.util.Map;

public record ConsultationResponse(
        List<Recommendation> recommendations,
        Map<String, Object> finalFacts,
        List<RuleTrace> inferenceTrace,
        List<String> explanations
) {}
