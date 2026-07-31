package com.architect.promptarchitect.model;

public record TaskClassification(
        TaskDomain domain,
        String subtype,     // e.g. "REST API design", "blog post", "EDA", "landing page"
        String reasoning
) {}