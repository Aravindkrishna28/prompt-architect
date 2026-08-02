package com.architect.promptarchitect.model;

public record EvaluationScore(
        int clarity,        // 0-10
        int completeness,   // 0-10
        int structure,       // 0-10
        double overallScore, // average
        String feedback
) {
    public boolean needsRefinement() {
        return overallScore < 7.0;
    }
}