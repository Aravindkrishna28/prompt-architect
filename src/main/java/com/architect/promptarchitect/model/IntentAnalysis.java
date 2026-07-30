package com.architect.promptarchitect.model;

import java.util.List;

public record IntentAnalysis(
        String primaryGoal,
        List<String> constraints,
        String complexity,     // LOW | MEDIUM | HIGH
        String domainHint      // free-text hint, refined later by Task Classifier
) {}