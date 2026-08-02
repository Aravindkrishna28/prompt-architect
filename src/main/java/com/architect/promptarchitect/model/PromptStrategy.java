package com.architect.promptarchitect.model;

import java.util.List;

public record PromptStrategy(
        PromptTechnique primaryTechnique,
        List<PromptTechnique> supportingTechniques,
        String rationale
) {}