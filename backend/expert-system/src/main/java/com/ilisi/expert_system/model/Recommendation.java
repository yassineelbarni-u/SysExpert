package com.ilisi.expert_system.model;

public record Recommendation(
        String type,
        String recommendation,
        String confidence,
        String raison,
        String details
) {}
