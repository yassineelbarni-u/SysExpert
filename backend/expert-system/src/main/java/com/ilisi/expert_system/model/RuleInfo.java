package com.ilisi.expert_system.model;

import java.util.Map;

public record RuleInfo(
        String name,
        String description,
        int priority,
        Map<String, Object> conditions,
        Map<String, Object> conclusion,
        String explanation
) {}
