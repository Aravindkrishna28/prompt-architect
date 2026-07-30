package com.architect.promptarchitect.model;

// Grows as each phase adds a stage's output. Nullable fields are populated progressively.
public record PipelineResult(
        String rawIdea,
        Object intentAnalysis,     // Phase 3
        Object taskClassification, // Phase 4
        Object strategy,           // Phase 5
        Object architectedPrompt,  // Phase 6
        Object evaluation,         // Phase 7
        Object refinedPrompt       // Phase 8
) {}