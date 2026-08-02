package com.architect.promptarchitect.model;

import java.util.List;

public record ArchitectedPrompt(
        String systemPersona,
        String taskDescription,
        List<String> constraints,
        String outputFormat,
        String fullAssembledPrompt
) {}