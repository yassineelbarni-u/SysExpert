package com.ilisi.expert_system.model;

import java.util.List;

public record Question(
        String id,
        String question,
        String type,
        List<String> options
) {}
